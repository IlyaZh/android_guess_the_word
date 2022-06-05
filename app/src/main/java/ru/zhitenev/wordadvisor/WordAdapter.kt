package ru.zhitenev.wordadvisor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.zhitenev.wordadvisor.database.Word

class WordAdapter(private val words: List<Word>) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_template, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordViewModel = words[position]
        holder.wordView.text = wordViewModel.text
    }

    override fun getItemCount(): Int {
        return words.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val wordView: TextView = itemView.findViewById(R.id.wordView)
    }
}