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
import com.example.drink_express_mallorca.adapter.KategorieWahlAdapter
import com.example.drink_express_mallorca.databinding.FragmentKategorieBinding
import com.example.drink_express_mallorca.databinding.FragmentKategorieWahlBinding
import java.util.*


class KategorieWahlFragment : Fragment() {
    private var _binding: FragmentKategorieWahlBinding? = null
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
        _binding = FragmentKategorieWahlBinding.inflate(inflater, container, false)
        val view = binding.root
        val getKategorie = requireArguments().getString("UI")
        val kategorieWahlAdapter = KategorieWahlAdapter()

        viewModel.loadKategorie(getKategorie!!)

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        viewModel.kategorieWahl.observe(viewLifecycleOwner){
            kategorieWahlAdapter.submitList(it)
        }

        binding.kategorieWahlZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.kategorieWahlSucheBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieWahlFragment_to_sucheFragment)
        }

        binding.katWahlRv.adapter = kategorieWahlAdapter

        binding.katWahlKategorieTv.text = getKategorie.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }

        bottomNavigation(view)

        return view
    }

    private fun bottomNavigation(view:View){
        binding.katwahlbotnavHomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieWahlFragment_to_homeFragment)
        }

        binding.katwahlbotnavWarenkorbBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieWahlFragment_to_warenkorbFragment)
        }

        binding.katwahlbotnavProfilBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_kategorieWahlFragment_to_profilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}