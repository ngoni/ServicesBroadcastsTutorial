package com.scribblex.tutorial.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val context: Context) {

    companion object {
        // setup DataStore Preferences
        private val Context.counterDataStore: DataStore<Preferences> by preferencesDataStore(name = "counter_preferences")
        private val KEY_COUNTER = intPreferencesKey("key_counter")
    }

    val counter: Flow<Int> = context.counterDataStore.data.map { preferences ->
        preferences[KEY_COUNTER] ?: 0
    }

    suspend fun incrementCounter() {
        context.counterDataStore.edit { preferences ->
            val currentCounter = preferences[KEY_COUNTER] ?: 0
            preferences[KEY_COUNTER] = currentCounter + 1
        }
    }
}