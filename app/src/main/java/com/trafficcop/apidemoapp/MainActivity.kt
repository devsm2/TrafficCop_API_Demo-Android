package com.trafficcop.apidemoapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trafficcop.apidemoapp.databinding.ActivityMainBinding
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val apiService: ApiInterface = RetrofitClient.buildApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnCallApi.setOnClickListener {
            callTrafficCopApi()
        }
    }

    private fun getLanguage(): String {
        val resources = application.resources

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0].language
        } else {
            resources.configuration.locale.language
        }
    }

    private fun getTimeZoneOffset(): Long {
        // get current date time
        val date = System.currentTimeMillis()

        return -TimeUnit.MILLISECONDS.toMinutes(
            TimeZone.getDefault().getOffset(date).toLong()
        )
    }

    private fun getApplicationId(): String {
        return "com.trafficcop.apidemoapp"
    }

    private fun callTrafficCopApi() {
        val requestBody = TrafficCopRequestBody(
            appId = getApplicationId(),
            navigatorLanguage = getLanguage(),
            timezoneOffset = getTimeZoneOffset()
        )

        Log.d(TAG, "Request Body: $requestBody")

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.checkIvtScore(requestBody)
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "Response: ${response.body()}")
                    launch(Dispatchers.Main) {
                        binding.tvValueIvtScore.text = response.body()!!.ivtScore.toString()
                    }
                } else {
                    Log.e(TAG, "TrafficCop API call not successful.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
                e.printStackTrace()
            }
        }
    }

}