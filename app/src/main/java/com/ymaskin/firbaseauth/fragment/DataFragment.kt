package com.ymaskin.firbaseauth.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ymaskin.firbaseauth.R
import com.ymaskin.firbaseauth.databinding.FragmentDataBinding
import com.ymaskin.firbaseauth.viewmodel.AuthViewModel

@SuppressLint("SetTextI18n")
class DataFragment : Fragment() {
    private lateinit var binding: FragmentDataBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSearch.setOnClickListener {
                if (etSearch.text.isNotBlank()) {
                    val user = viewModel.getUser(etSearch.text.toString())
                    user?.let {
                        binding.tvResult.text = "ID: ${it.id}\nName: ${it.name}\n" +
                                "Phone: ${it.phone}\nEmail: ${it.email}"
                    } ?: run {
                        binding.tvResult.text = ""
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.btnSignOut.setOnClickListener {
            viewModel.signOutUser()
            findNavController().navigate(R.id.authFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerUsersListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterListener()
    }
}