package com.sun.binding.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sun.binding.application.BindingApplication
import com.sun.binding.db.converter.UserInfoTypeConverter
import com.sun.binding.db.dao.UserInfoDao
import com.sun.binding.entity.UserInfoEntity

@Database(entities = [UserInfoEntity::class], version = 1)
@TypeConverters(UserInfoTypeConverter::class)
abstract class BindingDatabase : RoomDatabase() {

    companion object {
        fun getInstance() = Single.sin
    }

    private object Single {
        val sin: BindingDatabase = Room.databaseBuilder(
            BindingApplication.getInstance(),
            BindingDatabase::class.java,
            "BindingExample.db"
        ).allowMainThreadQueries().build()
    }

    abstract fun getUserInfoDao(): UserInfoDao
}