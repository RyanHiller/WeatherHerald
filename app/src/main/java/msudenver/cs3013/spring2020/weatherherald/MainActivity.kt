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
    private lateinit var queue: RequestQueue
    private lateinit var textView: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var stringRequest: StringRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topToolbar))

        queue = Volley.newRequestQueue(this)
        textView = findViewById(R.id.textView2)
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            textView.text = getText(R.string.str_http_pass)
        }, Response.ErrorListener { error ->
            Log.i("WEATHERLOG", "ERROR: $error")
            textView.text = getString(R.string.str_http_fail)
        })

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    textView.text = getText(R.string.str_home)
                    true
                }

                R.id.forecast_page -> {
                    textView.text = getText(R.string.str_forecast)
                    true
                }

                R.id.settings_page -> {
                    textView.text = getText(R.string.str_settings)
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
