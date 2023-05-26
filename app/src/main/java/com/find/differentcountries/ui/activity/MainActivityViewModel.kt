package com.find.differentcountries.ui.activity

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.find.differentcountries.ui.service.BackgroundMusicService
import com.find.differentcountries.ui.utils.Contants.SOUND
import com.find.differentcountries.ui.utils.Contants.VIBRATION
import com.find.differentcountries.ui.utils.Contants.VIBRATION_AMPLITUDE
import com.find.differentcountries.ui.utils.SharedPref
import kotlinx.coroutines.launch


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val sharedPref = SharedPref(context)

    fun startService() {
        val serviceIntent = Intent(context, BackgroundMusicService::class.java)
        context.startService(serviceIntent)
    }

    fun stopService() {
        val serviceIntent = Intent(context, BackgroundMusicService::class.java)
        context.startService(serviceIntent)
    }

    fun playBackgroundMusic() {
            startService()
    }

    fun pauseBackgroundMusic() {
        startService()
    }

    fun vibrate() {
        val amplitude = sharedPref.getInt(VIBRATION_AMPLITUDE)
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            v!!.vibrate(
                VibrationEffect.createOneShot(
                    40 * amplitude.toLong() + 10,
                    if (amplitude == 0) VibrationEffect.DEFAULT_AMPLITUDE else amplitude
                )
            )
        } else {
            v!!.vibrate(60)
        }
    }
}
