package com.rafaelduransaez.feature.myjastic.domain.usecase

import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.contacts.domain.ContactSelectionError
import com.rafaelduransaez.core.contacts.domain.ContactsRepository
import com.rafaelduransaez.core.domain.base.JasticUseCase
import com.rafaelduransaez.core.domain.models.JasticResult
import javax.inject.Inject

class GetContactInfoUseCase @Inject constructor(
    private val repository: ContactsRepository
) : JasticUseCase<String, Contact, ContactSelectionError>() {

    override suspend fun execute(params: String): JasticResult<Contact, ContactSelectionError> {
        val result = repository.getContactInfo(params)
        return result
    }
}