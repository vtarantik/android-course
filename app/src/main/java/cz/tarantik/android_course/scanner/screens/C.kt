package cz.tarantik.android_course.scanner.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentABinding
import cz.tarantik.android_course.databinding.FragmentCBinding

class C: Fragment(R.layout.fragment_c) {
    private var _binding: FragmentCBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnC.setOnClickListener {
            findNavController().navigate(R.id.action_c_to_d)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}