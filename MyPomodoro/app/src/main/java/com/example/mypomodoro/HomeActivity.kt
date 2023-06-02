package com.example.mypomodoro



import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val monthYearTextView: TextView = findViewById(R.id.monthYearTextView)
        val calendar = Calendar.getInstance()
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val currentMonthYear = monthYearFormat.format(calendar.time)
        monthYearTextView.text = currentMonthYear

        val gridLayout: GridLayout = findViewById(R.id.gridlayout)
        val numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1..numberOfDays) {
            val dayTextView = TextView(this)
            dayTextView.text = day.toString()
            dayTextView.gravity = Gravity.CENTER
            dayTextView.layoutParams = GridLayout.LayoutParams().apply {
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            dayTextView.textSize = 30f
            dayTextView.setTypeface(null, Typeface.BOLD_ITALIC)


            dayTextView.setOnClickListener {
                val selectedDate = day.toString()
                val selectedMonth = calendar.get(Calendar.MONTH) + 1 // Adding 1 because Calendar.MONTH is zero-based
                val selectedYear = calendar.get(Calendar.YEAR)

                val bundle = Bundle().apply {
                    putString("selectedDate", selectedDate)
                    putString("selectedMonth", selectedMonth.toString())
                    putString("selectedYear", selectedYear.toString())
                }

                val intent = Intent(this@HomeActivity, ToDoListActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            gridLayout.addView(dayTextView)
        }
    }
}
