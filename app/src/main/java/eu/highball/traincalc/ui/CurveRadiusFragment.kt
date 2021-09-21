package eu.highball.traincalc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentCurveRadiusBinding
import java.text.DecimalFormat

class CurveRadiusFragment : Fragment() {

    private var _binding: FragmentCurveRadiusBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurveRadiusBinding.inflate(inflater, container, false)

        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)

        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_curveRadiusFragment_to_scaleSelectorFragment)
        }
        binding.txtMinimumRadius.text = df.format(ScaleDescriptor.selectedScale.minRadius)
        binding.txtRecMinRadAtStations.text = df.format(ScaleDescriptor.selectedScale.rStation)
        binding.txtRecMinRadOnBranchLines.text = df.format(ScaleDescriptor.selectedScale.rBranch)
        binding.txtRecMinRadOnMainLines.text = df.format(ScaleDescriptor.selectedScale.rMain)
        return binding.root
    }
}