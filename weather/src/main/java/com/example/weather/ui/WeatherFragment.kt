package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.weather.data.States
import com.example.weather.databinding.FragmentWeatherBinding
import com.example.weather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<WeatherViewModel>()
    private val args: WeatherFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWeather(args.id)

        viewModel.weather.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.city.text = it.location.name
                binding.country.text = it.location.country
                binding.temp.text = it.current.temp_c.toString()
                binding.feelsLike.text = "Feels like ${it.current.feelslike_c.toString()}"
                binding.wind.text = "Wind ${it.current.wind_kph} km/h"
                binding.condition.text = it.current.condition.text
                val icon = it.current.condition.icon.replace("//", "https://")
                binding.image.load(icon)
            }
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                States.Loading -> binding.progress.visibility = View.VISIBLE
                States.Success -> binding.progress.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}