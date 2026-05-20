package com.example.rass_education.tugas_p7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.rass_education.databinding.FragmentHomeBinding
import com.example.rass_education.tugas_p2.HitungBangunActivity
import com.example.rass_education.tugas_p3.LoginActivity
import com.example.rass_education.tugas_p6.WebViewActivity
import com.example.rass_education.tugas_p4.Custom1Activity
import com.example.rass_education.tugas_p4.Custom2Activity
import com.example.rass_education.tugas_10.TabLayoutActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // // BARU: Fragment Home pindahan dari WelcomeActivity
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Farrassurya")
        binding.tvWelcomeTitle.text = "Welcome $username!"

        // // MODIF: Pindahan Logic Menu
        binding.btnRumus.setOnClickListener {
            startActivity(Intent(requireContext(), HitungBangunActivity::class.java))
        }

        binding.btnFocus.setOnClickListener {
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }

        binding.btnInspirasi.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.btnTabLayout.setOnClickListener {
            startActivity(Intent(requireContext(), TabLayoutActivity::class.java))
        }

        // // MODIF: Pindahan Logic Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    sharedPref.edit().remove("isLogin").apply()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}