package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.User
import com.google.android.material.textfield.TextInputEditText
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewModel.UserViewModel

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val  viewModel: UserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UserViewModel(GlobalApplication.userRepository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        if (GlobalApplication.getInstance().getValue("isLogin") && !GlobalApplication.getInstance().getValue("againLogin"))
        {
            startActivity(Intent(this@MainActivity, Activity2::class.java))
            finish()
        }

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ABCDE", "onCreate: MainActivity ")

        viewModel.allUser.observe(this@MainActivity) { user ->
            Log.d("dsfdsaf", "onCreate: $user")
        }

        init()
    }
    private fun init()
    {
        val loginButton = binding.loginBtn
            //findViewById<Button>(R.id.login_btn)
        val signButton = binding.signBtn
        val idEditText = binding.idEditText
        val pwEditText = binding.pwEditText

        viewModel.curUserPw.observe(this@MainActivity) {
            if (it != "") {
                if (it == binding.pwEditText.text.toString()) {
                    GlobalApplication.getInstance().putKeyValue("isLogin", true)
                    GlobalApplication.getInstance().putKeyValue("againLogin", false)
                    val intent = Intent(this@MainActivity, Activity2::class.java)
                    intent.putExtra("myId", idEditText.text.toString())
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_SHORT).show()
                }
                else {
                    dialog("????????? ??????.")
                }
            }
        }
        if (GlobalApplication.getInstance().getValue("againLogin"))
        {
            val logId = intent.getStringExtra("myId")
            idEditText.setText(logId)
        }
        binding.run {
            loginButton.setOnClickListener {
                val id = idEditText.text.toString()
                val pw = pwEditText.text.toString()
                if (isEmpty(id, pw)) {
                    //val sharedPref = getSharedPreferences("user info" , MODE_PRIVATE)
                    //val editor = sharedPref.edit()

                    if (viewModel.findUserCount(id) == 1) {
                        viewModel.findPW(id)
//                        if (viewModel.curUserPw.equals(pw)) {
//                            GlobalApplication.getInstance().putKeyValue("isLogin", true)
//                            val intent = Intent(this@MainActivity, Activity2::class.java)
//                            intent.putExtra("myId", id)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            dialog("PW??? ???????????? ????????????.")
//                            //Toast.makeText(this@MainActivity,"PW??? ???????????? ????????????.", Toast.LENGTH_SHORT).show()
//                        }
                    } else {
                        dialog("????????? ??????.")
                    }
                } else {
                    dialog("???????????? ID, PW??? ???????????????.")
                }
            }
        }
        signButton.setOnClickListener {
            val id = idEditText.text.toString()
            val pw = pwEditText.text.toString()
            if (isEmpty(id,pw))
            {
                //val sharedPref = getSharedPreferences("user info" , MODE_PRIVATE)
                //val editor = sharedPref.edit()
                if(viewModel.findUserCount(id) == 0)
                {
                    viewModel.insert(User(id, pw))
                    dialog("???????????? ?????????????????????.")
                    idEditText.setText(id)
                    pwEditText.setText(pw)
                }
                else
                {
                    dialog("???????????? ID ?????????. ?????? ????????? ?????????.")
                }
            }
            else
            {
                dialog("????????? ID, PW??? ???????????????.")
            }
        }
    }
    private fun isEmpty(id: String, pw: String): Boolean =  id.isNotEmpty() && pw.isNotEmpty()

    /*private fun putKeyValue(key: String, value: String){
        editor.putString(key, value)
        editor.commit()
    }

    fun getValue(key: String): String? = sharedPref.getString(key, "")*/

    private fun dialog(msg: String){
        var dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)

        dialog.show()
    }
    override fun onStart() {
        super.onStart()
        Log.d("ABCDE", "onCreate: MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ABCDE", "onCreate: MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ABCDE", "onCreate: MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ABCDE", "onCreate: MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ABCDE", "onCreate: MainActivity onDestroy")
    }


}