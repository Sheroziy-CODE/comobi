package de.comobi

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation (){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "SignIn",
        builder = {
            composable("SignIn", content = { SignInScreen(navController = navController)})
            composable("SignUp", content = { SignUpScreen(navController = navController)})
            composable("Chat", content = { ChatScreen(navController = navController)})
        }
    )
}
