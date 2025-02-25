package com.rafaelduransaez.core.components.jContactPicker

import android.Manifest.permission.READ_CONTACTS
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.rafaelduransaez.core.components.jFloatingActionButton.LikeFAB


@Composable
fun JContactPicker(contentPadding: PaddingValues) {
    var contactName by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Create a launcher using the PickContact contract
    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri: Uri? ->
        // When a contact is picked, contactUri will have its URI
        contactUri?.let { uri ->
            // Query the content resolver for the contact's display name
            val cursor = context.contentResolver.query(
                uri,
                arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
                null,
                null,
                null
            )
            cursor?.use { cur ->
                if (cur.moveToFirst()) {
                    val nameIndex = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        contactName = cur.getString(nameIndex)
                    }
                }
            }
        }
    }
}

/*
@Composable
fun contactPicker(
    context: Context,
    contactName: String,
    contactNumber: String,
) {
    // on below line we are creating variable for activity.
    val activity = LocalContext.current as Activity

    // on below line we are creating a column,
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // on below line we are adding a text for heading.
        Text(
            // on below line we are specifying text
            text = "Contact in Android",
            // on below line we are specifying
            // text color, font size and font weight
            color = greenColor, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        // on below line adding a spacer
        Spacer(modifier = Modifier.height(20.dp))

        // on below line creating a text for contact name
        Text(text = contactName, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // on below line adding a spacer
        Spacer(modifier = Modifier.height(20.dp))

        // on below line creating a text for contact number.
        Text(text = contactNumber, fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // on below line adding a spacer
        Spacer(modifier = Modifier.height(20.dp))

        // on below line creating a button to pick contact.
        Button(
            // on below line adding on click for button.
            onClick = {
                // on below line checking if permission is granted.
                if (hasContactPermission(context)) {
                    // if permission granted open intent to pick contact/
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    startActivityForResult(activity, intent, 1, null)
                } else {
                    // if permission not granted requesting permission .
                    requestContactPermission(context, activity)
                }
            },
            // adding padding to button.
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // displaying text in our button.
            Text(text = "Pick Contact")

        }
    }
}*/
