package com.example.lightmeter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lightmeter.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class LightMeterState(
    val currentLux: Float = 0f,
    val measurements: List<LightMeasurement> = emptyList(),
    val isRecording: Boolean = false,
    val historyData: List<DataPoint> = emptyList(),
    val selectedRecordIndex: Int? = null,
    val errorMessage: String? = null,
    val settings: AppSettings = AppSettings(),
    val calculationResult: CalculationResult? = null,
    val selectedScene: SceneType? = null,
    val selectedPlant: Plant? = null,
    val showSceneSelector: Boolean = false,
    val showPlantSelector: Boolean = false,
    val displayMode: String = "lux"
)

class LightMeterViewModel : ViewModel(), SensorEventListener {
    private val _state = MutableStateFlow(LightMeterState())
    val state: StateFlow<LightMeterState> = _state
    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private var lastRecordTime = 0L
    private var storageManager: StorageManager? = null

    fun initialize(context: Context) {
        try {
            if (storageManager == null) {
                storageManager = StorageManager(context)
                
                val savedMeasurements = storageManager!!.loadMeasurements()
                val savedSettings = storageManager!!.loadSettings()
                val savedPlantId = storageManager!!.loadSelectedPlantId()
                val savedSceneId = storageManager!!.loadSelectedSceneId()
                val savedCustomPlants = storageManager!!.loadCustomPlants()
                
                DataRepository.setCustomPlants(savedCustomPlants)
                
                val selectedPlant = savedPlantId?.let { id ->
                    DataRepository.getPlantById(id)
                }
                val selectedScene = savedSceneId?.let { id ->
                    DataRepository.scenes.find { it.id == id }
                }
                
                _state.value = _state.value.copy(
                    measurements = savedMeasurements,
                    settings = savedSettings ?: AppSettings(),
                    selectedPlant = selectedPlant,
                    selectedScene = selectedScene
                )
            }
        } catch (e: Exception) {
            _state.value = _state.value.copy(
                errorMessage = "加载数据失败: ${e.message}"
            )
        }
    }

