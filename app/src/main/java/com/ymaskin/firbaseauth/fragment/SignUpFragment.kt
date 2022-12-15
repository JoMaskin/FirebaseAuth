package com.ymaskin.firbaseauth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ymaskin.firbaseauth.R
import com.ymaskin.firbaseauth.databinding.FragmentSignUpBinding
import com.ymaskin.firbaseauth.model.User
import com.ymaskin.firbaseauth.util.isValidEmail
import com.ymaskin.firbaseauth.util.isValidPassword
import com.ymaskin.firbaseauth.viewmodel.AuthViewModel
import java.util.UUID

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isValidEmail() && password.isValidPassword()) {
                    viewModel.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                saveUserData()
                                findNavController().navigate(R.id.dataFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Registration failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Enter valid credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveUserData() {
        val name = binding.etName.text.toString()
        val phone = binding.etPhone.text.toString()
        val email = binding.etEmail.text.toString()
        val user = User(UUID.randomUUID().toString(), name, phone, email)
        viewModel.saveUserData(user)
    }
}
