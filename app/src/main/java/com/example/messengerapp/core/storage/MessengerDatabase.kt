package com.example.messengerapp.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.messengerapp.core.storage.dao.ContactDao
import com.example.messengerapp.data.entity.ContactEntity


@Database(version = 2, entities = [ContactEntity::class], exportSchema = false)
abstract class MessengerDatabase(): RoomDatabase(){
    abstract fun contactsDao(): ContactDao
}