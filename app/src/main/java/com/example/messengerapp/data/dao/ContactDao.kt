package com.example.messengerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.messengerapp.data.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactById(contactId: String): ContactEntity

    @Upsert
    fun upsertUser(contact:ContactEntity)

    @Upsert
    fun upsertAllContactsToDb(contacts: List<ContactEntity>)

    @Delete
    fun deleteContact(contact: ContactEntity)
}