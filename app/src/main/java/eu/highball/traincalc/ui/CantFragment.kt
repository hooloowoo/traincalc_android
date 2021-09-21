package eu.highball.traincalc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentCantBinding
import java.text.DecimalFormat


class CantFragment : Fragment() {

    private var _binding: FragmentCantBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCantBinding.inflate(inflater, container, false)

        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)

        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_cantFragment_to_scaleSelectorFragment)
        }
        binding.txtMaxElevation.text = df.format(ScaleDescriptor.selectedScale.cant)
        return binding.root
    }

}