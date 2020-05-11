package msudenver.cs3013.spring2020.weatherherald

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.*
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val days: ArrayList<String> = ArrayList()

        for(i in 1..7){
            days.add("Day #$i")
        }

        forecast_content.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        forecast_content.adapter = ForecastDays(days)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(forecast_content)
    }

}