    fun startSensor(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
        
        if (lightSensor == null) {
            _state.value = _state.value.copy(
                errorMessage = "设备不支持光线传感器"
            )
            return
        }
        
        sensorManager?.registerListener(
            this,
            lightSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun toggleRecording() {
        val currentState = _state.value
        if (currentState.isRecording) {
            _state.value = currentState.copy(isRecording = false)
        } else {
            lastRecordTime = 0L
            val now = System.currentTimeMillis()
            val timeStr = timeFormat.format(Date(now))
            val initialDataPoint = DataPoint(timeStr, currentState.currentLux)
            
            _state.value = currentState.copy(
                isRecording = true,
                historyData = listOf(initialDataPoint),
                selectedRecordIndex = null
            )
        }
    }

    fun saveRecord(label: String) {
        val currentState = _state.value
        if (label.isNotBlank()) {
            val now = Date()
            val dateFormat = SimpleDateFormat("M/d HH:mm", Locale.getDefault())
            val timeStr = dateFormat.format(now)
            
            val measurement = LightMeasurement(
                lux = currentState.currentLux,
                timestamp = now.time,
                label = label.trim(),
                historyData = if (currentState.historyData.isNotEmpty()) currentState.historyData else null
            )
            
            val newMeasurements = listOf(measurement) + currentState.measurements
            _state.value = currentState.copy(
                measurements = newMeasurements
            )
            
            storageManager?.saveMeasurements(newMeasurements)
        }
    }

    fun deleteRecord(index: Int) {
        val currentState = _state.value
        val newMeasurements = currentState.measurements.filterIndexed { i, _ -> i != index }
        _state.value = currentState.copy(
            measurements = newMeasurements,
            selectedRecordIndex = if (currentState.selectedRecordIndex == index) null else currentState.selectedRecordIndex
        )
        storageManager?.saveMeasurements(newMeasurements)
    }

    fun clearHistory() {
        _state.value = _state.value.copy(
            measurements = emptyList(),
            historyData = emptyList(),
            selectedRecordIndex = null
        )
    }

    fun selectRecord(index: Int) {
        _state.value = _state.value.copy(selectedRecordIndex = index)
    }

    fun clearRecordSelection() {
        _state.value = _state.value.copy(selectedRecordIndex = null)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_LIGHT) {
                val rawLux = it.values[0]
                val settings = _state.value.settings
                
                val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                val calibration = getCalibrationForLightSource(settings, rawLux, hour)
                val calibratedLux = (rawLux * calibration.multiplier + calibration.offset).coerceAtLeast(0f)
                
                val currentState = _state.value
                val newState = currentState.copy(currentLux = calibratedLux)
                
                if (currentState.isRecording) {
                    val now = System.currentTimeMillis()
                    if (now - lastRecordTime >= 500) {
                        lastRecordTime = now
                        val timeStr = timeFormat.format(Date(now))
                        val newDataPoint = DataPoint(timeStr, calibratedLux)
                        val newHistoryData = (currentState.historyData + newDataPoint).takeLast(1000)
                        
                        _state.value = newState.copy(historyData = newHistoryData)
                    } else {
                        _state.value = newState
                    }
                } else {
                    _state.value = newState
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(this)
    }

    fun updateSettings(settings: AppSettings) {
        _state.value = _state.value.copy(settings = settings)
    }

    fun setTheme(themeMode: ThemeMode) {
        val currentSettings = _state.value.settings
        val newSettings = currentSettings.copy(theme = themeMode)
        _state.value = _state.value.copy(settings = newSettings)
        storageManager?.saveSettings(newSettings)
    }

    fun setCalibration(multiplier: Float, offset: Float) {
        val currentSettings = _state.value.settings
        val newSettings = currentSettings.copy(
            calibrationMultiplier = multiplier,
            calibrationOffset = offset
        )
        _state.value = _state.value.copy(settings = newSettings)
        storageManager?.saveSettings(newSettings)
    }

    fun setCalibrationMode(mode: CalibrationMode) {
        val currentSettings = _state.value.settings
        val newSettings = currentSettings.copy(calibrationMode = mode)
        _state.value = _state.value.copy(settings = newSettings)
        storageManager?.saveSettings(newSettings)
    }

    fun setManualLightSource(source: LightSource) {
        val currentSettings = _state.value.settings
        val newSettings = currentSettings.copy(manualLightSource = source)
        _state.value = _state.value.copy(settings = newSettings)
        storageManager?.saveSettings(newSettings)
    }

    fun setLightCalibration(source: LightSource, multiplier: Float, offset: Float) {
        val currentSettings = _state.value.settings
        val newSettings = when (source) {
            LightSource.LED -> currentSettings.copy(ledCalibration = LightCalibration(multiplier, offset, currentSettings.ledCalibration.ppfdConversionFactor))
            LightSource.DIFFUSED -> currentSettings.copy(diffusedCalibration = LightCalibration(multiplier, offset, currentSettings.diffusedCalibration.ppfdConversionFactor))
            LightSource.DIRECT -> currentSettings.copy(directCalibration = LightCalibration(multiplier, offset, currentSettings.directCalibration.ppfdConversionFactor))
            LightSource.SOURCE_4 -> currentSettings.copy(source4Calibration = LightCalibration(multiplier, offset, currentSettings.source4Calibration.ppfdConversionFactor))
            LightSource.SOURCE_5 -> currentSettings.copy(source5Calibration = LightCalibration(multiplier, offset, currentSettings.source5Calibration.ppfdConversionFactor))
        }
        _state.value = _state.value.copy(settings = newSettings)
        storageManager?.saveSettings(newSettings)
    }

    fun setLightPPFD(source: LightSource, ppfdFactor: Float) {
        val currentSettings = _state.value.settings
        val newSettings = when (source) {
            LightSource.LED -> currentSettings.copy(ledCalibration = currentSettings.ledCalibration.copy(ppfdConversionFactor = ppfdFactor))
            LightSource.DIFFUSED -> currentSettings.copy(diffusedCalibration = currentSettings.diffusedCalibration.copy(ppfdConversionFactor = ppfdFactor))
            LightSource.DIRECT -> currentSettings.copy(directCalibration = currentSettings.directCalibration.copy(ppfdConversionFactor = ppfdFactor))
            LightSource.SOURCE_4 -> currentSettings.copy(source4Calibration = currentSettings.source4Calibration.copy(ppfdConversionFactor = ppfdFactor))
            LightSource.SOURCE_5 -> currentSettings.copy(source5Calibration = currentSettings.source5Calibration.copy(ppfdConversionFactor = ppfdFactor))
        }
        _state.value = _state.value.copy(settings = newSettings)
        storageManager?.saveSettings(newSettings)
    }

    fun toggleDisplayMode() {
        val currentMode = _state.value.displayMode
        val newMode = if (currentMode == "lux") "ppfd" else "lux"
        _state.value = _state.value.copy(displayMode = newMode)
    }

    fun calculateLux(
        roomLength: Float,
        roomWidth: Float,
        roomHeight: Float,
        numLights: Int,
        lightType: LightType,
        utilizationFactor: Float,
        maintenanceFactor: MaintenanceFactor,
        luxMode: String,
        customLux: Float,
        sceneType: RoomType
    ) {
        val area = roomLength * roomWidth
        
        val targetLux = when (luxMode) {
            "recommended" -> sceneType.recommendedLux.toFloat()
            "standard" -> sceneType.standardLux.toFloat()
            else -> customLux
        }
        
        if (area > 0 && roomHeight > 0 && numLights > 0 && targetLux > 0) {
            val heightFactor = 1f - (roomHeight - 2.8f) * 0.02f
            val utilization = heightFactor * maintenanceFactor.value.toFloat() * utilizationFactor
            
            val totalLumens = (targetLux * area) / utilization
            val requiredLumensPerLight = totalLumens / numLights
            val luxPerLight = (requiredLumensPerLight * utilization) / area
            
            _state.value = _state.value.copy(
                calculationResult = CalculationResult(
                    requiredLumensPerLight = requiredLumensPerLight.toInt(),
                    totalLumens = totalLumens.toInt(),
                    luxPerLight = luxPerLight.toInt()
                )
            )
        }
    }

    fun clearCalculationResult() {
        _state.value = _state.value.copy(calculationResult = null)
    }

    fun selectScene(scene: SceneType) {
        _state.value = _state.value.copy(selectedScene = scene, showSceneSelector = false)
        storageManager?.saveSelectedSceneId(scene.id)
    }

    fun toggleSceneSelector() {
        _state.value = _state.value.copy(showSceneSelector = !_state.value.showSceneSelector)
    }

    fun selectPlant(plant: Plant) {
        _state.value = _state.value.copy(selectedPlant = plant, showPlantSelector = false)
        storageManager?.saveSelectedPlantId(plant.id)
    }
    
    fun updatePlant(plant: Plant) {
        _state.value = _state.value.copy(selectedPlant = plant)
        storageManager?.saveSelectedPlantId(plant.id)
        
        val currentCustomPlants = storageManager?.loadCustomPlants() ?: emptyMap()
        val updatedCustomPlants = currentCustomPlants.toMutableMap()
        updatedCustomPlants[plant.id] = plant
        storageManager?.saveCustomPlants(updatedCustomPlants)
        DataRepository.setCustomPlants(updatedCustomPlants)
    }

    fun togglePlantSelector() {
        _state.value = _state.value.copy(showPlantSelector = !_state.value.showPlantSelector)
    }

    fun getLuxLevel(lux: Float): String {
        return when {
            lux < 100 -> "极暗"
            lux < 300 -> "较暗"
            lux < 500 -> "适中"
            lux < 750 -> "明亮"
            else -> "很亮"
        }
    }

    fun getLuxStatusForPlant(lux: Float, plant: Plant): String {
        return when {
            lux < plant.minLux -> "光照不足"
            lux > plant.maxLux -> "光照过强"
            else -> "光照适宜"
        }
    }

    fun getLuxStatusForScene(lux: Float, scene: SceneType): String {
        val range = scene.recommendedLux.split("-").map { it.toInt() }
        return when {
            lux < range[0] -> "照度偏低"
            lux > range[1] -> "照度偏高"
            else -> "照度适宜"
        }
    }
}
