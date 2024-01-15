package com.example.search.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.database.domain.models.WeatherFromDb
import com.example.database.entity.WeatherEntity
import com.example.search.databinding.ItemBinding
import com.example.search.domain.models.City
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate

class Adapter(
    onItemClick: (City) -> Unit,
    onItemClick2: (WeatherFromDb) -> Unit,

    ) : ListDelegateAdapter<WeatherEntity>(WeatherEntityDiffUtil()) {
    init {
        addDelegate(delegateCity(onItemClick))
        addDelegate(delegateWeatherFromDb(onItemClick2))
    }
}

fun delegateCity(
    onItemClick: (City) -> Unit
) = adapterDelegate<WeatherEntity, City, ItemBinding>({
    ItemBinding.inflate(
        LayoutInflater.from(it.context), it, false
    )
}) {
    bind {
        binding.image.visibility = View.GONE
        binding.city.text = item.name + ","
        binding.country.text = item.country
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
}

fun delegateWeatherFromDb(
    onItemClick: (WeatherFromDb) -> Unit
) = adapterDelegate<WeatherEntity, WeatherFromDb, ItemBinding>({
    ItemBinding.inflate(
        LayoutInflater.from(it.context), it, false
    )
}) {
    bind {
        binding.image.visibility = View.VISIBLE
        binding.city.text = item.city + ","
        binding.country.text = item.country
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