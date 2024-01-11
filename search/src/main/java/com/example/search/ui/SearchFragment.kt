package com.example.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.search.adapter.Adapter
import com.example.search.data.States
import com.example.search.databinding.FragmentSearchBinding
import com.example.search.domain.models.City
import com.example.search.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter by lazy {
            Adapter {
                onItemClick(it)
            }
        }
        binding.recycler.adapter = adapter

        viewModel.states.observe(viewLifecycleOwner) { state ->
            when (state) {
                States.Error -> {
                    binding.error.visibility = View.VISIBLE
                    binding.coordinator.visibility = View.GONE
                }

                is States.Success -> {
                    binding.error.visibility = View.GONE
                    binding.coordinator.visibility = View.VISIBLE
                    adapter.submitList(state.cityList)

                }
            }
        }

        binding.searchView.editText.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val keyword = s.toString()
                    viewModel.searchCity(keyword)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        )
    }

    private fun onItemClick(city: City) {
        val id = city.id.toString()
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