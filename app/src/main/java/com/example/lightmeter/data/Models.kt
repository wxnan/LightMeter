package com.example.lightmeter.data

import java.io.Serializable

data class LightMeasurement(
    val lux: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val label: String? = null,
    val historyData: List<DataPoint>? = null
) : Serializable

data class DataPoint(
    val time: String,
    val lux: Float
) : Serializable

data class AppSettings(
    val theme: ThemeMode = ThemeMode.LIGHT,
    val calibrationMultiplier: Float = 1f,
    val calibrationOffset: Float = 0f,
    val calibrationMode: CalibrationMode = CalibrationMode.AUTO,
    val manualLightSource: LightSource = LightSource.LED,
    val ledCalibration: LightCalibration = LightCalibration(1f, 0f, 0.012f),
    val diffusedCalibration: LightCalibration = LightCalibration(1f, 0f, 0.0185f),
    val directCalibration: LightCalibration = LightCalibration(1f, 0f, 0.0185f),
    val source4Calibration: LightCalibration = LightCalibration(1f, 0f, 0.0185f),
    val source5Calibration: LightCalibration = LightCalibration(1f, 0f, 0.0185f)
) : Serializable

data class LightCalibration(
    val multiplier: Float = 1f,
    val offset: Float = 0f,
    val ppfdConversionFactor: Float = 0.0185f
) : Serializable

fun detectLightSource(lux: Float, hour: Int): LightSource {
    val isNight = hour < 6 || hour >= 18
    
    return if (isNight) {
        LightSource.LED
    } else {
        if (lux > 10000) {
            LightSource.DIRECT
        } else {
            LightSource.DIFFUSED
        }
    }
}

fun getCalibrationForLightSource(
    settings: AppSettings,
    lux: Float,
    hour: Int
): LightCalibration {
    val lightSource = if (settings.calibrationMode == CalibrationMode.AUTO) {
        detectLightSource(lux, hour)
    } else {
        settings.manualLightSource
    }
    
    return when (lightSource) {
        LightSource.LED -> settings.ledCalibration
        LightSource.DIFFUSED -> settings.diffusedCalibration
        LightSource.DIRECT -> settings.directCalibration
        LightSource.SOURCE_4 -> settings.source4Calibration
        LightSource.SOURCE_5 -> settings.source5Calibration
    }
}

fun getCurrentLightSource(
    settings: AppSettings,
    lux: Float,
    hour: Int
): LightSource {
    return if (settings.calibrationMode == CalibrationMode.AUTO) {
        detectLightSource(lux, hour)
    } else {
        settings.manualLightSource
    }
}

fun getPPFDConversionFactor(
    settings: AppSettings,
    lux: Float,
    hour: Int
): Float {
    val calibration = getCalibrationForLightSource(settings, lux, hour)
    return calibration.ppfdConversionFactor
}

enum class ThemeMode : Serializable {
    LIGHT, DARK
}

enum class LightSource : Serializable {
    LED,
    DIFFUSED,
    DIRECT,
    SOURCE_4,
    SOURCE_5
}

enum class CalibrationMode : Serializable {
    AUTO,
    MANUAL
}

data class CalculationResult(
    val requiredLumensPerLight: Int,
    val totalLumens: Int,
    val luxPerLight: Int
) : Serializable

data class SceneType(
    val id: String,
    val name: String,
    val recommendedLux: String,
    val colorTemp: String,
    val description: String,
    val tips: List<String>,
    val specialRequirements: List<String>?
)

data class Plant(
    val id: String,
    val name: String,
    val nameEn: String,
    val minLux: Int,
    val maxLux: Int,
    val minPPFD: Int,
    val maxPPFD: Int,
    val hoursPerDay: String,
    val lackSymptoms: List<String>,
    val excessSymptoms: List<String>,
    val tips: String
)

data class LightType(
    val value: String,
    val label: String,
    val efficiency: Float
)

data class MaintenanceFactor(
    val value: String,
    val label: String
)
