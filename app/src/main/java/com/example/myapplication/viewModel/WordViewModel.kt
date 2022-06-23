package com.example.myapplication.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Word
import com.example.myapplication.recyclerview.WordAdapter

class WordViewModel: ViewModel() {
    lateinit var wordListLiveData: MutableLiveData<ArrayList<Word>>
    lateinit var wordList: ArrayList<Word>
    lateinit var wordAdapter: WordAdapter
    init {
        wordList = ArrayList()
        wordListLiveData = MutableLiveData()
        repeat(5){ it ->
            wordList.add(Word(eng = "eng$it", kor = "kor$it"))
        }
        wordListLiveData.value = wordList

        wordAdapter = WordAdapter(wordListLiveData.value!!)
        wordAdapter.listener = object : WordAdapter.OnClickListener{
            override fun onClick(word: Word, position: Int) {
                changeVisibility(position, word.visibility)
            }

        }
    }

    fun changePosition(oldPosition: Int, newPosition: Int) {
        wordList[oldPosition] = wordList[newPosition].also { wordList[newPosition] = wordList[oldPosition] }
        wordListLiveData.value = wordList
    }

    fun deleteWord(position: Int) {
        wordList.removeAt(position)
        wordListLiveData.value = wordList
    }
    fun changeVisibility(position: Int, visibility : Int) {
        when(visibility){
            View.VISIBLE -> wordList[position].visibility = View.GONE
            View.GONE -> wordList[position].visibility = View.VISIBLE
        }
        wordListLiveData.value = wordList
    }
    fun addWord() {
        val position = wordAdapter.itemCount
        wordList.add(Word(eng = "eng$position", kor = "kor$position"))
        wordListLiveData.value = wordList
    }
}