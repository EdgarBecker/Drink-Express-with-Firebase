package com.example.drink_express_mallorca.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.drink_express_mallorca.MainViewModel
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.databinding.LogInScreenBinding

class Login_screen : Fragment() {
    private var _binding: LogInScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = LogInScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.loginAnmeldenBtn.setOnClickListener {
            val email = binding.loginUsernameEdit.text.toString().lowercase()
            val passw = binding.loginPasswortEdit.text.toString()

            if (email.isNotEmpty() && passw.isNotEmpty()){
                viewModel.anmelden(email, passw)
            }
        }

        binding.loginGastAnmeldenBtn.setOnClickListener {
            val email = "gast@gast.gast"
            val passw = "gast1234"

            viewModel.anmelden(email, passw)
        }

        binding.loginRegistrierenBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_screen_to_registrierenFragment)
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

