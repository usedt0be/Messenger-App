package com.example.messengerapp.core.storage.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.messengerapp.UserProto
import com.example.messengerapp.core.annotations.ApplicationContext
import com.example.messengerapp.core.storage.MessengerDatabase
import com.example.messengerapp.core.storage.dao.ContactDao
import com.example.messengerapp.data.proto.UserSerializer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


private const val USER_PREFERENCES_NAME = "user_prefs"
private const val USER_TOKEN_FILE = "user_token_file"
@Module
object StorageModule {
    @Provides
    @Singleton
    fun provideUserDataStoreProto(
        @ApplicationContext context: Context,
        userSerializer: UserSerializer
    ): DataStore<UserProto> {
        return DataStoreFactory.create(
            serializer = userSerializer,
            produceFile = { context.dataStoreFile(fileName = USER_PREFERENCES_NAME) },
            corruptionHandler = null,
        )
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = {
                context.preferencesDataStoreFile(USER_TOKEN_FILE)
            }
        )
    }




    @Provides
    @Singleton
    fun provideMessengerDatabase(@ApplicationContext context: Context): MessengerDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MessengerDatabase::class.java,
            name = "contacts_db"
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideContactsDao(messengerDb: MessengerDatabase): ContactDao {
        return messengerDb.contactsDao()
    }
}