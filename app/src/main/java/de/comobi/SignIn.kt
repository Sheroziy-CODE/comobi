package de.comobi


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.comobi.ui.theme.DefaultTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun SignInScreen (navController: NavController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by derivedStateOf {
        email.isNotBlank() && password.isNotBlank()
    }

    val context = LocalContext.current


    Scaffold(backgroundColor = MaterialTheme.colors.primary)
    {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top)
        {
            Image(
                painter = painterResource(id = R.drawable.ic_welcome),
                contentDescription = "Welcome",
                modifier = Modifier
                    .weight(1f)
                    .size(900.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Card(
                Modifier
                    .weight(2f)
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
                    Text(text = "WELCOME", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                    Column(
                        Modifier
                            .fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
                    {
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = {email = it},
                            label = { Text(text = "Email") },
                            trailingIcon =
                            {
                                if(email.isNotBlank())
                                    IconButton(onClick = { email = "" })
                                    {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                    }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = {password = it},
                            label = { Text(text = "Password")},
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon =
                            {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible})
                                {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )

                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {

                                email.trim { it <= ' ' }
                                password.trim { it <= ' ' }

                                //Create an instance and create a register a user with email and password.
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        //If the logging in is successfully done
                                        if (task.isSuccessful)
                                        {
                                            //Firebase registered user
                                            val firebaseUser: FirebaseUser = task.result!!.user!!
                                            Toast.makeText(
                                                context,
                                                "You are logged in successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.popBackStack()
                                            navController.navigate("Chat"){
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        }
                                        else // if not it will show error message
                                        {
                                            Toast.makeText(
                                                context,
                                                task.exception!!.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            },
                            enabled = isFormValid,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )
                        {
                            Text(text = "Log In")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween)
                        {
                            TextButton(onClick = {
                                navController.navigate("SignUp"){
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                            )
                            {
                                Text(text = "Sign Up")
                            }
                            TextButton(onClick = {})
                            {
                                Text(text = "Forgot Password?")
                            }
                        }
                    }
                }

            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    val navController = rememberNavController()
    DefaultTheme {
        SignInScreen(navController)
    }
}

