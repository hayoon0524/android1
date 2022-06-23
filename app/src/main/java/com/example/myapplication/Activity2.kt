package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.Activity2Binding
import com.example.myapplication.recyclerview.WordItemTouchHelperCallback
import com.example.myapplication.viewModel.WordViewModel
import androidx.activity.viewModels as viewModels

//1. ListView
//2. RecyclerView
//3. DiffUtil
class Activity2 : AppCompatActivity() {
    lateinit var binding: Activity2Binding
    private val wordViewModel : WordViewModel by viewModels()
    private lateinit var  itemTouchHelper: ItemTouchHelper
    private val activity2Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            val str = result.data?.getStringExtra("info")
            Toast.makeText(this@Activity2,str, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = Activity2Binding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        Log.d("ABCDE", "onCreate: Activity2 ")
        setContentView(binding.root)

        intent.getStringExtra("myId")?.let{
            Toast.makeText(this@Activity2,it, Toast.LENGTH_SHORT).show()
        }
        init()
        setObserver()
        initLayout()
    }
    private fun init()
    {
        val backButton = binding.backBtn
        backButton.setOnClickListener {
//            val intent = Intent(this@Activity2, MainActivity::class.java)
//            val id = intent.getStringExtra("myId")
//            intent.putExtra("myId", id)
//            startActivity(intent)
//            finish()
            GlobalApplication.getInstance().putKeyValue("againLogin", true)
            val id = intent.getStringExtra("myId")
            val intent = Intent(this@Activity2, MainActivity::class.java)
            intent.putExtra("myId", id)
            activity2Launcher.launch(intent)
        }
        val addButton = binding.btn1
        addButton.setOnClickListener(){
            wordViewModel.addWord()
        }
    }
    private fun initLayout(){
        binding.run{
            recyclerView.run {
                layoutManager = LinearLayoutManager(this@Activity2, LinearLayoutManager.VERTICAL, false)
                adapter = wordViewModel.wordAdapter
            }
        }
        itemTouchHelper = ItemTouchHelper(WordItemTouchHelperCallback(wordViewModel))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setObserver(){
        wordViewModel.wordListLiveData.observe(this@Activity2){
            wordViewModel.wordAdapter.notifyDataSetChanged()
        }
    }

    override fun onStart() {
    super.onStart()
    Log.d("ABCDE", "onCreate: Activity2 onStart")
    }

    override fun onResume() {
    super.onResume()
    Log.d("ABCDE", "onCreate: Activity2 onResume")
    }

    override fun onPause() {
    super.onPause()
    Log.d("ABCDE", "onCreate: Activity2 onPause")
    }

    override fun onStop() {
    super.onStop()
    Log.d("ABCDE", "onCreate: Activity2 onStop")
    }

    override fun onDestroy() {
    super.onDestroy()
    Log.d("ABCDE", "onCreate: Activity2 onDestroy")
    }

}