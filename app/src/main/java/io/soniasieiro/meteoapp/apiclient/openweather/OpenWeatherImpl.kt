package io.soniasieiro.meteoapp.apiclient.openweather

import android.os.StrictMode
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import io.soniasieiro.meteoapp.BuildConfig
import io.soniasieiro.meteoapp.apiclient.ApiService
import io.soniasieiro.meteoapp.datamodels.Forecast



class OpenWeatherImpl: ApiService {
    private val API_KEY_PARAM = "appid=${BuildConfig.OpenWeatherApiKey}"
    private val UNITS_PARAM = "units=${BuildConfig.OpenWeatherUnits}"
    private val EXCLUDE_PARAM = "exclude=${BuildConfig.OpenWeatherExclude}"


    override fun getForecast (lat: String, lon: String): Forecast {
        val url = getUrl(lat,lon)
        val data = downloadData(url)
        val response = getResponse(data)
        val forecast = OpenWeatherParser.from(response)
        return forecast
    }

    private fun getUrl (lat: String, lon: String): String {
        return BuildConfig.OpenWeatherBaseUrl +
                "?lat=${lat}" +
                "&lon=${lon}" +
                "&${API_KEY_PARAM}" +
                "&${UNITS_PARAM}" +
                "&${EXCLUDE_PARAM}"
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

    private fun getResponse (response: String): OpenWeatherResponse {
        val gson = Gson()
        val responseParsed: OpenWeatherResponse = gson.fromJson(response, OpenWeatherResponse::class.java)
        return responseParsed
    }



}

