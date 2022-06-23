package com.example.myapplication.viewModel

import androidx.lifecycle.*
import com.example.myapplication.data.User
import com.example.myapplication.data.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    val allUser: LiveData<List<User>> = userRepository.allUser.asLiveData()
    val curUserPw: MutableLiveData<String> = MutableLiveData("")  // observe, 하고 pw랑 비교

    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun findUserCount(id: String): Int{
        return allUser.value!!.count {
            it.id == id
        }
    }

    fun findPW(id: String){
            viewModelScope.launch {
                curUserPw.value = userRepository.getUserPW(id)
        }
    }

    fun deleteAll() = viewModelScope.launch {
        userRepository.deleteUser()
    }
}