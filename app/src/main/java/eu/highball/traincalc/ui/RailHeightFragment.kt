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
import eu.highball.traincalc.databinding.FragmentRailHeightBinding
import java.text.DecimalFormat


class RailHeightFragment : Fragment() {

    private var _binding: FragmentRailHeightBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRailHeightBinding.inflate(inflater, container, false)
        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)
        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_railHeightFragment_to_scaleSelectorFragment)
        }

        binding.etnRailHeightProto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnRailHeightProto.hasFocus()) {
                    try {
                        val d = s.toString().replace(",".toRegex(), ".").toDouble()
                        binding.etnRailHeightScaled.setText(df.format(calcScaledFromProto(d)))
                        binding.etnRailHeightNEMProfil.setText(calcProfilFromProto(d).toString())
                        binding.etnRailHeightNMRACode.setText(calcCodeFromProto(d).toString())
                    } catch (e: Exception) {
                        binding.etnRailHeightScaled.setText("")
                        binding.etnRailHeightNEMProfil.setText("")
                        binding.etnRailHeightNMRACode.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })


        binding.etnRailHeightScaled.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnRailHeightScaled.hasFocus()) {
                    try {
                        val df = DecimalFormat("#.##")
                        val d = s.toString().replace(",".toRegex(), ".").toDouble()
                        binding.etnRailHeightProto.setText(df.format(calcProtoFromScaled(d)))
                        binding.etnRailHeightNEMProfil.setText(calcProfilFromScaled(d).toString())
                        binding.etnRailHeightNMRACode.setText(calcCodeFromScaled(d).toString())
                    } catch (e: Exception) {
                        binding.etnRailHeightProto.setText("")
                        binding.etnRailHeightNEMProfil.setText("")
                        binding.etnRailHeightNMRACode.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.etnRailHeightNEMProfil.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnRailHeightNEMProfil.hasFocus()) {
                    try {
                        val df = DecimalFormat("#.##")
                        val i = s.toString().replace(",".toRegex(), ".").toInt()
                        binding.etnRailHeightProto.setText(df.format(calcProtoFromProfil(i)))
                        binding.etnRailHeightScaled.setText(df.format(calcScaledFromProfil(i)))
                        binding.etnRailHeightNMRACode.setText(calcCodeFromProfil(i).toString())
                    } catch (e: Exception) {
                        binding.etnRailHeightScaled.setText("")
                        binding.etnRailHeightProto.setText("")
                        binding.etnRailHeightNMRACode.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.etnRailHeightNMRACode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnRailHeightNMRACode.hasFocus()) {
                    try {
                        val df = DecimalFormat("#.##")
                        val i = s.toString().replace(",".toRegex(), ".").toInt()
                        binding.etnRailHeightProto.setText(df.format(calcProtoFromCode(i)))
                        binding.etnRailHeightScaled.setText(df.format(calcScaledFromCode(i)))
                        binding.etnRailHeightNEMProfil.setText(calcProfilFromCode(i).toString())
                   } catch (e: Exception) {
                        binding.etnRailHeightProto.setText("")
                        binding.etnRailHeightScaled.setText("")
                        binding.etnRailHeightNEMProfil.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })


        return binding.root
    }

    private fun calcCodeFromScaled(d : Double) : Int {
        return (d / 25.4 * 1000).toInt()
    }

    private fun calcCodeFromProto(d: Double) : Int {
        return  (d / ScaleDescriptor.selectedScale.scale / 25.4 * 1000).toInt()
    }

    private fun calcScaledFromCode(i : Int) : Double {
        return i * 25.4 / 1000
    }

    private fun calcScaledFromProfil(i : Int) : Double {
        return i.toDouble() / 10
    }

    private fun calcProtoFromCode(i : Int) : Double {
        return (i * 25.4 / 1000 * ScaleDescriptor.selectedScale.scale)
    }

    private fun calcProtoFromScaled(d : Double) : Double {
        return d * ScaleDescriptor.selectedScale.scale
    }

    private fun calcScaledFromProto(d : Double) : Double {
        return d / ScaleDescriptor.selectedScale.scale
    }

    private fun calcProfilFromScaled(d : Double) : Int {
        return (d * 10).toInt()
    }

    private fun calcProfilFromProto(d : Double) : Int {
        return (d / ScaleDescriptor.selectedScale.scale * 10).toInt()
    }

    private fun calcProfilFromCode(i : Int) : Int {
        return (i * 25.4 / 100).toInt()
    }

    private fun calcCodeFromProfil(i : Int) : Int {
        return (i / 25.4 * 100).toInt()
    }

    private fun calcProtoFromProfil(i : Int) : Double {
        return (i * ScaleDescriptor.selectedScale.scale /  10)
    }

}