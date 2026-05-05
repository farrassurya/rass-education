package com.example.rass_education.tugas_p3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rass_education.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // // BARU: Fragment Profile
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // // BARU: Set Toolbar Title
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarProfile)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Developer Profile"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}