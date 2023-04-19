package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.databinding.FragmentProfilBinding



class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
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
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root


        viewModel.currentUser.observe(viewLifecycleOwner){
            val gastNutzer = "gast@gast.gast"

            if (it == null){
                Navigation.findNavController(view).navigate(R.id.login_screen)
            } else {
                if (it.email == gastNutzer){
                    binding.profileLoginBtn.visibility = View.VISIBLE
                    binding.profileLoginBtn.setOnClickListener {
                        Navigation.findNavController(view).navigate(R.id.action_profilFragment_to_login_screen)
                        viewModel.abmelden()
                    }
                } else {
                    binding.profileLoginBtn.visibility = View.GONE
                }
            }
        }

        binding.profilAbmeldenBtn.setOnClickListener {
            viewModel.abmelden()
        }

        bottomNavigation(view)

        viewModel.warkorbList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.notificationIv.visibility = View.VISIBLE
            } else {
                binding.notificationIv.visibility = View.GONE
            }
        }

        return view
    }

    private fun bottomNavigation(view: View){
        binding.profilbotnavHomeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profilFragment_to_homeFragment)
        }

        binding.profilbotnavKategorieBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profilFragment_to_kategorieFragment)
        }

        binding.profilbotnavWarenkorbBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profilFragment_to_warenkorbFragment)
        }
    }
}