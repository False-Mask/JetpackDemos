package com.example.roomdemo.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Owner(@PrimaryKey val ownerId: Long, val name: String)
