package de.comobi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import de.comobi.ui.theme.DefaultTheme
import android.preference.PreferenceManager

import android.content.SharedPreferences
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent





class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultTheme {
                    Navigation()
            }
        }
    }
}

