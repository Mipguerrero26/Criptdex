package com.pi.criptdex.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.pi.criptdex.R
import com.pi.criptdex.service.ApiService
import com.pi.criptdex.model.api.Message
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Ventana del foro
@Composable
fun ForumScreen() {
    val messageText = remember { mutableStateOf("") }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email

    Column(modifier = Modifier.fillMaxSize()) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://criptdex-6ebcc-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val messagesState = remember { mutableStateOf<List<Message>>(emptyList()) }

        LaunchedEffect(Unit) {
            while (true) {
                try {
                    val messages = apiService.getMessages()

                    messagesState.value = messages.values.toList()
                } catch (e: Exception) {
                    println(e)
                }
                delay(1000)
            }
        }

        if (userEmail != null) {
            MessageList(messages = messagesState.value, userEmail = userEmail, modifier = Modifier.weight(1f))
        }

        MessageInput(
            messageText = messageText.value,
            onMessageTextChanged = { messageText.value = it },
            onSendMessage = {
                val message = Message(System.currentTimeMillis(), messageText.value, "$userEmail")
                sendMessage(message)
                messageText.value = ""
            }
        )
    }
}

//Lista de los mensajes
@Composable
fun MessageList(messages: List<Message>, userEmail: String, modifier: Modifier) {
    val sortedMessages = messages.sortedByDescending { it.date }

    LazyColumn(
        modifier,
        reverseLayout = true
    ) {
        items(sortedMessages) { message ->
            MessageItem(message = message, userEmail = userEmail)
        }
    }
}

//Mensaje
@Composable
fun MessageItem(message: Message, userEmail: String) {
    val alignment = if (userEmail == message.userName) Alignment.End else Alignment.Start
    val maxWidth = with(LocalConfiguration.current) { screenWidthDp * 2f / 3f }.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentWidth(align = alignment)
            .widthIn(max = maxWidth)
    ) {
        Card(Modifier.fillMaxWidth()) {
            Column {
                MessageHeader(message.userName)
                Text(message.messageText)
                MessageDate(message.date)
            }
        }
    }
}

//Encabezado del mensaje (usuario)
@Composable
fun MessageHeader(userName: String) {
    Text(
        text = userName,
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primary
    )
}

//Fecha del mensaje
@Composable
fun MessageDate(date: Long) {
    val formattedDate = SimpleDateFormat("dd/MM/yyyy-HH:mm", Locale.getDefault()).format(Date(date))

    Text(
        text = formattedDate,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(textAlign = TextAlign.End, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primary
    )
}

//Textfield mensaje
@Composable
fun MessageInput(messageText: String, onMessageTextChanged: (String) -> Unit, onSendMessage: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = messageText,
            onValueChange = onMessageTextChanged,
            placeholder = { Text(stringResource(R.string.message_text)) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        IconButton(
            onClick = onSendMessage
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(R.string.send_text)
            )
        }
    }
}

//Subir el mensaje a la base de datos
fun sendMessage(message: Message) {
    val database = FirebaseDatabase.getInstance("https://criptdex-6ebcc-default-rtdb.europe-west1.firebasedatabase.app/")
    val ref = database.getReference("forum")
    val messageRef = ref.push()

    messageRef.child("date").setValue(message.date)
    messageRef.child("messageText").setValue(message.messageText)
    messageRef.child("userName").setValue(message.userName)
}