package com.example.sensorlister

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class SensorDetailActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Auslesen der übergebenen Extras aus der MainActivity mit Standardwerten
        val name = intent.getStringExtra("NAME") ?: "Unbekannt"
        val vendor = intent.getStringExtra("VENDOR") ?: "Unbekannt"
        val version = intent.getIntExtra("VERSION", 0)
        val type = intent.getIntExtra("TYPE", 0)
        val stringType = intent.getStringExtra("STRING_TYPE") ?: "Unbekannt"
        val power = intent.getFloatExtra("POWER", 0.0f)
        val maxRange = intent.getFloatExtra("MAX_RANGE", 0.0f)
        val resolution = intent.getFloatExtra("RESOLUTION", 0.0f)
        val minDelay = intent.getIntExtra("MIN_DELAY", 0)

        // Haupt-Layout erstellen
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 100, 60, 60)
            setBackgroundColor(Color.BLACK)
        }

        // Titel-TextView
        val titleView = TextView(this).apply {
            text = "Sensor Details\n"
            textSize = 24f
            setTextColor(Color.CYAN)
        }
        layout.addView(titleView)

        // Hilfsfunktion zur Erstellung von formatierten Key-Value-Einträgen
        fun addDetailRow(label: String, value: String, color: Int = Color.WHITE) {
            val labelView = TextView(this).apply {
                text = label
                textSize = 14f
                setTextColor(Color.GRAY)
                setPadding(0, 15, 0, 0)
            }
            val valueView = TextView(this).apply {
                text = value
                textSize = 18f
                setTextColor(color)
                setPadding(0, 0, 0, 15)
            }
            layout.addView(labelView)
            layout.addView(valueView)
        }

        // Details zur UI hinzufügen
        addDetailRow("Name", name, Color.WHITE)
        addDetailRow("Hersteller (Vendor)", vendor, Color.YELLOW)
        addDetailRow("Klassen-Typ (Int)", type.toString())
        addDetailRow("Typbezeichnung (String)", stringType, Color.MAGENTA)
        addDetailRow("Version", version.toString())
        addDetailRow("Stromverbrauch", "$power mA", Color.GREEN)
        addDetailRow("Maximaler Messbereich", maxRange.toString())
        addDetailRow("Auflösung", resolution.toString())
        addDetailRow("Minimale Verzögerung", "$minDelay µs")

        // Button, um die Live-Daten-Ansicht zu öffnen
        val liveButton = Button(this).apply {
            text = "Live-Daten anzeigen"
            setBackgroundColor(Color.DKGRAY)
            setTextColor(Color.WHITE)

            // Abstand nach oben, damit der Button nicht direkt am letzten Text klebt
            setPadding(0, 20, 0, 20)

            // Klick-Listener startet die neue SensorLiveActivity
            setOnClickListener {
                val intent = Intent(this@SensorDetailActivity, SensorLiveActivity::class.java).apply {
                    putExtra("SENSOR_TYPE", type) // Wichtig, damit die Live-Activity weiß, welcher Sensor abgehört wird
                }
                startActivity(intent)
            }
        }

        // Füge einen leeren Abstandshalter-TextView vor dem Button ein, für besseres Layout
        val spacer = TextView(this).apply {
            text = ""
            setPadding(0, 20, 0, 20)
        }
        layout.addView(spacer)
        layout.addView(liveButton)

        // Verpacken des gesamten Layouts in eine ScrollView, falls der Bildschirm zu klein ist
        val scrollView = ScrollView(this).apply {
            addView(layout)
        }

        setContentView(scrollView)
    }
}