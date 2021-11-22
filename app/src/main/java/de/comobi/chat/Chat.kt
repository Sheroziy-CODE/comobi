package de.comobi.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.comobi.ui.theme.DefaultTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import de.comobi.Constants

/**
ChatScreen function create design, sort the messages based on the time we are using the “sent_on” key. And to check if the message was sent by the current user or not we are using the “send_by” key.
 **/

@Composable
fun ChatScreen (navController: NavController, chatViewModel: ChatViewModel = viewModel())
{
    val message: String by chatViewModel.message.observeAsState(initial = "")
    val messages: List<Map<String, Any>> by chatViewModel.messages.observeAsState(initial = emptyList<Map<String, Any>>().toMutableList())

    Scaffold(backgroundColor = MaterialTheme.colors.primary)
    {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            Card(
                Modifier
                    .weight(3f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            )
            {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text(text = "CHAT", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                        IconButton(
                            onClick = {
                                FirebaseAuth.getInstance().signOut()
                                navController.popBackStack()
                                navController.navigate("SignIn"){
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                        {
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Exit")
                        }
                    }
                    Divider(color = Color.Black, thickness = 1.dp)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 0.85f, fill = true),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        reverseLayout = true
                    ) {
                        items(messages) { message ->
                            val isCurrentUser = message[Constants.IS_CURRENT_USER] as Boolean

                            SingleMessage(
                                message = message[Constants.MESSAGE].toString(),
                                isCurrentUser = isCurrentUser
                            )
                        }
                    }
                    OutlinedTextField(
                        value = message,
                        onValueChange = {
                            chatViewModel.updateMessage(it)
                        },
                        label = {
                            Text(
                                "Type Your Message"
                            )
                        },
                        maxLines = 1,
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 1.dp)
                            .fillMaxWidth(),
                            //.weight(weight = 1f, fill = true),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {chatViewModel.addMessage()}
                        ),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    chatViewModel.addMessage()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send Button"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

/**

 **/

@Composable
fun SingleMessage(message: String, isCurrentUser: Boolean) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = (if (isCurrentUser) MaterialTheme.colors.primary else Green)
    )
    {
        Text(
            text = message,
            textAlign =
            if (isCurrentUser)
                TextAlign.End
            else
                TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            color = if (!isCurrentUser) MaterialTheme.colors.primary else Green
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    val chatViewModel = ChatViewModel()
    val navController = rememberNavController()
    DefaultTheme {
        ChatScreen(navController, chatViewModel = chatViewModel)
    }
}

