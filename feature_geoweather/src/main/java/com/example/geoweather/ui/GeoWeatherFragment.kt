package com.example.geoweather.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil.load
import com.example.geoweather.adapter.Adapter
import com.example.geoweather.data.PermissionEvent
import com.example.geoweather.data.States
import com.example.geoweather.data.ViewState
import com.example.geoweather.databinding.FragmentGeoWeatherBinding
import com.example.geoweather.permissions.Permissions
import com.example.geoweather.viewmodel.GeoWeatherVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeoWeatherFragment : Fragment() {

    private var _binding: FragmentGeoWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<GeoWeatherVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeoWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                if (map.values.all { it }) {
                    Toast.makeText(requireContext(), "Permissions granted", Toast.LENGTH_LONG)
                        .show()
                    viewModel.handle(PermissionEvent.Granted)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permissions are not granted",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.handle(PermissionEvent.Revoked)
                }
            }
        val permissions = Permissions(requireContext(), launcher, viewModel)

        val adapter by lazy {
            Adapter()
        }
        binding.recycler.adapter = adapter

        permissions.checkPermissions()

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ViewState.RevokedPermissions -> {
                    binding.permissions.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                }

                is ViewState.Success -> {
                    binding.permissions.visibility = View.GONE
                    if (state.location != null) {
                        val latitude = state.location.latitude.toString()
                        val longitude = state.location.longitude.toString()
                        viewModel.getWeather(latitude, longitude)
                        viewModel.getForeCast(latitude, longitude)
                    }
                }
            }
        }
        viewModel.weather.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.city.text = it.location.name
                binding.country.text = it.location.country
                binding.temp.text = it.current.temp_c.toString()
                binding.feelsLike.text = it.current.feelslike_c.toString()//"Feels like ${it.current.feelslike_c.toString()}"
                binding.wind.text = it.current.wind_kph.toString() //"Wind ${it.current.wind_kph.toString()} km/h"
                binding.condition.text = it.current.condition.text
                val icon = it.current.condition.icon.replace("//", "https://")
                binding.image.load(icon)
            }
        }
        viewModel.foreCast.observe(viewLifecycleOwner) {
            adapter.submitList(it?.forecast?.forecastday)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                States.Loading -> binding.progress.visibility = View.VISIBLE
                States.Success -> binding.progress.visibility = View.GONE
                States.Error -> {
                    binding.progress.visibility = View.GONE
                    binding.permissions.visibility = View.VISIBLE
                    //binding.permissions.text = "No Internet"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}