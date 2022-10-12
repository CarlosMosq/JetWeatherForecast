package com.company.jetweatherforecast.uiLayer.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.company.jetweatherforecast.R
import com.company.jetweatherforecast.dataLayer.data.DataOrException
import com.company.jetweatherforecast.dataLayer.model.Weather
import com.company.jetweatherforecast.dataLayer.model.WeatherItem
import com.company.jetweatherforecast.domainLayer.components.MainViewModel
import com.company.jetweatherforecast.domainLayer.components.SettingsViewModel
import com.company.jetweatherforecast.navigation.WeatherScreens
import com.company.jetweatherforecast.uiLayer.widgets.WeatherAppBar
import com.company.jetweatherforecast.utils.formatDate
import com.company.jetweatherforecast.utils.formatDateTime
import com.company.jetweatherforecast.utils.formatDecimals
import com.company.jetweatherforecast.utils.formatWeekDate

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    val unit = remember {
        mutableStateOf("imperial")
    }
    val isImperial = remember {
        mutableStateOf(false)
    }

    if(!unitFromDb.isNullOrEmpty()) {
        unit.value = unitFromDb[0].unit
        isImperial.value = unit.value == "imperial"
    }
    val weatherData = produceState<DataOrException<
            Weather,
            Boolean,
            Exception>>(initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city = city.toString(), unit = unit.value)
    }.value

    if(weatherData.loading == true) {
        CircularProgressIndicator()
    }
    else if(weatherData.data != null){
        MainScaffold(weather = weatherData.data!!, navController, isImperial)
    }


}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: MutableState<Boolean>) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        WeatherAppBar(
            title = weather.city.name + ", ${weather.city.country}",
            navController = navController,
            onAddActionController = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp) {
            Log.d("clickEvent", "Button clicked")
        }

    }) {
        MainContent(weather = weather, isImperial)
    }
}

@Composable
fun MainContent(weather: Weather, isImperial: MutableState<Boolean>) {
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        MainWeatherInfo(weather = weather)
        HumidityWindPressureRow(weatherItem = weather.list[0], isImperial)
        Divider()
        SunsetAndSunriseRow(weatherItem = weather.list[0])
        Text(text = "This Week")
        RestOfTheWeek(weather = weather)
    }
}

@Composable
fun MainWeatherInfo(weather: Weather) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}.png"
    Text(
        text = formatDate(weather.list[0].dt),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSecondary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(6.dp))

    Surface(modifier = Modifier
        .padding(4.dp)
        .size(200.dp), shape = CircleShape, color = Color(0xFFFFC400)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            WeatherStateImage(imageUrl = imageUrl)
            Text(
                text = formatDecimals(weather.list[0].temp.day)  + "º",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = weather.list[0].weather[0].main,
                fontStyle = FontStyle.Italic)
        }
    }
}


@Composable
fun HumidityWindPressureRow(weatherItem: WeatherItem, isImperial: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp))
            Text(text = "${weatherItem.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp))
            Text(text = "${weatherItem.pressure}psi", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp))
            Text(text = "${weatherItem.speed} ${if(isImperial.value) "mph" else "m/s"}", style = MaterialTheme.typography.caption)
        }

    }
}

@Composable
fun SunsetAndSunriseRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp))
            Text(
                text = formatDateTime(weatherItem.sunrise),
                style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset icon",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp))
            Text(
                text = formatDateTime(weatherItem.sunset),
                style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun RestOfTheWeek(weather: Weather) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(weather.list.subList(1, weather.list.size)) {day ->
            DailyRow(weatherItem = day)
        }
    }
}

@Composable
fun DailyRow(weatherItem: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Row(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(75.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = formatWeekDate(weatherItem.dt))
        WeatherStateImage(imageUrl = imageUrl)
        Surface(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
            color = Color.Yellow, shape = RoundedCornerShape(15.dp)) {
            Text(text = weatherItem.weather[0].description)
        }
        Text(text = formatDecimals(weatherItem.temp.max) + "º " +
                formatDecimals(weatherItem.temp.min) + "º")

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(imageUrl),
        contentDescription = "Current weather icon",
        modifier = Modifier.size(80.dp))
}
