package msudenver.cs3013.spring2020.weatherherald

import android.os.Bundle
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
        val tempView = activity?.findViewById<TextView>(R.id.current_temp)
        val temperature = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
            .getFloat("current_temp", 0.0F)
        tempView?.text = temperature.toString()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
