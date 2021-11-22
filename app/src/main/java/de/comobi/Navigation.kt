package de.comobi

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import de.comobi.chat.ChatScreen

/**
  NavController API will manage navigation between the pages
 **/

@ExperimentalComposeUiApi
@Composable
fun Navigation (){
    val navController = rememberNavController()
    var start: String?= null
    val mAuth = FirebaseAuth.getInstance()

    //if statement is going to check is the user was logged in before and based on it send to appropriate page
    if  (mAuth.getCurrentUser() == null)
    {
        start = "SignIn"
    }
    else
    {
        start = "Chat"
    }
    NavHost(
        navController = navController,
        startDestination = start,
        builder = {
            composable("SignIn", content = { SignInScreen(navController = navController)})
            composable("SignUp", content = { SignUpScreen(navController = navController)})
            composable("Chat", content = { ChatScreen(navController = navController) })
            composable("Map", content = { MapScreen(navController = navController) })
        }
    )
}
