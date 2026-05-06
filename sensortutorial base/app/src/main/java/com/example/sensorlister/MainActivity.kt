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


        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }
}