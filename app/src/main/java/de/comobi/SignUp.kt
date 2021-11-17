package de.comobi

import android.util.Log
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


@Composable
fun SignUpScreen (navController: NavController) {

    var email by remember{
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var carmodel by remember{
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by derivedStateOf {
        email.isNotBlank() && username.isNotBlank() &&  carmodel.isNotBlank() && password.isNotBlank()
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
                    .weight(3f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            )
            {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                    Column(
                        Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    )
                    {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "Email") },
                            trailingIcon =
                            {
                                if (email.isNotBlank())
                                    IconButton(onClick = { email = "" })
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = ""
                                        )
                                    }
                            }
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = username,
                            onValueChange = { username = it },
                            label = { Text(text = "Name") },
                            trailingIcon =
                            {
                                if (username.isNotBlank())
                                    IconButton(onClick = { username = "" })
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = ""
                                        )
                                    }
                            }
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = carmodel,
                            onValueChange = { carmodel = it },
                            label = { Text(text = "Car Model") },
                            trailingIcon =
                            {
                                if (carmodel.isNotBlank())
                                    IconButton(onClick = { carmodel = "" })
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = ""
                                        )
                                    }
                            }
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon =
                            {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible })
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
                                username.trim { it <= ' ' }

                                //Create an instance and create a register a user with email and password.
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        //If the registration is successfully done
                                        if (task.isSuccessful)
                                        {
                                            //Firebase registered user
                                            val firebaseUser: FirebaseUser = task.result!!.user!!

                                            //Firebase register additional data to DB
                                            val uid = FirebaseAuth.getInstance().uid ?: "" //get user id from Firebase to put for the Firebase path

                                            // Get the database instance and store into object
                                            // getReference() get the refrence if the refrence is already creted... if reference is not created then it will create a new refrence here
                                            var fDatabase: DatabaseReference = FirebaseDatabase.getInstance("https://d2d-communication-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users/$uid")
                                            val user = Users(email, username, carmodel)
                                            fDatabase.setValue(user) // assign value to the particular refrence.
                                                .addOnSuccessListener {
                                                    Log.d("Register Activity", "User saved to database")
                                                }
                                            Toast.makeText(
                                                context,
                                                "You are registered successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            val popBackStack = navController.popBackStack()
                                            navController.navigate("SignIn"){
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        }
                                        else
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
                            Text(text = "Create account")
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    val navController = rememberNavController()
    DefaultTheme {
        SignUpScreen(navController)
    }
}

