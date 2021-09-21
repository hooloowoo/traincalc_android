package eu.highball.traincalc.ui

import ScaleDescriptor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentGaugeBinding
import java.text.DecimalFormat
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


class GaugeFragment : Fragment() {

    private var _binding: FragmentGaugeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGaugeBinding.inflate(inflater, container, false)

        binding.etnGaugeProtoGauge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnGaugeProtoGauge.hasFocus()) {
                    try {
                        val df = DecimalFormat("#.##")
                        val d = s.toString().replace(",".toRegex(), ".").toDouble()
                        val res: Double = d / ScaleDescriptor.selectedScale.scale
                        binding.etnGaugeScaledGauge.setText(df.format(res))
                    } catch (e: Exception) {
                        binding.etnGaugeScaledGauge.setText("")
                    }
                }
                scaleFromProtoGauge()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })


        binding.etnGaugeScaledGauge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnGaugeScaledGauge.hasFocus()) {
                    try {
                        val df = DecimalFormat("#.##")
                        val d = s.toString().replace(",".toRegex(), ".").toDouble()
                        val res: Double = d * ScaleDescriptor.selectedScale.scale
                        binding.etnGaugeProtoGauge.setText(df.format(res))
                    } catch (e: Exception) {
                        binding.etnGaugeProtoGauge.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })



        binding.etnGaugeScaledGauge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            binding.etnGaugeProtoGauge.setText("")
            binding.etnGaugeScaledGauge.setText("")
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_gauge_to_scaleSelectorFragment)
        }
        showNormGauge()
        binding.etnGaugeProtoGauge.clearFocus()
        return binding.root
    }



    private fun showNormGauge() {
        val df = DecimalFormat("#.##")
        binding.txtGaugeNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)
    }


    private fun scaleFromProtoGauge() {
        try {
            val strProto = binding.etnGaugeProtoGauge.text.toString()
            val i = strProto.replace(",".toRegex(), ".").toDouble().toInt()
            for (scale in ScaleDescriptor.scales) {
                if ((scale.scale == ScaleDescriptor.selectedScale.scale) && (i >= scale.gaugeFrom) && (i < scale.gaugeTo)) {
                    ScaleDescriptor.selectedScale = scale
                    binding.btnScale.setParams(scale)
                    showNormGauge()
                    break
                }
            }
        } catch (e : Exception) {
            e.printStackTrace()
            // Nothing to do
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}