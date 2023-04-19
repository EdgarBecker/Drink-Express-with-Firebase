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
import com.example.drink_express_mallorca.databinding.FragmentRegistrierenBinding

class RegistrierenFragment : Fragment() {
    private var _binding: FragmentRegistrierenBinding? = null
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

        _binding = FragmentRegistrierenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.registrierenRegistrierenBtn.setOnClickListener {
            val email = binding.registrierenBenutzernameEdit.text.toString()
            val passw1 = binding.registrierenPassw1Edit.text.toString()
            val passw2 = binding.registrierenPassw2Edit.text.toString()

            if (passw2 == passw1){
                viewModel.registrieren(email, passw2)
            } else {
                Toast.makeText(requireContext(), "Die Passwörter stimmen nicht über ein", Toast.LENGTH_LONG).show()
            }
        }

        binding.RegistrierenZurCkBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registrierenFragment_to_login_screen)
        }

        viewModel.currentUser.observe(viewLifecycleOwner){
            if (it != null){
                Navigation.findNavController(view).navigate(R.id.homeFragment)
            }
        }

        viewModel.toast.observe(viewLifecycleOwner){
            if (it != null){
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}