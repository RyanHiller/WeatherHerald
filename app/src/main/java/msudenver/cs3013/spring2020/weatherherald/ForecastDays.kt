package msudenver.cs3013.spring2020.weatherherald

import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.day_post.view.*

class ForecastDays(val days:ArrayList<String>): RecyclerView.Adapter<ForecastDays.DayViewHolder>(){
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.dayname.text = days[position]
    }

    override fun getItemCount() = days.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view :View = LayoutInflater.from(parent.context).inflate(R.layout.day_post, parent, false)
        return DayViewHolder(view)
    }

    class DayViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val dayname: TextView  = itemview.dayname
    }
}