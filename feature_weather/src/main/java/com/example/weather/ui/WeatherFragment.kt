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

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                States.Loading -> binding.progress.visibility = View.VISIBLE

                is States.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.city.text = state.weather.location.name
                    binding.country.text = state.weather.location.country
                    binding.temp.text = state.weather.current.temp_c.toString()
                    binding.feelsLike.text =
                        "Feels like ${state.weather.current.feelslike_c.toString()}"
                    binding.wind.text = "Wind ${state.weather.current.wind_kph} km/h"
                    binding.condition.text = state.weather.current.condition.text
                    val icon = state.weather.current.condition.icon.replace("//", "https://")
                    binding.image.load(icon)

                    viewModel.upsertWeather(
                        id = args.id.toInt(),
                        city = state.weather.location.name,
                        country = state.weather.location.country,
                        date = state.weather.location.localtime,
                        condition = state.weather.current.condition.text,
                        feelsLike = state.weather.current.feelslike_c.toString(),
                        image = state.weather.current.condition.icon.replace("//", "https://"),
                        temperature = state.weather.current.temp_c.toString(),
                        wind = state.weather.current.wind_kph.toString()
                    )
                }

                is States.SuccessDb -> {
                    if (state.weather == null) {
                        binding.date.text = "No saved data"
                        binding.progress.visibility = View.GONE
                    } else {
                        binding.date.text = "Data saved on: ${state.weather.date}"
                        binding.progress.visibility = View.GONE
                        binding.city.text = state.weather.city
                        binding.country.text = state.weather.country
                        binding.temp.text = state.weather.temperature
                        binding.feelsLike.text =
                            "Feels like ${state.weather.feelsLike}"
                        binding.wind.text = "Wind ${state.weather.wind} km/h"
                        binding.condition.text = state.weather.condition
                        binding.image.load(state.weather.image)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}