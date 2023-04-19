package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.adapter.HomeAdapter
import com.example.drink_express_mallorca.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val homeAdapter = HomeAdapter()
        val snapHelper = PagerSnapHelper()

        binding.homeRv.adapter = homeAdapter
        snapHelper.attachToRecyclerView(binding.homeRv)

        viewModel.currentUser.observe(viewLifecycleOwner){
            if (it?.email == null){
                Navigation.findNavController(view).navigate(R.id.login_screen)
            }
        }

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        viewModel.loading.observe(viewLifecycleOwner){
            when(it){
                MainViewModel.ApiStatus.LOADING -> binding.homeProgressbar.visibility = View.VISIBLE
                else -> binding.homeProgressbar.visibility = View.GONE
            }
        }

        viewModel.randomCocktailList.observe(viewLifecycleOwner){
            homeAdapter.submitList(it)
        }

        binding.homeSucheIbtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_sucheFragment)
        }

        bottomNavigation(view)

        return view
    }

    private fun bottomNavigation(view: View){
        binding.homebotnavKategorieBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_kategorieFragment)
        }

        binding.homebotnavWarenkorbBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_warenkorbFragment)
        }

        binding.homebotnavProfilBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profilFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}