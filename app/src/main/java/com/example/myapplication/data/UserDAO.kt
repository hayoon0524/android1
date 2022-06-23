package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Query("select * from user_table")
    fun getAllUser(): Flow<List<User>>

    @Query("select count(id) from user_table where id = :inputId")
    suspend fun findUser(inputId: String): Int

    @Insert
    suspend fun insert(user: User)

    @Query("delete from user_table")
    suspend fun deleteAll()

    @Query("select pw from user_table where id = :userId")
    suspend fun findPW(userId: String): String
}