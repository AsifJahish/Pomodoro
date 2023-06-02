package com.example.mypomodoro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypomodoro.Adapter.ListAdapter
import com.example.mypomodoro.Room.DB
import com.example.mypomodoro.Room.Listitem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
/*import kotlinx.coroutines.runOnUiThread*/

class ToDoListActivity : AppCompatActivity() {

    private lateinit var write: EditText
    private lateinit var save: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var date: TextView
    private lateinit var itemDatabase: DB
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        write = findViewById(R.id.insert)
        save = findViewById(R.id.Save)
        recyclerView = findViewById(R.id.recycle)
        date = findViewById(R.id.Date)

        itemDatabase = DB.getDatabase(this)
        listAdapter = ListAdapter(this, ArrayList())

        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val selectedDate = intent.extras?.getString("selectedDate")
        val selectedMonth = intent.extras?.getString("selectedMonth")
        val selectedYear = intent.extras?.getString("selectedYear")

        date.text = "$selectedDate/$selectedMonth/$selectedYear"

        save.setOnClickListener {
            insertItem()
        }

        getList()
    }

    private fun insertItem() {
        val itemText = write.text.toString()
        val item = Listitem(date = date.text.toString(), description = itemText, condition = "Not Done")

        if (itemText.isNotEmpty()) {
            GlobalScope.launch {
                itemDatabase.itemDao().insert(item)
                getList()
            }
        } else {
            Toast.makeText(this, "Please write the text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getList() {
        GlobalScope.launch {
            val itemList = itemDatabase.itemDao().getItemsByDate(date.text.toString())
            runOnUiThread {
                listAdapter.updateList(itemList)
            }
        }
    }
}
