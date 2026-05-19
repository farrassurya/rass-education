package com.example.rass_education.tugas_p9

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rass_education.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // // BARU: Fragment Settings dengan Custom ListView
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // // BARU: Set Toolbar Title
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarSettings)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Settings"

        // // IMPROVISASI: Data untuk SimpleAdapter (Icon, Title, Desc)
        val data = ArrayList<Map<String, Any>>()

        val items = arrayOf(
            Pair("Privacy Policy", "Kelola data dan privasi akun Anda"),
            Pair("Terms of Service", "Syarat dan ketentuan penggunaan aplikasi"),
            Pair("About Bina Desa", "Informasi lengkap mengenai proyek Bina Desa"),
            Pair("Help Center", "Pusat bantuan dan panduan pengguna"),
            Pair("Contact Support", "Hubungi tim teknis kami"),
            Pair("App Version", "Versi 1.0.5 (Stable Build)")
        )

        val icons = arrayOf(
            R.drawable.ic_lock_idle_lock,
            R.drawable.ic_menu_agenda,
            R.drawable.ic_menu_info_details,
            R.drawable.ic_menu_help,
            R.drawable.ic_menu_call,
            R.drawable.ic_menu_set_as
        )

        for (i in items.indices) {
            val map = HashMap<String, Any>()
            map["title"] = items[i].first
            map["desc"] = items[i].second
            map["icon"] = icons[i]
            data.add(map)
        }

        // // BARU: Implementasi SimpleAdapter untuk tampilan yang lebih "keren"
        val from = arrayOf("icon", "title", "desc")
        val to = intArrayOf(com.example.rass_education.R.id.ivSettingIcon, com.example.rass_education.R.id.tvSettingTitle, com.example.rass_education.R.id.tvSettingDesc)

        val adapter = SimpleAdapter(
            requireContext(),
            data,
            com.example.rass_education.R.layout.item_setting,
            from,
            to
        )

        binding.lvSettings.adapter = adapter

        // // BARU: OnItemClickListener untuk ListView
        binding.lvSettings.setOnItemClickListener { _, _, position, _ ->
            val selectedTitle = items[position].first
            Toast.makeText(requireContext(), "Membuka: $selectedTitle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}