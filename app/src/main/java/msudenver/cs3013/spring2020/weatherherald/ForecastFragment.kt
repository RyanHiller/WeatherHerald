package msudenver.cs3013.spring2020.weatherherald

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.*
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.day_post.*
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

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)

        val forecastdayHigh = sharedPreferences.getFloat("today_high", 0.0F)
        val forecastdayLow = sharedPreferences.getFloat("today_low", 0.0F)
        val forecastdayHigh1 = sharedPreferences.getFloat("day1_high", 0.0F)
        val forecastdayLow1 = sharedPreferences.getFloat("day1_low", 0.0F)
        val forecastdayHigh2 = sharedPreferences.getFloat("day2_high", 0.0F)
        val forecastdayLow2 = sharedPreferences.getFloat("day2_low", 0.0F)
        val forecastdayHigh3 = sharedPreferences.getFloat("day3_high", 0.0F)
        val forecastdayLow3 = sharedPreferences.getFloat("day3_low", 0.0F)
        val forecastdayHigh4 = sharedPreferences.getFloat("day4_high", 0.0F)
        val forecastdayLow4 = sharedPreferences.getFloat("day4_low", 0.0F)
        val forecastdayHigh5 = sharedPreferences.getFloat("day5_high", 0.0F)
        val forecastdayLow5 = sharedPreferences.getFloat("day5_low", 0.0F)
        val forecastdayHigh6 = sharedPreferences.getFloat("day6_high", 0.0F)
        val forecastdayLow6 = sharedPreferences.getFloat("day6_low", 0.0F)
        val forecastdayHigh7 = sharedPreferences.getFloat("day7_high", 0.0F)
        val forecastdayLow7 = sharedPreferences.getFloat("day7_low", 0.0F)


        val tempUnit = if (PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)
                .getBoolean("temp_toggle", false)
        ) "C" else "F"
        val degreeSign = getText(R.string.degreeSign)


        for(i in 1..8){
            days.add("Day #$i")
        }

        forecast_scroll.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        forecast_scroll.adapter = ForecastDays(days)

        val mLayoutManager = forecast_scroll.layoutManager

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(forecast_scroll)
        //https://developer.android.com/reference/android/support/v7/widget/RecyclerView.OnScrollListener.html
        //source: https://stackoverflow.com/questions/8140623/android-onscrollstatechanged-scroll-state-idle-sometimes-doesnt-fire
        forecast_scroll.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView:RecyclerView,newState:Int){
                super.onScrollStateChanged(recyclerView, newState)
                val centerView = snapHelper.findSnapView(mLayoutManager)
                val position = mLayoutManager?.getPosition(centerView!!)
                if(position == 0){
                    val currentHighString = "$forecastdayHigh${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }
                if(position ==1 ){
                    val currentHighString = "$forecastdayHigh1${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow1${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }
                if(position ==2){
                    val currentHighString = "$forecastdayHigh2${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow2${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }
                if(position == 3){
                    val currentHighString = "$forecastdayHigh3${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow3${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }
                if(position == 4){
                    val currentHighString = "$forecastdayHigh4${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow4${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }
                if(position ==5){
                    val currentHighString = "$forecastdayHigh5${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow5${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }
                if(position ==6){
                    val currentHighString = "$forecastdayHigh6${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow6${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString

                }
                if(position == 7){
                    val currentHighString = "$forecastdayHigh7${degreeSign}${tempUnit}"
                    val currentLowString = "$forecastdayLow7${degreeSign}${tempUnit}"
                    forecast_dayHigh?.text = currentHighString
                    forecast_dayLow?.text = currentLowString
                }

            }
        })

}

}
