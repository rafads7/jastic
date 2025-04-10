package com.rafaelduransaez.core.contacts.di

import android.content.Context
import com.rafaelduransaez.core.contacts.data.repository.ContactsRepositoryImpl
import com.rafaelduransaez.core.contacts.data.source.AndroidContactsSource
import com.rafaelduransaez.core.contacts.data.source.ContactsSource
import com.rafaelduransaez.core.contacts.domain.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsModule {

    @Provides
    @Singleton
    fun provideContactsSource(
        @ApplicationContext context: Context
    ): ContactsSource = AndroidContactsSource(Dispatchers.IO, context.contentResolver)

    @Provides
    @Singleton
    fun provideContactsRepository(
        contactsSource: ContactsSource
    ): ContactsRepository = ContactsRepositoryImpl(contactsSource)
}
