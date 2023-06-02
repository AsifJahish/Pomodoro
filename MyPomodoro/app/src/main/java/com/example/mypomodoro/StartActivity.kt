package com.example.mypomodoro



import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var timerOneTextView: TextView
    private lateinit var timerTwoTextView: TextView
    private var isTimerOneRunning: Boolean = false
    private var isTimerTwoRunning: Boolean = false
    private var isTimerOnePaused: Boolean = false
    private var isTimerTwoPaused: Boolean = false
    private var timerOneRemainingMillis: Long = 0
    private var timerTwoRemainingMillis: Long = 0

    private lateinit var startfifty: Button
    private lateinit var startTen: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        timerOneTextView = findViewById(R.id.timerChronometer)
        timerTwoTextView = findViewById(R.id.timerChronometer10)

        startfifty= findViewById(R.id.start50)
        startTen= findViewById(R.id.start10)

        timerOneTextView.setOnClickListener(this)
        timerTwoTextView.setOnClickListener(this)

        startfifty.setOnClickListener(this)
        startTen.setOnClickListener(this)

    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.timerChronometer -> {
                if (!isTimerOneRunning) {
                    startTimerOne()
                } else {
                    if (!isTimerOnePaused) {
                        pauseTimerOne()
                    } else {
                        resumeTimerOne()
                    }
                }
            }
            R.id.timerChronometer10 -> {
                if (!isTimerTwoRunning) {
                    startTimerTwo()
                } else {
                    if (!isTimerTwoPaused) {
                        pauseTimerTwo()
                    } else {
                        resumeTimerTwo()
                    }
                }
            }
            R.id.start50 -> {
                if (!isTimerOneRunning) {
                    startTimerOne()
                } else {
                    if (!isTimerOnePaused) {
                        pauseTimerOne()
                    } else {
                        resumeTimerOne()
                    }
                }
            }
            R.id.start10 -> {
                if (!isTimerTwoRunning) {
                    startTimerTwo()
                } else {
                    if (!isTimerTwoPaused) {
                        pauseTimerTwo()
                    } else {
                        resumeTimerTwo()
                    }
                }
            }
        }
    }


    private fun startTimerOne() {
        val fiftyMinutesInMillis: Long = 50 * 60 * 1000

        countDownTimer = object : CountDownTimer(if (isTimerOnePaused) timerOneRemainingMillis else fiftyMinutesInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerOneRemainingMillis = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                // Update your UI with the remaining time
                timerOneTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // Timer finished, handle any necessary actions
                timerOneFinished()
            }
        }

        countDownTimer.start()
        isTimerOneRunning = true
        isTimerOnePaused = false
    }


    private fun pauseTimerOne() {
        countDownTimer.cancel()
        isTimerOnePaused = true
    }

    private fun resumeTimerOne() {
        startTimerOne()
        isTimerOnePaused = false
    }

    private fun timerOneFinished() {
        // Handle any necessary actions when the timer finishes
        isTimerOneRunning = false
    }
    private fun startTimerTwo() {
        val tenMinutesInMillis: Long = 10 * 60 * 1000

        countDownTimer = object : CountDownTimer(if (isTimerTwoPaused) timerTwoRemainingMillis else tenMinutesInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTwoRemainingMillis = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                // Update your UI with the remaining time
                timerTwoTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // Timer finished, handle any necessary actions
                timerTwoFinished()
            }
        }

        countDownTimer.start()
        isTimerTwoRunning = true
        isTimerTwoPaused = false
    }

    private fun stopTimerTwo() {
        countDownTimer.cancel()
        isTimerTwoRunning = false
        isTimerTwoPaused = false
    }
    private fun pauseTimerTwo() {
        countDownTimer.cancel()
        isTimerTwoPaused = true
    }

    private fun resumeTimerTwo() {
        startTimerTwo()
        isTimerTwoPaused = false
    }

    private fun timerTwoFinished() {
        // Handle any necessary actions when the timer finishes
        isTimerTwoRunning = false
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel() // Cancel the timer to avoid leaks
    }
}
