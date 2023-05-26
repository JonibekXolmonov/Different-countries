package com.find.differentcountries.ui.screen.settings

import android.content.Context
import android.media.AudioManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.find.differentcountries.ui.utils.Contants.SOUND
import com.find.differentcountries.ui.utils.Contants.SOUND_VOLUME
import com.find.differentcountries.ui.utils.Contants.VIBRATION_AMPLITUDE
import com.find.differentcountries.ui.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPref: SharedPref, @ApplicationContext val context: Context
) : ViewModel() {

    private lateinit var audioManager: AudioManager

    private var _vibrationAmplitude = mutableStateOf(0)
    val vibrationAmplitude get() = _vibrationAmplitude

    private var _soundVolume = mutableStateOf(0)
    val soundVolume get() = _soundVolume

    private fun getSoundAndVibrationLevel() {
        viewModelScope.launch {
            _soundVolume.value = sharedPref.getInt(SOUND_VOLUME)
            _vibrationAmplitude.value = sharedPref.getInt(VIBRATION_AMPLITUDE)
        }
    }

    fun setVibrationLevel(amplitude: Float) {
        viewModelScope.launch {
            _vibrationAmplitude.value = amplitude.toInt()
            sharedPref.saveInt(VIBRATION_AMPLITUDE, amplitude.toInt())
        }
    }

    fun setVolume(volume: Float) {
        viewModelScope.launch {
            _soundVolume.value = volume.toInt()
            sharedPref.saveInt(SOUND_VOLUME, volume.toInt())
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume.toInt(), 0)
        }
    }

    init {
        viewModelScope.launch {
            audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            getSoundAndVibrationLevel()
        }
    }
}