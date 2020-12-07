package io.soniasieiro.meteoapp.data

import android.os.StrictMode
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import io.soniasieiro.meteoapp.data.Models.AppModels.Forecast
import io.soniasieiro.meteoapp.data.Models.AppModels.HourForecast
import io.soniasieiro.meteoapp.data.Models.ResponseModels.WeatherResponse

const val LAT = "40.385739727777704"
const val LON = "-3.642704890032998"
const val API_KEY = "2efe82c429bd6078d27b0bbe2165137b"

class MeteoAppService {

    fun getForecast (lat: String, lon: String, units: String = "metric", exclude: String = "minutely,current,alerts,daily"): Forecast {
        var url = getUrl(lat,lon,units,exclude)
        var data = downloadData(url)
        var response = getResponse(data)
        var forecast = Forecast.parseForecast(response)
        return forecast
    }

    private fun getUrl (lat: String, lon: String, units: String, exclude: String): String {
        return "https://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=${units}&exclude=${exclude}"
    }

    @Throws(IOException::class)
    private fun downloadData(url:String):String {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var inputStream: InputStream? = null
        return try {
            val apiUrl = URL(url)
            val connection = apiUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            inputStream = connection.inputStream
            inputStream.bufferedReader().use{
                it.readText()
            }
        }finally {
            if(inputStream != null){
                inputStream.close()
            }

        }
    }

    private fun getResponse (response: String): WeatherResponse {
        var gson = Gson()
        var responseParsed: WeatherResponse = gson.fromJson(response, WeatherResponse::class.java)
        return responseParsed
    }



}

