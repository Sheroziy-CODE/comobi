package de.comobi.widgets

import android.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
  Not implemented yet function for navigation bar.
 **/

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBar(
        route = "",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Profile : BottomBar(
        route = "",
        title = "Location",
        icon = Icons.Default.Map
    )

    object Settings : BottomBar(
        route = "",
        title = "Exit",
        icon = Icons.Default.Close
    )
}

