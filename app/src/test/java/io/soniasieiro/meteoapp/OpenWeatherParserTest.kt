package io.soniasieiro.meteoapp

import io.soniasieiro.meteoapp.apiclient.openweather.HourlyItem
import io.soniasieiro.meteoapp.apiclient.openweather.OpenWeatherParser
import io.soniasieiro.meteoapp.apiclient.openweather.OpenWeatherResponse
import io.soniasieiro.meteoapp.apiclient.openweather.WeatherItem
import org.junit.Assert
import org.junit.Test

class OpenWeatherParserTest {
    @Test
    fun openWeatherParserIsCorrect() {
        val weather = WeatherItem(icon = "n40")
        val weatherList: List<WeatherItem?>? = listOf(weather)
        val hourly1 = HourlyItem(dt = 1608584400, temp = 8.55, weather = weatherList)
        val hourly2 = HourlyItem(dt = 1608588000, temp = 10.55, weather = weatherList)
        val hourly3 = HourlyItem(dt = 1608591600, temp = 7.55, weather = weatherList)
        val hourly: List<HourlyItem?>? = listOf(hourly1,hourly2, hourly3)

        val openWeatherResponse = OpenWeatherResponse(lat = 40.39, lon = -3.64, hourly = hourly)

        val forecast = OpenWeatherParser.from(openWeatherResponse)

        Assert.assertEquals("40.39", forecast.lat)
        Assert.assertEquals("-3.64", forecast.lon)
        Assert.assertEquals(2, forecast.forecastByHours?.size)
        Assert.assertEquals("23:00h", forecast.forecastByHours?.get(0)?.hour)
        Assert.assertEquals("http://openweathermap.org/img/wn/n40@2x.png", forecast.forecastByHours?.get(0)?.icon)
    }
}