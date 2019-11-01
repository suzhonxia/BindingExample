package com.sun.binding.db.dao

import androidx.room.*
import com.sun.binding.entity.UserInfoEntity

/**
 * Data Access Object for the userInfo table
 */
@Dao
interface UserInfoDao {

    /**
     * Insert a userInfo in the database. If the userInfo already exists, relpase it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfoEntity)

    /**
     * Delete all userInfo.
     */
    @Query("DELETE FROM userInfo")
    fun deleteUserInfo()

    /**
     * Update a userInfo
     */
    @Update
    fun updateUserInfo(userInfo: UserInfoEntity)

    /**
     * Select a userInfo.
     */
    @Query("SELECT * FROM userInfo limit 1")
    fun getUserInfo(): UserInfoEntity?
}