package eu.highball.traincalc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentGaugeBinding
import eu.highball.traincalc.databinding.FragmentGuidingRailsBinding
import java.text.DecimalFormat


class GuidingRailsFragment : Fragment() {

    private var _binding: FragmentGuidingRailsBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuidingRailsBinding.inflate(inflater, container, false)

        val df = DecimalFormat("#.##")
        binding.txtGaugeNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)

        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_guiding_rails_to_scaleSelectorFragment)
        }
        binding.txtGuidingRails.text = df.format(ScaleDescriptor.selectedScale.guidingRailDist)

        return binding.root
    }


}