package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.util.Log
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
import com.example.drink_express_mallorca.databinding.FragmentWarenkorbBinding


class WarenkorbFragment : Fragment() {
    private var _binding: FragmentWarenkorbBinding? = null
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
        _binding = FragmentWarenkorbBinding.inflate(inflater, container, false)
        val view = binding.root
        val warenkorbAdapter = WarenkorbAdapter(viewModel)

        viewModel.loadWarkorbList()


        binding.warkorbZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.warkorbSucheBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(WarenkorbFragmentDirections.actionWarenkorbFragmentToSucheFragment())
        }

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        viewModel.warkorbList.observe(viewLifecycleOwner){
            warenkorbAdapter.submitWarenkorbList(it)
            viewModel.preisKalkulation(it)
            if (it.isEmpty()){
                binding.warkorbLeerTv.visibility = View.VISIBLE
            } else {
                binding.warkorbLeerTv.visibility = View.GONE
            }
        }
        viewModel.warenkorbTotalPreis.observe(viewLifecycleOwner){
            binding.warenkorbTotalPreisTv.text = "${"%.2f".format(it)}€"
        }


        binding.warkorbBezahlenBtn.setOnClickListener {
            if (viewModel.warenkorb.isEmpty()){
                Toast.makeText(requireContext(),"Warenkorb ist leer", Toast.LENGTH_LONG).show()
            } else {
                Navigation.findNavController(view).navigate(R.id.action_warenkorbFragment_to_adresseFragment)
            }
        }

        binding.warenkorbRv.adapter = warenkorbAdapter

        bottomNavigation(view)

        return view
    }

    private fun bottomNavigation(view: View){
        binding.warenkorbbotnavHomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_warenkorbFragment_to_homeFragment)
        }

        binding.warenkorbbotnavKategorieBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_warenkorbFragment_to_kategorieFragment)
        }

        binding.warenkorbbotnavProfilBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_warenkorbFragment_to_profilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}