package com.example.lightmeter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lightmeter.data.*
import com.example.lightmeter.ui.components.*
import com.example.lightmeter.ui.theme.LightMeterTheme

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.values.all { it }
            if (!allGranted) {
                Toast.makeText(this, "需要存储权限才能使用保存记录功能", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkAndRequestPermissions()
        setContent {
            LightMeterTheme {
                LightMeterApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun setStatusBarColor(isDarkTheme: Boolean) {
        val decorView = window.decorView
        val flags = decorView.systemUiVisibility
        if (isDarkTheme) {
            decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LightMeterApp(
    viewModel: LightMeterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val view = LocalView.current
    var currentTab by remember { mutableStateOf(TabType.MEASUREMENT) }
    
    LaunchedEffect(Unit) {
        viewModel.initialize(context)
        viewModel.startSensor(context)
    }
    
    LaunchedEffect(state.settings.theme) {
        val activity = context as? MainActivity
        activity?.setStatusBarColor(state.settings.theme == ThemeMode.DARK)
    }
    
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    
    LightMeterTheme(darkTheme = state.settings.theme == ThemeMode.DARK) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentTab) {
                TabType.MEASUREMENT -> {
                    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
                    val lightSource = getCurrentLightSource(state.settings, state.currentLux, hour)
                    MeasurementScreen(
                        currentLux = state.currentLux,
                        isRecording = state.isRecording,
                        historyData = state.historyData,
                        measurements = state.measurements,
                        selectedRecordIndex = state.selectedRecordIndex,
                        onToggleRecording = { viewModel.toggleRecording() },
                        onSaveRecord = { viewModel.saveRecord(it) },
                        onDeleteRecord = { viewModel.deleteRecord(it) },
                        onSelectRecord = { viewModel.selectRecord(it) },
                        onClearRecordSelection = { viewModel.clearRecordSelection() },
                        lightSource = lightSource
                    )
                }
                TabType.CALCULATOR -> {
                    CalculatorScreen(
                        result = state.calculationResult,
                        onCalculate = { length, width, height, lights, lightType, utilizationFactor, maintenanceFactor, luxMode, customLux, roomType ->
                            viewModel.calculateLux(length, width, height, lights, lightType, utilizationFactor, maintenanceFactor, luxMode, customLux, roomType)
                        },
                        onClearResult = { viewModel.clearCalculationResult() }
                    )
                }
                TabType.PLANT -> {
                    val selectedPlant = state.selectedPlant ?: com.example.lightmeter.data.DataRepository.getAllPlants()[0]
                    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
                    val ppfdFactor = getPPFDConversionFactor(state.settings, state.currentLux, hour)
                    val lightSource = getCurrentLightSource(state.settings, state.currentLux, hour)
                    PlantScreen(
                        currentLux = state.currentLux,
                        selectedPlant = selectedPlant,
                        showSelector = state.showPlantSelector,
                        onToggleSelector = { viewModel.togglePlantSelector() },
                        onSelectPlant = { viewModel.selectPlant(it) },
                        onUpdatePlant = { viewModel.updatePlant(it) },
                        displayMode = state.displayMode,
                        ppfdConversionFactor = ppfdFactor,
                        onToggleDisplayMode = { viewModel.toggleDisplayMode() },
                        lightSource = lightSource
                    )
                }
                TabType.SCENE -> {
                    val selectedScene = state.selectedScene ?: com.example.lightmeter.data.DataRepository.scenes[0]
                    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
                    val lightSource = getCurrentLightSource(state.settings, state.currentLux, hour)
                    SceneScreen(
                        currentLux = state.currentLux,
                        selectedScene = selectedScene,
                        showSelector = state.showSceneSelector,
                        onToggleSelector = { viewModel.toggleSceneSelector() },
                        onSelectScene = { viewModel.selectScene(it) },
                        lightSource = lightSource
                    )
                }
                TabType.SETTINGS -> {
                    SettingsScreen(
                        settings = state.settings,
                        onThemeChange = { viewModel.setTheme(it) },
                        onCalibrationChange = { multiplier, offset ->
                            viewModel.setCalibration(multiplier, offset)
                        },
                        onCalibrationModeChange = { mode ->
                            viewModel.setCalibrationMode(mode)
                        },
                        onManualLightSourceChange = { source ->
                            viewModel.setManualLightSource(source)
                        },
                        onLightCalibrationChange = { source, multiplier, offset ->
                            viewModel.setLightCalibration(source, multiplier, offset)
                        },
                        onLightPPFDChange = { source, factor ->
                            viewModel.setLightPPFD(source, factor)
                        }
                    )
                }
            }
        }
    }
    }
}