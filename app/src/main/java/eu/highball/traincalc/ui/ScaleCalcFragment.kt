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
import android.widget.Spinner
import androidx.fragment.app.Fragment
import eu.highball.traincalc.R
import java.text.DecimalFormat


class ScaleCalcFragment : Fragment() {

    var courses = arrayOf(
        "mm", "cm","m","km","inch","feet","yard","mile"
    )

    var weights : Array<Double> = arrayOf(
        1.0, 10.0,1000.0,1000000.0,25.4,304.8,914.4,1609340.0
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etnProtoSize: EditText? = view?.findViewById(R.id.etnProtoSize)
        val etnScale: EditText? = view?.findViewById(R.id.etnScale)
        val etnModelSize: EditText? = view?.findViewById(R.id.etnModelSize)
        val spnProtoUnit: Spinner? = view?.findViewById(R.id.spnProtoUnit)
        val spnModelUnit: Spinner? = view?.findViewById(R.id.spnModelUnit)
        spnProtoUnit?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                calcFromProto()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        spnModelUnit?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                calcFromModel()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        var aa = ArrayAdapter(requireView().context, android.R.layout.simple_spinner_item, courses)
        aa.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spnProtoUnit!!.adapter = aa
        spnModelUnit!!.adapter = aa


        val btnScale: ScaleSelectorButton? = view?.findViewById(R.id.btnScale)
        btnScale!!.setParams(null)


        etnProtoSize!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (etnProtoSize!!.hasFocus()) calcFromProto()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        etnScale!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (etnScale!!.hasFocus()) {
                    calcSize()
                    calcFromProto()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        etnModelSize!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (etnModelSize!!.hasFocus()) calcFromModel()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scale_calc, container, false)
    }

    private fun calcSize() {
        val entScale : EditText? = requireView().findViewById(R.id.etnScale)
        val scale = entScale?.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val scaleDesc = ScaleDescriptor.scales.filter { sd -> sd.scale == scale }.firstOrNull()
        val btnScale: ScaleSelectorButton? = view?.findViewById(R.id.btnScale)
        btnScale!!.setParams(scaleDesc)
    }

    private fun calcFromProto() {
        val spnProtoUnit: Spinner? = requireView().findViewById(R.id.spnProtoUnit)
        val spnModelUnit: Spinner? = requireView().findViewById(R.id.spnModelUnit)
        val entScale : EditText? = requireView().findViewById(R.id.etnScale)
        val etnProtoSize : EditText? = requireView().findViewById(R.id.etnProtoSize)
        val etnModelSize : EditText? = requireView().findViewById(R.id.etnModelSize)
        val scale = entScale?.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val protoSize = etnProtoSize?.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val protoUnit = spnProtoUnit?.selectedItem.toString()
        val modelUnit = spnModelUnit?.selectedItem.toString()

        if ((protoUnit === null) || (scale === null) || (protoSize === null)) {
            etnModelSize?.setText("");
        } else {
            val protoMm = protoSize * weights[courses.indexOf(protoUnit)]
            val modelMm = (protoMm / scale).toString()
            val modelSize = (modelMm.toDouble() / weights[courses.indexOf(modelUnit)])

            val df = DecimalFormat("#.##")
            etnModelSize?.setText(df.format(modelSize))

        }
    }

    private fun calcFromModel() {
        val spnProtoUnit: Spinner? = requireView().findViewById(R.id.spnProtoUnit)
        val spnModelUnit: Spinner? = requireView().findViewById(R.id.spnModelUnit)
        val entScale : EditText? = requireView().findViewById(R.id.etnScale)
        val etnProtoSize : EditText? = requireView().findViewById(R.id.etnProtoSize)
        val etnModelSize : EditText? = requireView().findViewById(R.id.etnModelSize)
        val scale = entScale?.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val modelSize = etnModelSize?.text.toString().replace(",".toRegex(), ".").toDoubleOrNull()
        val protoUnit = spnProtoUnit?.selectedItem.toString()
        val modelUnit = spnModelUnit?.selectedItem.toString()
        if ((modelUnit === null) || (scale === null) || (modelSize === null)) {
            etnProtoSize?.setText("");
        } else {
            val modelMm = modelSize * weights[courses.indexOf(modelUnit)]
            val protoMm = (modelMm * scale).toString()
            val protoSize = (protoMm.toDouble() / weights[courses.indexOf(protoUnit)])
            val df = DecimalFormat("#.##")
            etnProtoSize?.setText(df.format(protoSize))
        }
    }


}