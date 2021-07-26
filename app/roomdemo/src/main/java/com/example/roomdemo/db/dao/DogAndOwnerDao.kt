package com.example.roomdemo.db.dao

import androidx.room.*
import com.example.roomdemo.model.entity.Dog
import com.example.roomdemo.model.entity.Owner
import com.example.roomdemo.model.entity.relation.*

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  15:32
 *@signature 好在键盘没坏。ha
 */
@Dao
interface DogAndOwnerDao {
    //one to one
    @Transaction
    @Query("SELECT * FROM Owner")
    fun getDogAndOwnerOneToOne(): List<DogAndOwnerOneToOne>

    //one to many
    @Transaction
    @Query("select * from Owner")
    fun getDogAndOwnerOneToMany():List<DogAndOwnerOneToMany>

    //many to many
    @Transaction
    @Query("select * from Owner")
    fun getOwnerWithDogs():List<OwnerWithDogs>

    @Transaction
    @Query("select * from Dog")
    fun getDogWithOwners():List<DogWithOwners>

    //insert

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDogs(vararg dog:Dog):List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOwners(vararg owner: Owner):List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRelationMap(vararg dogOwnerCrossRef: DogOwnerCrossRef):List<Long>



}