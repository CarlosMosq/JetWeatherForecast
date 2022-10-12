package com.company.jetweatherforecast.uiLayer.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.jetweatherforecast.dataLayer.model.Unit
import com.company.jetweatherforecast.domainLayer.components.SettingsViewModel
import com.company.jetweatherforecast.uiLayer.widgets.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()) {

    val measurementUnits = listOf("imperial", "metric")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val defaultChoice = if(choiceFromDb.isNullOrEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    val choiceState = remember {
        mutableStateOf(defaultChoice)
    }
    val unitToggleState = remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Settings",
            isMainScreen = false,
            icon = Icons.Default.ArrowBack) {
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally) {

                Text(
                    text = "Change Unit of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState.value,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(color = Color.Magenta.copy(alpha = 0.4f)),
                    onCheckedChange = {
                    unitToggleState.value = !it
                    choiceState.value =
                    if(unitToggleState.value) "imperial" else "metric"
                }) {
                    Text(text = if(unitToggleState.value) "Fahrenheit ºF" else "Celsius ºC")
                }
                Button(
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Unit(unit = choiceState.value))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEFBE42))){
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp)
                }
            }
        }
    }
}