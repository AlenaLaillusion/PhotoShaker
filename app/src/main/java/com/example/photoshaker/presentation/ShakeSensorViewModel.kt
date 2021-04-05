package com.example.photoshaker.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoshaker.App

class ShakeSensorViewModel : ViewModel(), SensorEventListener {

    private val _shakeSensorLiveData = MutableLiveData<Boolean>()
    val shakeSensorLiveData: LiveData<Boolean> get() = _shakeSensorLiveData

    private lateinit var sensorManager: SensorManager

    // todo Review's hint: can be private, IDE hints about it ;)
    var lastUpdate = 0L

    fun setUpSensor() {
        sensorManager = App.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val curTime = System.currentTimeMillis()

            if ((curTime - lastUpdate) > DIFF) {
                lastUpdate = curTime
                val speed = Math.sqrt(
                    Math.pow(x.toDouble(), POW) +
                            Math.pow(y.toDouble(), POW) +
                            Math.pow(z.toDouble(), POW)
                ) - SensorManager.GRAVITY_EARTH

                // todo Review's hint: not need to use so long 'if' condition ;)
                val isShake = if (speed > SHAKE_THRESHOLD) true else false

                _shakeSensorLiveData.value = isShake

                Log.d(
                    ShakeSensorViewModel::class.java.simpleName,
                    "x= $x, curTime= $curTime, isShake= $isShake, speed= $speed"
                )
            }
        }
    }

    fun stopSensor() {
        sensorManager.unregisterListener(this)
    }
}

// todo Review's hint: it's not required but I would prefer to see this constants in the top of the class/file
const val SHAKE_THRESHOLD = 1.5
const val POW = 2.0
const val DIFF = 3000