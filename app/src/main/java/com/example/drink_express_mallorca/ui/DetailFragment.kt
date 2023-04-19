package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import coil.load
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.data.model.WarenkorbItem
import com.example.drink_express_mallorca.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
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
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val getDrinkName = requireArguments().getString("getränk")
        var randomPreis = mutableListOf<Double>(3.50, 4.00, 4.20, 4.80, 5.00, 6.00).random()

        viewModel.loadDrinkFromName(getDrinkName!!)

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        viewModel.warkorbList.observe(viewLifecycleOwner) { list ->

            viewModel.drinkName.observe(viewLifecycleOwner) {
                binding.detailTitelTv.text = it.strDrink
                binding.detailImageIv.load(it.strDrinkThumb)


                binding.detailWarkorbBtn.setOnClickListener { view ->
                    var duplicate = viewModel.checkForDuplicates(
                        it.strDrinkThumb!!,
                        viewModel.detailGetraenkeAnzahl.value!!
                    )

                    if (!duplicate) {
                        viewModel.warenkorbHinzufügen(
                            image = it.strDrinkThumb,
                            anzahl = viewModel.detailGetraenkeAnzahl.value!!,
                            preis = randomPreis
                        )
                    }

                    Toast.makeText(
                        requireContext(),
                        "Getränk wurde hinzugefügt",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.loadWarkorbList()
                }

            }

        }

        viewModel.detailGetraenkeAnzahl.observe(viewLifecycleOwner) {
            var totalPreis = randomPreis * it
            binding.detailGetrNkeAnzahlTv.text = it.toString()
            binding.detailTotalPreisTv.text = "€${"%.2f".format(totalPreis)}"
        }

        binding.detailZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.detailSucheBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_sucheFragment)
        }

        binding.detailMinusIbtn.setOnClickListener {
            viewModel.getraenkeAnzahlAendern(false)
        }

        binding.detailPlusIbtn.setOnClickListener {
            viewModel.getraenkeAnzahlAendern(true)
        }

        bottomNavigation(view)

        return view
    }

    private fun bottomNavigation(view: View) {
        binding.detailbotnavHomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_homeFragment)
        }

        binding.detailbotnavKategorieBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_detailFragment_to_kategorieFragment)
        }

        binding.detailbotnavWarenkorbBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_detailFragment_to_warenkorbFragment)
        }

        binding.detailbotnavProfilBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_detailFragment_to_profilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetGetraenkAnzahl()
        _binding = null
    }
}