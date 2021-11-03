package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences

    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.secondsElapsed, secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        sharedPref = getPreferences(MODE_PRIVATE)
        backgroundThread.start()
    }

    override fun onStart() {
        super.onStart()
        secondsElapsed = sharedPref.getInt(SECONDS, 0)
    }

    override fun onStop() {
        with(sharedPref.edit()) {
            putInt(SECONDS, secondsElapsed)
            apply()
        }
        super.onStop()
    }

    companion object {
        const val SECONDS = "seconds"
    }

}
