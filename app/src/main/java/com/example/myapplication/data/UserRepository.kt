package com.example.myapplication.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDAO: UserDAO) {

    val allUser: Flow<List<User>> = userDAO.getAllUser()

    @WorkerThread
    suspend fun insert(user: User){
        if (userDAO.findUser(user.id) == 0){
            userDAO.insert(user)
        }
    }

    @WorkerThread
    suspend fun getUserCountById(id: String) = userDAO.findUser(id)

    @WorkerThread
    suspend fun deleteUser()
    {
        userDAO.deleteAll()
    }

    @WorkerThread
    suspend fun getUserPW(userId: String) = userDAO.findPW(userId)
}