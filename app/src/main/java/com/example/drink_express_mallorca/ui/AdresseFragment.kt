package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.adapter.WarenkorbAdapter
import com.example.drink_express_mallorca.databinding.FragmentAdresseBinding

class AdresseFragment : Fragment() {

    private var _binding: FragmentAdresseBinding? = null
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
        _binding = FragmentAdresseBinding.inflate(inflater, container, false)
        val view = binding.root
        var warenkorbAdapter = WarenkorbAdapter(viewModel)

        binding.adresseZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.adresseSucheBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_adresseFragment_to_sucheFragment)
        }

        binding.adresseWeiterBtn.setOnClickListener {
            if (!viewModel.warenkorb.isEmpty()){
                Toast.makeText(requireContext(),"Bezahlung bestätigt", Toast.LENGTH_LONG).show()
            }

            viewModel.warenkorb.clear()
            viewModel.preisKalkulation(viewModel.warenkorb)
            warenkorbAdapter.notifyDataSetChanged()
            Navigation.findNavController(view).navigate(R.id.action_adresseFragment_to_bestaetigtFragment)
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}