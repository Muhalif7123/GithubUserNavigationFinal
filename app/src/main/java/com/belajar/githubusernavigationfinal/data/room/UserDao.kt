package com.belajar.githubusernavigationfinal.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.belajar.githubusernavigationfinal.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY login DESC")
    fun getUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user ORDER BY favorite = 0 DESC")
    fun getUserByNoFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE favorite = 1")
    fun getUserFavorite(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user WHERE favorite = 0")
    suspend fun deleteUser()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login AND favorite = 1)")
    suspend fun userFavorite(login: String): Boolean

}