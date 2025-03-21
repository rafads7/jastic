package com.rafaelduransaez.core.contacts.data.repository

import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.contacts.data.source.ContactsSource
import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.contacts.domain.ContactSelectionError
import com.rafaelduransaez.core.contacts.domain.ContactsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepositoryImpl @Inject constructor(
    private val contactsSource: ContactsSource
): ContactsRepository {

    override suspend fun getContactInfo(params: String): JasticResult<Contact, ContactSelectionError> {
        val res = contactsSource.getContactInfo(params)
        return res
    }
}