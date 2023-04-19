package com.example.drink_express_mallorca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.drink_express_mallorca.R
import com.example.drink_express_mallorca.databinding.FragmentBestaetigtBinding


class BestaetigtFragment : Fragment() {

    private var _binding: FragmentBestaetigtBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentBestaetigtBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.bestaetigtZurueckBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_bestaetigtFragment_to_homeFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}