package com.rafaelduransaez.core.contacts.data.source

import android.content.ContentResolver
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.Contacts
import androidx.core.net.toUri
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.models.JasticResult.Companion.failure
import com.rafaelduransaez.core.base.models.JasticResult.Companion.success
import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.contacts.domain.ContactSelectionError
import com.rafaelduransaez.core.domain.extensions.empty
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AndroidContactsSource @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver
) : ContactsSource {

    override suspend fun getContactInfo(contactIdentifier: String): JasticResult<Contact, ContactSelectionError> =
        withContext(dispatcher) {
            val uri = contactIdentifier.toUri()
            val projection = arrayOf(Contacts._ID, Contacts.DISPLAY_NAME)

            contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (!cursor.moveToFirst()) {
                    return@withContext JasticResult.failure(ContactSelectionError.FieldNotFound)
                }

                val id = cursor.getString(cursor.getColumnIndexOrThrow(Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME)).orEmpty()
                val phoneNumber = getPhoneNumber(id)

                return@withContext JasticResult.success(Contact(id = id, name = name, phoneNumber = phoneNumber))
            }
            return@withContext JasticResult.failure(ContactSelectionError.Unknown)
        }

        private fun getPhoneNumber(contactId: String): String {
            val projection = arrayOf(Phone.NUMBER)
            return contentResolver.query(
                Phone.CONTENT_URI, projection, "${Phone.CONTACT_ID} = ?", arrayOf(contactId), null
            )?.use { phoneCursor ->
                if (phoneCursor.moveToFirst()) {
                    phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(Phone.NUMBER)).orEmpty()
                } else String.empty
            } ?: String.empty
        }

}