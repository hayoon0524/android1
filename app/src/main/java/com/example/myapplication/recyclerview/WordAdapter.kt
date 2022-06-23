package com.example.myapplication.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Word
import com.example.myapplication.databinding.WordRowBinding

class WordAdapter(private var wordList: ArrayList<Word>) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onClick(word: Word, position: Int)
    }
    var listener: OnClickListener? = null
    inner class ViewHolder(private val binding: WordRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eng: String, kor: String, visibility:Int) {
            binding.run {
                engText.text = eng
                korText.text = kor
                korText.visibility = visibility
            }
        }
        init {
            binding.engText.setOnClickListener {
                listener?.onClick(wordList[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = WordRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wordList[position].eng,wordList[position].kor, wordList[position].visibility)
        //holder.binding.engText.text = wordList[position].eng
        //holder.binding.korText.text = wordList[position].kor
    }

    override fun getItemCount(): Int = wordList.size
}