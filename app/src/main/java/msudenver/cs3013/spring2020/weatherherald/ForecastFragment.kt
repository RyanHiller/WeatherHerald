package msudenver.cs3013.spring2020.weatherherald

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager

class ForecastFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pm = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
        Log.i("WEATHERLOG", "TEST1: ${pm.all}")
        with(pm.edit()) {
            putString("user_temp_high", "99")
            apply()
        }
        Log.i("WEATHERLOG", "TEST1: ${pm.all}")


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }
}
