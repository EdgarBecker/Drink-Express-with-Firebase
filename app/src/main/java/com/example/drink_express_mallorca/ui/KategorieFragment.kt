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
import com.example.drink_express_mallorca.databinding.FragmentKategorieBinding

class KategorieFragment : Fragment() {
    private var _binding: FragmentKategorieBinding? = null
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
        _binding = FragmentKategorieBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        binding.kategorieZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.kategorieSucheBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieFragment_to_sucheFragment)
        }

        binding.katAlkoholfreiBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(KategorieFragmentDirections.actionKategorieFragmentToKategorieWahlFragment("alkoholfrei"))
        }

        binding.katAlkoholBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(KategorieFragmentDirections.actionKategorieFragmentToKategorieWahlFragment("alkohol"))

        }

        binding.katCocktailBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(KategorieFragmentDirections.actionKategorieFragmentToKategorieWahlFragment("cocktail"))

        }

        binding.katGetrNkeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(KategorieFragmentDirections.actionKategorieFragmentToKategorieWahlFragment("getränke"))

        }

        binding.katChampagneFluteBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(KategorieFragmentDirections.actionKategorieFragmentToKategorieWahlFragment("champagne flute"))

        }

        bottomNavigation(view)

        return view
    }

    private fun bottomNavigation(view:View){
        binding.kategoriebotnavHomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieFragment_to_homeFragment)
        }

        binding.kategoriebotnavWarenkorbBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieFragment_to_warenkorbFragment)
        }

        binding.kategoriebotnavProfilBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieFragment_to_profilFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}