package com.company.jetweatherforecast.uiLayer.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.jetweatherforecast.dataLayer.model.Favorite
import com.company.jetweatherforecast.domainLayer.components.FavoriteViewModel
import com.company.jetweatherforecast.navigation.WeatherScreens

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionController: () -> Unit = {},
    onButtonClicked: () -> Unit = {}) {

    val showDialog = remember {
        mutableStateOf(false)
    }
    if(showDialog.value) {
        ShowSettingDropdownMenu(showDialog = showDialog, navController = navController)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    TopAppBar(
        title = {
                Text(
                    text = title,
                    color = MaterialTheme.colors.onSecondary,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp))
        },
        actions = {
            if(isMainScreen) {
                IconButton(onClick = { onAddActionController.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                }
                IconButton(onClick = { showDialog.value = true}) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More options")
                }
            }
            else Box{}
        },
        navigationIcon = {
            if(icon != null) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSecondary,
                        modifier = Modifier.clickable { onButtonClicked.invoke() })
                }
            }
            else if(isMainScreen) {
                val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value.filter{
                    item ->
                    item.city == title.split(", ")[0]
                }
                if(isAlreadyFavList.isNullOrEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val favorite = title.split(", ")
                                favoriteViewModel.insertFavorite(
                                    Favorite(favorite[0], favorite[1])
                                ).run { showIt.value = true }

                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                }
                else {
                    showIt.value = false
                    Box {}
                }
                ShowToast(context = context, showIt)

            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation)
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if(showIt.value) {
        Toast.makeText(context, "City added to favorites", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropdownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember{
        mutableStateOf(true)
    }
    val items = listOf("About", "Favorites", "Settings")

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)) {
            items.forEachIndexed{ _, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                    
                }) {
                    Icon(imageVector =
                    when(text) {
                        "About" -> Icons.Default.Info
                        "Favorites" -> Icons.Default.FavoriteBorder
                        else -> Icons.Default.Settings
                    }, contentDescription = null, tint = Color.LightGray)
                    Text(text = text,
                        modifier = Modifier.clickable { navController.navigate(
                            when(text) {
                            "About" -> WeatherScreens.AboutScreen.name
                            "Favorites" -> WeatherScreens.FavoriteScreen.name
                            else -> WeatherScreens.SettingsScreen.name
                        }) },
                        fontWeight = FontWeight.W300)


        }}
        }
    }
}
