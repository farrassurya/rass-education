package com.example.rass_education.Home.tugas_p10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rass_education.databinding.FragmentLandListBinding

class LandListFragment : Fragment() {

    private var _binding: FragmentLandListBinding? = null
    private val binding get() = _binding!!

    private var isGrid: Boolean = false

    companion object {
        private const val ARG_IS_GRID = "is_grid"

        fun newInstance(isGrid: Boolean) = LandListFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_IS_GRID, isGrid)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isGrid = it.getBoolean(ARG_IS_GRID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyData = listOf(
            LandRecord("10.01.01.01", "H. Ahmad Sulaiman", "2.500", "Sertifikat Hak Milik", "Sawah Produktif"),
            LandRecord("10.01.01.02", "Siti Aminah", "1.200", "Sertifikat Hak Guna", "Pemukiman"),
            LandRecord("10.01.01.03", "Kelompok Tani Makmur", "5.000", "Tanah Kas Desa", "Kebun Kolektif"),
            LandRecord("10.01.01.04", "Budi Santoso", "800", "Sertifikat Hak Milik", "Pekarangan"),
            LandRecord("10.01.01.05", "Dewi Sartika", "3.100", "Akta Jual Beli", "Perkebunan Cengkeh")
        )

        binding.rvLandList.layoutManager = if (isGrid) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }

        binding.rvLandList.adapter = LandAdapter(dummyData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
