package com.example.photoshaker.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoshaker.App

class ShakeSensorViewModel : ViewModel(), SensorEventListener {

    private val _shakeSensorLiveData = MutableLiveData<Boolean>()
    val shakeSensorLiveData get() = _shakeSensorLiveData

    private lateinit var sensorManager: SensorManager

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
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val curTime = System.currentTimeMillis()

            if ((curTime - lastUpdate) > 10000) {
                lastUpdate = curTime
                val speed = Math.sqrt(Math.pow(x.toDouble(), 2.0) +
                        Math.pow(y.toDouble(), 2.0) +
                        Math.pow(z.toDouble(), 2.0)) - SensorManager.GRAVITY_EARTH

                val isShake = if (speed > SHAKE_THRESHOLD) true else false

                _shakeSensorLiveData.value = isShake

                Log.d(ShakeSensorViewModel::class.java.simpleName, "x= $x, curTime= $curTime, isShake= $isShake, speed= $speed")
            }
        }
    }
    fun stopSensor() {
        sensorManager.unregisterListener(this)
    }
}
const val SHAKE_THRESHOLD = 1