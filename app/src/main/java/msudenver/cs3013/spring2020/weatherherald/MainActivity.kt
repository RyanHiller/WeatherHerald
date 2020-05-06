package msudenver.cs3013.spring2020.weatherherald

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val apiKey = ""
    private val url = "https://www.google.com"
    private val fragmentManager = supportFragmentManager
    private lateinit var locationManager: LocationManager
    private lateinit var queue: RequestQueue
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var stringRequest: StringRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Render Home fragment by default
        fragmentManager.beginTransaction().replace(R.id.content_layout, HomeFragment()).commit()

        queue = Volley.newRequestQueue(this)
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            //REQUEST RESPONSE HERE
        }, Response.ErrorListener { error ->
            Log.i("WEATHERLOG", "ERROR: $error")
        })

        // Bottom navigation selection listeners
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    val fragment = HomeFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.content_layout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.forecast_page -> {
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
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
                        // TODO: Set lat and long in preferences here
                    }
                }
            } else {
                Toast.makeText(baseContext, "Please enable location", Toast.LENGTH_LONG).show()
                val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(locationIntent)
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
            var lastLocation: Location = locationResult!!.lastLocation
            // TODO: Set lat and long in preferences here
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
    }
}
