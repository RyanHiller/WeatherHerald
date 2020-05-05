package msudenver.cs3013.spring2020.weatherherald

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val apiKey = ""
    private val url = "https://www.google.com"
    private val fragmentManager = supportFragmentManager
    private lateinit var queue: RequestQueue
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var stringRequest: StringRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            //REQUEST RESPONSE HERE
        }, Response.ErrorListener { error ->
            Log.i("WEATHERLOG", "ERROR: $error")
        })

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
    }

    override fun onResume() {
        super.onResume()
        queue.add(stringRequest)
    }
}
