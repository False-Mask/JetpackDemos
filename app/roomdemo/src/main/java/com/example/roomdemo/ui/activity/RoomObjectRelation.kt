package com.example.roomdemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.roomdemo.R
import com.example.roomdemo.db.DogAndOwnerDataBase
import com.example.roomdemo.model.entity.Dog
import com.example.roomdemo.model.entity.Owner
import com.example.roomdemo.model.entity.relation.DogOwnerCrossRef
import kotlinx.android.synthetic.main.activity_object_relation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG  = "RoomObjectRelation"
class RoomObjectRelation : AppCompatActivity() {
    private val dogeAndOwnerDao by lazy { DogAndOwnerDataBase.getInstance(applicationContext).getDogAndOwnerDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_relation)
        setListeners()
    }

    private fun setListeners() {
        insert_owner.setOnClickListener {

            lifecycleScope.launch (Dispatchers.IO){
                val owner1 = Owner(1,"zhangshan")
                val owner2 = Owner(2,"lisi")
                val owner3 = Owner(3,"wangmazi")
                dogeAndOwnerDao.insertOwners(owner1,owner2,owner3)
            }
        }

        insert_dog.setOnClickListener {

            lifecycleScope.launch (Dispatchers.IO){
                val dog1 = Dog(2,1,"zs",1,2,"wanw")
                val dog2 = Dog(3,2,"ls",3,4,"wanw")
                val dog3 = Dog(4,3,"wmz",5,6,"wanw")
                val dog4 = Dog(5,1,"zs2",1,3,"wanw")


                dogeAndOwnerDao.insertRelationMap(
                    DogOwnerCrossRef(4,1),
                    DogOwnerCrossRef(2,2),
                    DogOwnerCrossRef(3,2),
                    DogOwnerCrossRef(5,2),
                    DogOwnerCrossRef(2,3),
                    DogOwnerCrossRef(3,3),
                    DogOwnerCrossRef(4,3),
                    DogOwnerCrossRef(5,3)
                )

                dogeAndOwnerDao.insertDogs(dog1,dog2,dog3,dog4)
            }

        }

        get_dog_and_owner.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                //one to one
                /*val dogAndOwnerOneToOne = dogeAndOwnerDao.getDogAndOwnerOneToOne()
                dogAndOwnerOneToOne.forEach {
                    Log.e(TAG, "getDogAndOwnerOneToOne $it" )
                }*/


                //one to many
                /*val dogAndOwnerOneToOne = dogeAndOwnerDao.getDogAndOwnerOneToMany()
                dogAndOwnerOneToOne.forEach{
                    Log.e(TAG, "getDogAndOwnerOneToOne $it" )
                }*/


                //money to money
                val ownerWithDogs = dogeAndOwnerDao.getOwnerWithDogs()
                ownerWithDogs.forEach {
                    Log.e(TAG, "getOwnerWithDogs $it" )
                }
                val dogWithOwners = dogeAndOwnerDao.getDogWithOwners()
                dogWithOwners.forEach{
                    Log.e(TAG, "getDogWithOwners $it")
                }


            }

        }

        jump_button.setOnClickListener {
            startActivity(Intent(this,RoomConverter::class.java))
        }
    }
}

