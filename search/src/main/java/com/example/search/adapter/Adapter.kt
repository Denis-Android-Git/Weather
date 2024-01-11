package com.example.search.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.example.search.databinding.ItemBinding
import com.example.search.domain.models.City
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate

class Adapter(
    onItemClick: (City) -> Unit
) : ListDelegateAdapter<City>(CityDiffUtil()) {
    init {
        addDelegate(delegate(onItemClick))
    }
}

fun delegate(
    onItemClick: (City) -> Unit
) = adapterDelegate<City, City, ItemBinding>({
    ItemBinding.inflate(
        LayoutInflater.from(it.context), it, false
    )
}) {
    bind {
        binding.city.text = item.name + ","
        binding.country.text = item.country
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
}

class CityDiffUtil : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.id == newItem.id
}