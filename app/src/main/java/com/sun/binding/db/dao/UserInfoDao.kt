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
     * Delete a userInfo by id.
     */
    @Query("DELETE FROM userInfo WHERE userId = :userId")
    fun deleteUserInfoById(userId: String)

    /**
     * Delete all userInfo.
     */
    @Query("DELETE FROM userInfo")
    fun deleteAllUserInfo()

    /**
     * Update a userInfo
     */
    @Update
    fun updateUserInfo(userInfo: UserInfoEntity)

    /**
     * Select a userInfo by id.
     */
    @Query("SELECT * FROM userInfo WHERE userId = :userId")
    fun getUserInfoById(userId: String): UserInfoEntity?
}