package com.example.lightmeter.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class StorageManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    companion object {
        private const val PREFS_NAME = "LightMeterPrefs"
        private const val KEY_MEASUREMENTS = "measurements"
        private const val KEY_SETTINGS = "settings"
        private const val KEY_SELECTED_PLANT_ID = "selected_plant_id"
        private const val KEY_SELECTED_SCENE_ID = "selected_scene_id"
        private const val KEY_CUSTOM_PLANTS = "custom_plants"
        private const val TAG = "StorageManager"
    }

    fun saveMeasurements(measurements: List<LightMeasurement>) {
        try {
            val json = gson.toJson(measurements)
            val itemCount: Int = measurements.size
            Log.d(TAG, "Saving measurements: $itemCount items")
            prefs.edit().putString(KEY_MEASUREMENTS, json).commit()
            Log.d(TAG, "Measurements saved successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving measurements", e)
        }
    }

    fun loadMeasurements(): List<LightMeasurement> {
        return try {
            val json = prefs.getString(KEY_MEASUREMENTS, null) ?: return emptyList()
            Log.d(TAG, "Loading measurements from storage")
            val type: java.lang.reflect.Type = object : TypeToken<List<LightMeasurement>>() {}.type
            val result = gson.fromJson<List<LightMeasurement>>(json, type) ?: emptyList()
            val itemCount: Int = result.size
            Log.d(TAG, "Loaded $itemCount measurements")
            result
        } catch (e: Exception) {
            Log.e(TAG, "Error loading measurements", e)
            emptyList()
        }
    }

    fun saveSettings(settings: AppSettings) {
        try {
            val json = gson.toJson(settings)
            prefs.edit().putString(KEY_SETTINGS, json).commit()
            Log.d(TAG, "Settings saved successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving settings", e)
        }
    }

    fun loadSettings(): AppSettings? {
        return try {
            val json = prefs.getString(KEY_SETTINGS, null) ?: return null
            gson.fromJson(json, AppSettings::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading settings", e)
            null
        }
    }

    fun saveSelectedPlantId(plantId: String) {
        prefs.edit().putString(KEY_SELECTED_PLANT_ID, plantId).commit()
    }

    fun loadSelectedPlantId(): String? {
        return prefs.getString(KEY_SELECTED_PLANT_ID, null)
    }

    fun saveSelectedSceneId(sceneId: String) {
        prefs.edit().putString(KEY_SELECTED_SCENE_ID, sceneId).commit()
    }

    fun loadSelectedSceneId(): String? {
        return prefs.getString(KEY_SELECTED_SCENE_ID, null)
    }
    
    fun saveCustomPlants(customPlants: Map<String, Plant>) {
        try {
            val json = gson.toJson(customPlants)
            prefs.edit().putString(KEY_CUSTOM_PLANTS, json).commit()
            Log.d(TAG, "Custom plants saved successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error saving custom plants", e)
        }
    }
    
    fun loadCustomPlants(): Map<String, Plant> {
        return try {
            val json = prefs.getString(KEY_CUSTOM_PLANTS, null) ?: return emptyMap()
            Log.d(TAG, "Loading custom plants from storage")
            val type = object : TypeToken<Map<String, Plant>>() {}.type
            gson.fromJson<Map<String, Plant>>(json, type) ?: emptyMap()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading custom plants", e)
            emptyMap()
        }
    }

    fun clearMeasurements() {
        prefs.edit().remove(KEY_MEASUREMENTS).commit()
    }
}