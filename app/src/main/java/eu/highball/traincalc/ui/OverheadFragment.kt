package eu.highball.traincalc.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentOverheadBinding
import java.text.DecimalFormat
import kotlin.math.sqrt


class OverheadFragment : Fragment() {
    private var _binding: FragmentOverheadBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverheadBinding.inflate(inflater, container, false)
        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)
        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_overheadFragment_to_scaleSelectorFragment)
        }

        binding.txtMaxHorizDeflection.text = df.format(getZigZag())
        binding.txtOverheadHeightHighest.text = df.format(getHighPos())
        binding.txtOverheadHeightNormal.text = df.format(getNormalPos())
        binding.txtOverheadHeightLowest.text = df.format(getLowPos())

        binding.etnOverheadMastRadius.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                try {
                    val d = s.toString().replace(",".toRegex(), ".").toDouble()
                    val res: String = calculateCurve(d)
                    binding.txtMastDistanceInCurves.text = res
                } catch (e: Exception) {
                    binding.txtMastDistanceInCurves.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        return binding.root
    }

    private fun getZigZag() : Double {
        val scale = ScaleDescriptor.selectedScale
        if (scale.name.startsWith("H0")) {
            return 3.0
        } else if (scale.name.startsWith("TT")) {
            return 2.0
        } else if (scale.name.startsWith("N")) {
            return 1.5
        } else if (scale.name.startsWith("Z")) {
            return 1.0
        }
        return 0.0
    }

    private fun getLowPos() : Int {
        val scale = ScaleDescriptor.selectedScale
        if (scale.name == "H0") {
            return 60
        } else if (scale.name.startsWith("H0")) {
            return 50
        } else if (scale.name == "TT") {
            return 44
        } else if (scale.name.startsWith("TT")) {
            return 38
        } else if (scale.name == "N") {
            return 34
        } else if (scale.name.startsWith("N")) {
            return 29
        } else if (scale.name == "Z") {
            return 25
        }
        return 0
    }

    private fun getNormalPos() : Int {
        val scale = ScaleDescriptor.selectedScale
        if (scale.name == "H0") {
            return 69
        } else if (scale.name.startsWith("H0")) {
            return 65
        } else if (scale.name == "TT") {
            return 50
        } else if (scale.name.startsWith("TT")) {
            return 47
        } else if (scale.name == "N") {
            return 38
        } else if (scale.name.startsWith("N")) {
            return 35
        } else if (scale.name == "Z") {
            return 28
        }
        return 0
    }

    private fun getHighPos() : Int {
        val scale = ScaleDescriptor.selectedScale
        if (scale.name == "H0") {
            return 73
        } else if (scale.name.startsWith("H0")) {
            return 70
        } else if (scale.name == "TT") {
            return 52
        } else if (scale.name.startsWith("TT")) {
            return 51
        } else if (scale.name == "N") {
            return 40
        } else if (scale.name.startsWith("N")) {
            return 38
        } else if (scale.name == "Z") {
            return 30
        }
        return 0
    }

    private fun calculateCurve(r : Double) : String {
        val zigzag = getZigZag()
        if (zigzag != 0.0) {
            return (4 * sqrt(r * zigzag)).toInt().toString()
        }
        return "-"
    }


}