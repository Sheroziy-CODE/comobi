package de.comobi.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


/**
Not implemented yet function should show the list of available cars in the radius
 **/
@Composable
fun ChatListScreen () {
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
                        .padding(32.dp),
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
                            onClick = { /*TODO*/ }
                        ) 
                        {
                            Icon(imageVector = Icons.Filled.Map, contentDescription = "Map")
                        }
                    }
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatListPreview() {
    val navController = rememberNavController()
    DefaultTheme {
        ChatListScreen()
    }
}
