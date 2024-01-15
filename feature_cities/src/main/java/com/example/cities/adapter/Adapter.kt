package com.example.cities.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.example.cities.databinding.ItemCitiesBinding
import com.example.database.domain.models.WeatherFromDb
import com.example.database.entity.WeatherEntity
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate

class Adapter(
    onItemClick: (WeatherFromDb) -> Unit
) : ListDelegateAdapter<WeatherEntity>(WeatherEntityDiffUtil()) {
    init {
        addDelegate(delegateWeatherFromDb(onItemClick))
    }
}

fun delegateWeatherFromDb(
    onItemClick: (WeatherFromDb) -> Unit
) = adapterDelegate<WeatherEntity, WeatherFromDb, ItemCitiesBinding>({
    ItemCitiesBinding.inflate(
        LayoutInflater.from(it.context), it, false
    )
}) {
    bind {
        val dateList = item.date.split(" ")
        binding.date.text = dateList[0]
        binding.location.text = "${item.city}/${item.country}"
        binding.temp.text = item.temperature
        binding.image.load(item.image)
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
}

class WeatherEntityDiffUtil : DiffUtil.ItemCallback<WeatherEntity>() {
    override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean =
        oldItem.id == newItem.id
}