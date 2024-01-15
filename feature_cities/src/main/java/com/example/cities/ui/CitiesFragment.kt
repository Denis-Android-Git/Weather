package com.example.cities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.cities.adapter.Adapter
import com.example.cities.data.States
import com.example.cities.databinding.FragmentCitiesBinding
import com.example.cities.viewmodel.CitiesViewModel
import com.example.database.domain.models.WeatherFromDb
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModel<CitiesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getList()

        val adapter by lazy {
            Adapter(::onItemClick)
        }

        binding.recycler.adapter = adapter

        viewModel.states.observe(viewLifecycleOwner) { state ->
            when (state) {
                States.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }

                is States.Success -> {
                    adapter.submitList(state.savedList)
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    private fun onItemClick(weatherFromDb: WeatherFromDb) {
        val id = weatherFromDb.id.toString()
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://weather_fragment/$id".toUri())
            .build()
        findNavController().navigate(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}