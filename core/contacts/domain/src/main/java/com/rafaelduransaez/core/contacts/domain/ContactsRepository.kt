package com.rafaelduransaez.core.contacts.domain

import com.rafaelduransaez.core.base.models.JasticResult

interface ContactsRepository {
    suspend fun getContactInfo(params: String): JasticResult<Contact, ContactSelectionError>
}
