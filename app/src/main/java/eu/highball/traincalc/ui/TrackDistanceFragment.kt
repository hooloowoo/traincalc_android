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
import eu.highball.traincalc.databinding.FragmentTrackDistanceBinding
import java.text.DecimalFormat


class TrackDistanceFragment : Fragment() {
    private var _binding: FragmentTrackDistanceBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackDistanceBinding.inflate(inflater, container, false)
        val df = DecimalFormat("#.##")
        binding.txtNemGauge.text = df.format(ScaleDescriptor.selectedScale.gauge)
        binding.btnScale.setParams(ScaleDescriptor.selectedScale)
        binding.btnScale.setOnClickListener { _ ->
            Navigation.findNavController(requireView()).navigate(R.id.action_trackDistanceFragment_to_scaleSelectorFragment)
        }


        binding.txtTrackDistanceAtStations.text = df.format(ScaleDescriptor.selectedScale.trackDistStation)
        binding.txtTrackDistanceOnLines.text = df.format(ScaleDescriptor.selectedScale.trackDistLine)

        binding.etnTrackDistanceRadius.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                try {
                    val d = s.toString().replace(",".toRegex(), ".").toDouble()
                    val res: Int = calcCurve(d)
                    if (res == -1) {
                        binding.txtTrackDistanceInCurves.text = ""
                    } else {
                        binding.txtTrackDistanceInCurves.text = res.toString()
                    }
                } catch (e: Exception) {
                    binding.txtTrackDistanceInCurves.text = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        return binding.root
    }

    fun calcCurve(r : Double) : Int {
        var cdist : Int = -1;
        val scale = ScaleDescriptor.selectedScale
        if (scale.name.startsWith("H0")) {
            if (r < 400) {cdist = -1}
            else if ((r >= 400) && (r < 450)) {cdist=64}
            else if ((r >= 450) && (r < 500)) {cdist=61}
            else if ((r >= 500) && (r < 550)) {cdist=59}
            else if ((r >= 550) && (r < 600)) {cdist=57}
            else if ((r >= 600) && (r < 700)) {cdist=55}
            else if ((r >= 700) && (r < 800)) {cdist=52}
            else if ((r >= 800) && (r < 900)) {cdist=50}
            else if ((r >= 900) && (r < 1000)) {cdist=48}
            else if ((r >= 1000) && (r < 1200)) {cdist=47}
            else {cdist=46}
        } else if (scale.name.startsWith("TT")) {
            if (r < 300) {cdist = -1}
            else if ((r >= 300) && (r < 325)) {cdist=46}
            else if ((r >= 325) && (r < 350)) {cdist=45}
            else if ((r >= 350) && (r < 400)) {cdist=43}
            else if ((r >= 400) && (r < 450)) {cdist=41}
            else if ((r >= 450) && (r < 500)) {cdist=40}
            else if ((r >= 500) && (r < 550)) {cdist=38}
            else if ((r >= 550) && (r < 600)) {cdist=37}
            else if ((r >= 600) && (r < 700)) {cdist=36}
            else if ((r >= 700) && (r < 800)) {cdist=35}
            else {cdist=34}

        } else if (scale.name.startsWith("N")) {
            if (r < 225) {cdist = -1}
            else if ((r >= 225) && (r < 250)) {cdist=35}
            else if ((r >= 250) && (r < 275)) {cdist=33}
            else if ((r >= 275) && (r < 300)) {cdist=32}
            else if ((r >= 300) && (r < 325)) {cdist=31}
            else if ((r >= 325) && (r < 350)) {cdist=30}
            else if ((r >= 350) && (r < 400)) {cdist=29}
            else if ((r >= 400) && (r < 450)) {cdist=28}
            else if ((r >= 450) && (r < 500)) {cdist=27}
            else if ((r >= 500) && (r < 700)) {cdist=26}
            else {cdist=25}

        } else if (scale.name.startsWith("Z")) {
            if (r < 175) {cdist = -1}
            else if ((r >= 175) && (r < 200)) {cdist=25}
            else if ((r >= 200) && (r < 225)) {cdist=24}
            else if ((r >= 225) && (r < 250)) {cdist=23}
            else if ((r >= 250) && (r < 275)) {cdist=22}
            else if ((r >= 275) && (r < 325)) {cdist=21}
            else if ((r >= 325) && (r < 400)) {cdist=20}
            else {cdist=19}

        }
        return cdist
    }


}