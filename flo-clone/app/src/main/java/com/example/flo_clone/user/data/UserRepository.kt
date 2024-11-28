package com.example.flo_clone.user.data

import android.content.Context
import com.example.flo_clone.utils.FloDatabase

class UserRepository(context: Context) {
    private val userDao = FloDatabase.getInstance(context)!!.userDao()

    fun getAllUsers(): List<User> {
        return userDao.getAll()
    }

    fun insert(user: User) {
        userDao.insert(user)
    }

    fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email).firstOrNull()
    }
}