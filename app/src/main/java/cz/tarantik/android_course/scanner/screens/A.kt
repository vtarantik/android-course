package cz.tarantik.android_course.scanner.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.tarantik.android_course.R
import cz.tarantik.android_course.databinding.FragmentABinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class A : Fragment(R.layout.fragment_a) {
    private var _binding: FragmentABinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: AViewModel by viewModel<AViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel.enableScanner()
        _binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect {
                Log.d("A Fragment", "Fragment A received: $it")
            }
        }

        binding.btnA.setOnClickListener {
            findNavController().navigate(R.id.action_a_to_b)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}