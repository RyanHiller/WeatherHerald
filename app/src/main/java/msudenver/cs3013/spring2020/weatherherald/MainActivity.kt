package msudenver.cs3013.spring2020.weatherherald

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        queue = Volley.newRequestQueue(this)
        textView = findViewById(R.id.textView2)
    }

    override fun onResume() {
        super.onResume()
        val url = "http://www.google.com"
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                val text = "Response is: ${response.substring(0, 500)}"
                textView.text = text
            }, Response.ErrorListener { textView.text = getString(R.string.str_http_fail) })

        queue.add(stringRequest)
    }
}
