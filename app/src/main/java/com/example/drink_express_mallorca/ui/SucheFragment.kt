package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.adapter.SucheAdapter
import com.example.drink_express_mallorca.databinding.FragmentSucheBinding


class SucheFragment : Fragment() {
    private var _binding: FragmentSucheBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSucheBinding.inflate(inflater, container, false)
        val view = binding.root
        val sucheAdapter = SucheAdapter()

        binding.sucheRv.adapter = sucheAdapter

        binding.sucheBtn.setOnClickListener {
            if (!binding.sucheEdit.text.isNullOrEmpty()){
                viewModel.loadDrinkFromSearch(binding.sucheEdit.text.toString())
            }
        }

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        viewModel.drinkSearch.observe(viewLifecycleOwner){
            sucheAdapter.submitList(it)
        }

        binding.sucheZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        bottomNavigation(view)

        return view
    }

    private fun bottomNavigation(view: View){
        binding.suchebotnavHomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_sucheFragment_to_homeFragment)
        }

        binding.suchebotnavKategorieBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_sucheFragment_to_kategorieFragment)
        }

        binding.suchebotnavWarenkorbBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_sucheFragment_to_warenkorbFragment)
        }

        binding.suchebotnavProfilBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_sucheFragment_to_profilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}