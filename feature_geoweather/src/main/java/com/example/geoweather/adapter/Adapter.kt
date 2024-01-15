package com.example.geoweather.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.example.geoweather.databinding.ItemForecastBinding
import com.example.geoweather.domain.models.Forecastday
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate


class Adapter : ListDelegateAdapter<Forecastday>(ForecastdayDiffUtil()) {
    init {
        addDelegate(forecastdayDelegate())
    }
}

fun forecastdayDelegate() = adapterDelegate<Forecastday, Forecastday, ItemForecastBinding>({
    ItemForecastBinding.inflate(
        LayoutInflater.from(it.context), it, false
    )
}) {
    bind {
        val icon = item.day.condition.icon.replace("//", "https://")
        binding.image.load(icon)
        binding.temp.text = "${item.day.maxtemp_c}/${item.day.mintemp_c}"
        binding.condition.text = item.day.condition.text
        val date = item.date.split("-")
        val formattedDate = "${date[2]}/${date[1]}"
        binding.date.text = formattedDate
    }
}

class ForecastdayDiffUtil : DiffUtil.ItemCallback<Forecastday>() {
    override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean =
        oldItem.date == newItem.date

    override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean =
        oldItem.date == newItem.date
}