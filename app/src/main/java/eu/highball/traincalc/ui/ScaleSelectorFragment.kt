package eu.highball.traincalc.ui

import ScaleDescriptor
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import eu.highball.traincalc.R
import java.util.*
import kotlin.collections.ArrayList


class ScaleSelectorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scale_selector, container, false)
        val layout = view.findViewById(R.id.fr_scale_selector) as LinearLayout

        var row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = Gravity.CENTER_HORIZONTAL
        layout.addView(row)
        var lastDescr : ScaleDescriptor? = null
        for(scDescr in ScaleDescriptor.scales) {
            if ((lastDescr != null) && (lastDescr.scale != scDescr.scale)) {
                row = LinearLayout(requireContext())
                row.orientation = LinearLayout.HORIZONTAL
                row.gravity = Gravity.CENTER_HORIZONTAL
                layout.addView(row)
            }
            val btn = ScaleSelectorButton(requireContext())
            btn.tag = scDescr.id
            btn.setParams(scDescr)
            btn.setOnClickListener {v ->
                val scale=ScaleDescriptor.scaleById(v.tag as UUID)
                if (scale != null) {
                    ScaleDescriptor.selectedScale = scale
                    var navController = Navigation.findNavController(v)
                    navController.popBackStack()
                }
            }
            row.addView(btn)
            lastDescr = scDescr
        }




        return view
    }
}