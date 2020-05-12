package msudenver.cs3013.spring2020.weatherherald

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private lateinit var locationManager: LocationManager
    private lateinit var queue: RequestQueue
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variable Initialization
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        queue = Volley.newRequestQueue(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Bottom navigation selection listeners
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    getWeatherData()
                    val fragment = HomeFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.content_layout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.forecast_page -> {
                    getWeatherData()
                    val fragment = ForecastFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.content_layout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.settings_page -> {
                    val fragment = SettingsFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.content_layout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                else -> false
            }
        }

        // Send HTTP requests to get weather data from API
        getWeatherData()

        // Render Home fragment by default
        fragmentManager.beginTransaction().replace(R.id.content_layout, HomeFragment()).commit()
    }

    // Send HTTP requests to get current weather data and forecast data
    private fun getWeatherData() {
        val lastUpdate = sharedPreferences.getLong("last_update", 0)
        if ((System.currentTimeMillis() / 1000) - lastUpdate > 60) {

            // Update device location
            getLastLocation()

            // Get weather data for current location and time
            val lat = sharedPreferences.getFloat("user_lat", 0F)
            val long = sharedPreferences.getFloat("user_long", 0F)
            val units =
                if (sharedPreferences.getBoolean("temp_toggle", false)) "M" else "I"
            val creds = "30a3a8d02ded4e70ab5adcd53677daab"
            var url =
                "https://api.weatherbit.io/v2.0/current?lat=${lat}&lon=${long}&units=${units}&key=${creds}"

            var jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    val data: JSONObject = response.getJSONArray("data")[0] as JSONObject
                    val weather: JSONObject = data.getJSONObject("weather")
                    val currentTemp = data.getDouble("temp")
                    val icon =
                        "https://www.weatherbit.io/static/img/icons/${weather.getString("icon")}.png"
                    val stateName = data.getString("state_code")
                    val cityName = data.getString("city_name")
                    val countryName = data.getString("country_code")

                    with(sharedPreferences.edit()) {
                        putFloat("current_temp", currentTemp.toFloat())
                        putString("current_icon", icon)
                        putString("state_name", stateName)
                        putString("city_name", cityName)
                        putString("country_name", countryName)
                        apply()
                    }
                }, Response.ErrorListener { error ->
                    Log.e("WEATHERLOG", "ERROR: $error")
                }
            )
            queue.add(jsonRequest)

            // Get weather data for current location for next 5 days
            url =
                "https://api.weatherbit.io/v2.0/forecast/daily?lat=${lat}&lon=${long}&units=${units}&days=8&key=${creds}"

            jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    val data = response.getJSONArray("data")
                    val today: JSONObject = data[0] as JSONObject
                    val day1: JSONObject = data[1] as JSONObject
                    val day2: JSONObject = data[2] as JSONObject
                    val day3: JSONObject = data[3] as JSONObject
                    val day4: JSONObject = data[4] as JSONObject
                    val day5: JSONObject = data[5] as JSONObject
                    val day6: JSONObject = data[6] as JSONObject
                    val day7: JSONObject = data[7] as JSONObject

                    val todayHigh = today.getDouble("high_temp")
                    val todayLow = today.getDouble("low_temp")
                    val day1High = day1.getDouble("high_temp")
                    val day1Low = day1.getDouble("low_temp")
                    val day2High = day2.getDouble("high_temp")
                    val day2Low = day2.getDouble("low_temp")
                    val day3High = day3.getDouble("high_temp")
                    val day3Low = day3.getDouble("low_temp")
                    val day4High = day4.getDouble("high_temp")
                    val day4Low = day4.getDouble("low_temp")
                    val day5High = day5.getDouble("high_temp")
                    val day5Low = day5.getDouble("low_temp")
                    val day6High = day6.getDouble("high_temp")
                    val day6Low = day6.getDouble("low_temp")
                    val day7High = day7.getDouble("high_temp")
                    val day7Low = day7.getDouble("low_temp")


                    with(sharedPreferences.edit()) {
                        putFloat("today_high", todayHigh.toFloat())
                        putFloat("today_low", todayLow.toFloat())
                        putFloat("day1_high", day1High.toFloat())
                        putFloat("day1_low", day1Low.toFloat())
                        putFloat("day2_high", day2High.toFloat())
                        putFloat("day2_low", day2Low.toFloat())
                        putFloat("day3_high", day3High.toFloat())
                        putFloat("day3_low", day3Low.toFloat())
                        putFloat("day4_high", day4High.toFloat())
                        putFloat("day4_low", day4Low.toFloat())
                        putFloat("day5_high", day5High.toFloat())
                        putFloat("day5_low", day5Low.toFloat())
                        putFloat("day6_high", day6High.toFloat())
                        putFloat("day6_low", day6Low.toFloat())
                        putFloat("day7_high", day7High.toFloat())
                        putFloat("day7_low", day7Low.toFloat())
                        apply()
                    }

                    // TODO: Handle forecast data
                }, Response.ErrorListener { error ->
                    Log.e("WEATHERLOG", "ERROR: $error")
                }
            )

            queue.add(jsonRequest)
            with(sharedPreferences.edit()) {
                putLong("last_update", (System.currentTimeMillis() / 1000))
                apply()
            }
        }
    }

    // Uses the Fused Location Provider API to get the device's location
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        with(sharedPreferences.edit()) {
                            putFloat("user_lat", location.latitude.toFloat())
                            putFloat("user_long", location.longitude.toFloat())
                            apply()
                        }
                    }
                }
            } else {
                Toast.makeText(baseContext, "Please enable location", Toast.LENGTH_LONG).show()
                val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(locationIntent, LOCATION_INTENT_CODE)
            }
        } else {
            requestPermissions()
        }
    }

    // Gets new location data if lastLocation is empty
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    // Callback object for getting location
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            val lastLocation: Location = locationResult!!.lastLocation
            with(sharedPreferences.edit()) {
                putFloat("user_lat", lastLocation.latitude.toFloat())
                putFloat("user_long", lastLocation.longitude.toFloat())
                apply()
            }
        }
    }

    // Checks if app has required location permissions
    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Checks if location services are enabled on the device
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Requests location permissions if not already granted
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            ACCESS_FINE_LOCATION
        )
    }

    // Gets current location after successful permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    companion object {
        private const val ACCESS_FINE_LOCATION = 1
        private const val LOCATION_INTENT_CODE = 101
    }
}
