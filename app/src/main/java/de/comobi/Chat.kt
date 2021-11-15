package de.comobi

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ChatScreen (navController: NavController) {
    
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
                    Text(text = "CHAT", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    val navController = rememberNavController()
    DefaultTheme {
        ChatScreen(navController)
    }
}
