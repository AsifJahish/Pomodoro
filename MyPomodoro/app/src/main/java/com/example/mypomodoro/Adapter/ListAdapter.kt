package com.example.mypomodoro.Adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypomodoro.R
import com.example.mypomodoro.Room.DB
import com.example.mypomodoro.Room.Listitem
import com.example.mypomodoro.StartActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListAdapter(private val context: Context, private var itemList: List<Listitem>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.titleB.text = currentItem.description

        // Update the button text based on the condition
        if (currentItem.condition == "Done") {
            holder.doneButton.text = "Done"
        } else {
            holder.doneButton.text = "Not Done"
        }

        if (currentItem.condition == "Done") {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.PaleBlue
                )
            )
        }

        holder.doneButton.setOnClickListener {
            // Update the condition of the current item to "Done"
            currentItem.condition = "Done"

            // Save the updated item in the Room database
            GlobalScope.launch {
                updateItem(currentItem)
            }

            notifyItemChanged(position) // Refresh the view to update the button text and item color
        }

        holder.titleB.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateList(newItemList: List<Listitem>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    private suspend fun updateItem(item: Listitem) {
        withContext(Dispatchers.IO) {
            // Update the item in the Room database
            val itemDao = DB.getDatabase(context).itemDao()
            itemDao.updateList(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleB: TextView = itemView.findViewById(R.id.list_item)
        val doneButton: Button = itemView.findViewById(R.id.done)

        init {
            itemView.setOnClickListener {
                // Handle click on the item view if needed
            }
        }
    }
}
