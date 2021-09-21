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
import eu.highball.traincalc.databinding.FragmentClearanceBinding
import java.text.DecimalFormat
import kotlin.math.sqrt

class ClearanceFragment : Fragment() {

    private var _binding: FragmentClearanceBinding? = null

    private val binding get() = _binding!!

    var b1 : Double = 0.0
    var b2 : Double = 0.0
    var b3 : Double = 0.0
    var b4 : Double = 0.0
    var b5 : Double = 0.0
    var h1 : Double = 0.0
    var h2 : Double = 0.0
    var h3 : Double = 0.0
    var h4 : Double = 0.0
    var h5 : Double = 0.0

    var h : Double = 0.0
    var b : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClearanceBinding.inflate(inflater, container, false)
        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)
        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_clearanceFragment_to_scaleSelectorFragment)
        }
        try {
            calculate(binding.etnCurveRadius.toString().replace(",".toRegex(), ".").toDouble())
        } catch (e: Exception) {
            calculate(0.0)
        }
        setFields()

        binding.etnCurveRadius.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                try {
                    calculate(s.toString().replace(",".toRegex(), ".").toDouble())
                } catch (e: Exception) {
                    calculate(0.0)
                }
                setFields()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        return binding.root
    }

    private fun setFields() {
        val df = DecimalFormat("#.##")
        if (ScaleDescriptor.selectedScale.isNarrow()) {
            binding.txtHVal.text = df.format(h)
            binding.txtBVal.text = df.format(b)
            binding.layoutNarrow.visibility = View.VISIBLE
            binding.layoutNormal.visibility = View.GONE
        } else {
            binding.txtB1Val.text = df.format(b1)
            binding.txtB2Val.text = df.format(b2)
            binding.txtB3Val.text = df.format(b3)
            binding.txtB4Val.text = df.format(b4)
            binding.txtB5Val.text = df.format(b5)
            binding.txtH1Val.text = df.format(h1)
            binding.txtH2Val.text = df.format(h2)
            binding.txtH3Val.text = df.format(h3)
            binding.txtH4Val.text = df.format(h4)
            binding.txtH5Val.text = df.format(h5)
            binding.layoutNarrow.visibility = View.GONE
            binding.layoutNormal.visibility = View.VISIBLE
        }
    }


    private fun getE(r : Double) : Double {
        if (r == 0.0) { return 0.0 }
        var e=0.0
        val scale = ScaleDescriptor.selectedScale
        when {
            scale.isNarrow() -> {
                val a = (13600 / scale.scale)  / 2
                val mr = (r * r) - ( a * a)
                e = if (mr < 0) 0.0 else r - sqrt(mr)
            }
            scale.name == "Z" -> {
                when {
                    r < 200 -> {e=5.0}
                    r < 250 -> {e=4.0}
                    r < 325 -> {e=3.0}
                    r < 450 -> {e=2.0}
                    r < 700 -> {e=1.0}
                }
            }
            scale.name == "N" -> {
                when {
                    r < 250 -> {e=7.0}
                    r < 300 -> {e=6.0}
                    r < 350 -> {e=5.0}
                    r < 450 -> {e=4.0}
                    r < 550 -> {e=3.0}
                    r < 800 -> {e=2.0}
                    r < 1000 -> {e=1.0}
                }
            }
            scale.name == "TT" -> {
                when {
                    r < 325 -> {e=10.0}
                    r < 350 -> {e=9.0}
                    r < 400 -> {e=8.0}
                    r < 450 -> {e=7.0}
                    r < 500 -> {e=6.0}
                    r < 550 -> {e=5.0}
                    r < 700 -> {e=4.0}
                    r < 900 -> {e=3.0}
                    r < 1200 -> {e=2.0}
                    r < 1800 -> {e=1.0}
                }
            }
            scale.name == "H0" -> {
                when {
                    r < 450 -> {e=14.0}
                    r < 500 -> {e=12.0}
                    r < 550 -> {e=11.0}
                    r < 600 -> {e=10.0}
                    r < 700 -> {e=9.0}
                    r < 800 -> {e=7.0}
                    r < 900 -> {e=6.0}
                    r < 1000 -> {e=5.0}
                    r < 1200 -> {e=4.0}
                    r < 1400 -> {e=3.0}
                    r < 1800 -> {e=2.0}
                    r < 2500 -> {e=1.0}
                }
            }
        }
        return e*2;
    }

    private fun calculate(r : Double) {
        val scale = ScaleDescriptor.selectedScale
        if (scale.name == "Z") {
            b1=20.0+getE(r);
            b2=14.0+getE(r);
            b3=18.0+getE(r);
            h1=4.0;
            h2=6.0;
            h3=18.0;
            h4=24.0;
            b4=16.0;
            b5=13.0;
            h5=27.0;
        } else if (scale.name == "Nm") {
            h=26.0;
            b=22.0+getE(r);
        } else if (scale.name == "N") {
            b1=27.0+getE(r);
            b2=18.0+getE(r);
            b3=25.0+getE(r);
            h1=6.0;
            h2=8.0;
            h3=25.0;
            h4=33.0;
            b4=22.0;
            b5=18.0;
            h5=37.0;
        } else if (scale.name == "TTe") {
            h=32.0;
            b=26.0+getE(r);
        } else if (scale.name == "TTm") {
            h=34.0;
            b=28.0+getE(r);
        } else if (scale.name == "TT") {
            b1=36.0+getE(r);
            b2=24.0+getE(r);
            b3=32.0+getE(r);
            h1=8.0;
            h2=10.0;
            h3=33.0;
            h4=43.0;
            b4=28.0;
            b5=22.0;
            h5=48.0;
        } else if (scale.name == "H0") {
            b1=48.0+getE(r);
            b2=32.0+getE(r);
            b3=42.0+getE(r);
            h1=11.0;
            h2=14.0;
            h3=45.0;
            h4=59.0;
            b4=38.0;
            b5=30.0;
            h5=65.0;
        } else if (scale.name == "H0m") {
            b=38.0+getE(r);
            h=48.0;
        } else if (scale.name == "H0e") {
            b=36.0+getE(r);
            h=46.0;
        } else if (scale.name == "H0i") {
            b=36.0+getE(r);
            h=46.0;
        }
    }
}