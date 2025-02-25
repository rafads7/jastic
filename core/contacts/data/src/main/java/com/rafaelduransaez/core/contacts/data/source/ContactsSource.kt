package com.rafaelduransaez.core.contacts.data.source

import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.contacts.domain.ContactSelectionError
import com.rafaelduransaez.core.domain.models.JasticFailure
import com.rafaelduransaez.core.domain.models.JasticResult
import kotlinx.coroutines.flow.Flow

interface ContactsSource {
    suspend fun getContactInfo(contactIdentifier: String): JasticResult<Contact, ContactSelectionError>
}