package eu.highball.traincalc.ui

import ScaleDescriptor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import eu.highball.traincalc.databinding.FragmentScaleCalcBinding
import java.text.DecimalFormat


class ScaleCalcFragment : Fragment() {

    private var _binding: FragmentScaleCalcBinding? = null

    private val binding get() = _binding!!
//    Imperial units will be shown when NMRA added
//    private var courses = arrayOf("mm", "cm","m","km","inch","feet","yard","mile")
//    private var weights : Array<Double> = arrayOf(1.0, 10.0,1000.0,1000000.0,25.4,304.8,914.4,1609340.0)

    private var courses = arrayOf("mm", "cm","m","km")
    private var weights : Array<Double> = arrayOf(1.0, 10.0,1000.0,1000000.0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var aa = ArrayAdapter(requireView().context, android.R.layout.simple_spinner_item, courses)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnProtoUnit.adapter = aa
        binding.spnModelUnit.adapter = aa
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val df = DecimalFormat("#.##")
        val txt = df.format(ScaleDescriptor.selectedScale.scale)
        binding.etnScale.setText(txt)
        calcFromProto()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding === null) _binding = FragmentScaleCalcBinding.inflate(inflater, container, false)
        binding.btnScale.setParams(ScaleDescriptor.selectedScale)

        binding.spnProtoUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (view !== null) {
                    calcFromProto()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.spnModelUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (view !== null) {
                    calcFromModel()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        binding.etnProtoSize.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnProtoSize.hasFocus()) calcFromProto()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.etnScale.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnScale.hasFocus()) {
                    calcSize()
                    calcFromProto()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })



        binding.etnModelSize.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (binding.etnModelSize.hasFocus()) calcFromModel()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_gauge_to_scaleSelectorFragment)
        }


        return binding.root
    }

    private fun calcSize() {
        val scale = binding.etnScale.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val scaleDesc = ScaleDescriptor.scales.filter { sd -> sd.scale == scale }.firstOrNull()
        binding.btnScale.setParams(scaleDesc)
        if (scaleDesc !== null) {
            ScaleDescriptor.selectedScale = scaleDesc
        }
    }

    private fun calcFromProto() {
        val scale = binding.etnScale?.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val protoSize = binding.etnProtoSize.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val protoUnit = binding.spnProtoUnit.selectedItem.toString()
        val modelUnit = binding.spnModelUnit.selectedItem.toString()

        if ((protoUnit === null) || (scale === null) || (protoSize === null)) {
            binding.etnModelSize.setText("");
        } else {
            val protoMm = protoSize * weights[courses.indexOf(protoUnit)]
            val modelMm = (protoMm / scale).toString()
            val modelSize = (modelMm.toDouble() / weights[courses.indexOf(modelUnit)])

            val df = DecimalFormat("#.##")
            binding.etnModelSize.setText(df.format(modelSize))
        }
    }

    private fun calcFromModel() {
        val scale = binding.etnScale.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val modelSize = binding.etnModelSize.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val protoUnit = binding.spnProtoUnit.selectedItem.toString()
        val modelUnit = binding.spnModelUnit.selectedItem.toString()
        if ((modelUnit === null) || (scale === null) || (modelSize === null)) {
            binding.etnProtoSize.setText("");
        } else {
            val modelMm = modelSize * weights[courses.indexOf(modelUnit)]
            val protoMm = (modelMm * scale).toString()
            val protoSize = (protoMm.toDouble() / weights[courses.indexOf(protoUnit)])
            val df = DecimalFormat("#.##")
            binding.etnProtoSize.setText(df.format(protoSize))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}