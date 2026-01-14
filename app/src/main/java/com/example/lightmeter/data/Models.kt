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
    val ppfdConversionFactor: Float = 0.0185f
) : Serializable

enum class ThemeMode : Serializable {
    LIGHT, DARK
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
