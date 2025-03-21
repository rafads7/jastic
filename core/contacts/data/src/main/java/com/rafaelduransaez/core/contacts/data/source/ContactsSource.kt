package com.rafaelduransaez.core.contacts.data.source

import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.contacts.domain.ContactSelectionError

interface ContactsSource {
    suspend fun getContactInfo(contactIdentifier: String): JasticResult<Contact, ContactSelectionError>
}