package com.example.sensorlister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.graphics.Color

class MainActivity : Activity() { // Wir nutzen die Basis-Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 100, 60, 60)

        }

        val title = TextView(this).apply {
            text = "Sensor Liste (${deviceSensors.size} gefunden):\n"
            textSize = 22f
            setTextColor(Color.WHITE)
        }
        layout.addView(title)

        for (s in deviceSensors) {

            val tv = TextView(this).apply {
                text = "• ${s.name}"
                setTextColor(Color.WHITE)
                setPadding(0, 10, 0, 10)
                setOnClickListener {
                    val intent = Intent(this@MainActivity, SensorDetailActivity::class.java).apply {
                        putExtra("NAME", s.name)
                        putExtra("VENDOR", s.vendor)
                        putExtra("VERSION", s.version)
                        putExtra("TYPE", s.type)
                        putExtra("STRING_TYPE", s.stringType)
                        putExtra("POWER", s.power) // in mA
                        putExtra("MAX_RANGE", s.maximumRange)
                        putExtra("RESOLUTION", s.resolution)
                        putExtra("MIN_DELAY", s.minDelay) // in Mikrosekunden
                    }
                    startActivity(intent)
                }
            }
            layout.addView(tv)
        }

        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }
}