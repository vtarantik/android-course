package cz.tarantik.android_course.scanner.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentABinding
import cz.tarantik.android_course.databinding.FragmentBBinding

class B: Fragment(R.layout.fragment_b) {
    private var _binding: FragmentBBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnB.setOnClickListener {
            findNavController().navigate(R.id.action_b_to_c)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}