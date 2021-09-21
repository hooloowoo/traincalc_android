package eu.highball.traincalc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentTrackbedBinding
import java.text.DecimalFormat

class TrackbedFragment : Fragment() {
    private var _binding: FragmentTrackbedBinding? = null

    private val binding get() = _binding!!

    var a : Double = 0.0
    var b : Double = 0.0
    var c : Double = 0.0
    var d : Double = 0.0
    var e : Double = 0.0
    var f : Double = 0.0
    var h : Double = 0.0
    var k : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackbedBinding.inflate(inflater, container, false)
        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)
        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_trackbedFragment_to_scaleSelectorFragment)
        }
        setFields()
        return binding.root
    }

    private fun setFields() {
        calculate()
        val df = DecimalFormat("#.##")
        if (ScaleDescriptor.selectedScale.isNarrow()) {
            binding.layoutNarrow.visibility = View.VISIBLE
            binding.layoutNormal.visibility = View.GONE

            binding.txtAnVal.text = df.format(a)
            binding.txtBnVal.text = df.format(b)
            binding.txtCnVal.text = df.format(c)
            binding.txtKnVal.text = df.format(k)
            binding.txtFnVal.text = df.format(f)
            binding.txtHnVal.text = df.format(h)


        } else {
            binding.layoutNarrow.visibility = View.GONE
            binding.layoutNormal.visibility = View.VISIBLE
            binding.txtANVal.text = df.format(a)
            binding.txtBNVal.text = df.format(b)
            binding.txtCNVal.text = df.format(c)
            binding.txtDNVal.text = df.format(d)
            binding.txtENVal.text = df.format(e)
            binding.txtFNVal.text = df.format(f)
            binding.txtHNVal.text = df.format(h)

        }
    }



    private fun calculate() {
        val scale = ScaleDescriptor.selectedScale
        when (scale.name) {
            "Z" -> {
                a=12.0
                b=16.0
                c=28.0
                d=3.0
                e=2.0
                f=2.0
                h=4.0
            }
            "N" -> {
                a=16.0
                b=22.0
                c=38.0
                d=5.0
                e=3.0
                f=3.0
                h=6.0
            }
            "Nm" -> {
                a=12.0
                b=14.0
                c=26.0
                k=2.0
                f=1.5
                h=4.0
            }
            "TT" -> {
                a=22.0
                b=28.0
                c=50.0
                d=7.0
                e=4.0
                f=5.0
                h=8.0
            }
            "TTm" -> {
                a=15.0
                b=18.0
                c=35.0
                k=3.0
                f=2.5
                h=5.0
            }
            "TTe" -> {
                a=12.5
                b=16.0
                c=25.0
                k=3.0
                f=2.5
                h=3.5
            }
            "H0" -> {
                a=30.0
                b=38.0
                c=70.0
                d=9.0
                e=5.0
                f=6.0
                h=10.0
            }
            "H0m" -> {
                a=21.0
                b=25.0
                c=48.0
                k=4.0
                f=3.0
                h=6.0
            }
            "H0e" -> {
                a=17.0
                b=22.0
                c=35.0
                k=4.0
                f=3.0
                h=4.5
            }
            "H0i" -> {
                a=15.0
                b=20.0
                c=35.0
                k=4.0
                f=3.0
                h=0.0
            }
        }
    }

}