package msudenver.cs3013.spring2020.weatherherald

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)

        updateUI(view)

        // Inflate the layout for this fragment
        return view
    }

    private fun updateUI(view: View) {
        val currTempView = view.findViewById<TextView>(R.id.currentTemp)
        val dayHighView = view.findViewById<TextView>(R.id.dayHigh)
        val dayLowView = view.findViewById<TextView>(R.id.dayLow)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
        val currTemp = sharedPreferences.getFloat("current_temp", 0.0F)
        val dayHigh = sharedPreferences.getFloat("today_high", 0.0F)
        val dayLow = sharedPreferences.getFloat("today_low", 0.0F)
        val tempUnit = if (PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
                .getBoolean("temp_toggle", false)
        ) "C" else "F"
        val degreeSign = getText(R.string.degreeSign)

        val currTempString = "$currTemp${degreeSign}${tempUnit}"
        val dayHighString = "$dayHigh${degreeSign}${tempUnit}"
        val dayLowString = "$dayLow${degreeSign}${tempUnit}"

        currTempView?.text = currTempString
        dayHighView?.text = dayHighString
        dayLowView?.text = dayLowString
    }
}
