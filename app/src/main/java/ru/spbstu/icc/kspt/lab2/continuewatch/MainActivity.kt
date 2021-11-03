package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isOnScreen = true
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    private var backgroundThread = Thread {
        while (true) {
            if (isOnScreen) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.secondsElapsed, secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onStart() {
        super.onStart()
        isOnScreen = true
    }

    override fun onStop() {
        super.onStop()
        isOnScreen = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(SECONDS, secondsElapsed)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.run {
            getInt(SECONDS)
        }
    }

    companion object {
        const val SECONDS = "seconds"
    }
}
