package com.example.sensorlister

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class SensorLiveActivity : Activity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var mySensor: Sensor? = null
    private lateinit var valueTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sensorType = intent.getIntExtra("SENSOR_TYPE", -1)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mySensor = sensorManager.getDefaultSensor(sensorType)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 100, 60, 60)
            setBackgroundColor(Color.BLACK)
        }

        val title = TextView(this).apply {
            text = "Live-Werte:\n${mySensor?.name ?: "Nicht gefunden"}"
            textSize = 20f
            setTextColor(Color.CYAN)
        }

        valueTextView = TextView(this).apply {
            text = "Warte auf Daten..."
            textSize = 24f
            setTextColor(Color.WHITE)
            setPadding(0, 50, 0, 0)
        }

        layout.addView(title)
        layout.addView(valueTextView)
        setContentView(layout)
    }

    // Wird aufgerufen, wenn neue Sensorwerte vorliegen
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        // Die Werte liegen meist in event.values (ein Array aus Floats)
        val v = event.values
        val dataString = when (v.size) {
            1 -> "Wert: ${v[0]}"
            3 -> "X: ${v[0]}\nY: ${v[1]}\nZ: ${v[2]}"
            else -> v.joinToString("\n")
        }
        valueTextView.text = dataString
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Hier könnte man auf Genauigkeitsänderungen reagieren
    }

    // WICHTIG: Sensor-Registrierung beim Starten/Stoppen der Activity
    override fun onResume() {
        super.onResume()
        mySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this) // Akku sparen!
    }
}