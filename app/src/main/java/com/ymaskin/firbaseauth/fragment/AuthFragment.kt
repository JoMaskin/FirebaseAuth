package com.ymaskin.firbaseauth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ymaskin.firbaseauth.R
import com.ymaskin.firbaseauth.databinding.FragmentAuthBinding
import com.ymaskin.firbaseauth.viewmodel.AuthViewModel

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.getCurrentUser() != null) {
            findNavController().navigate(R.id.dataFragment)
        }

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }
}
