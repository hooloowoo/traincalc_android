import android.graphics.Color
import java.util.*
import kotlin.math.floor

data class ScaleDescriptor (
    var id : UUID, val name : String,
    var scale : Double,
    var standard : String,
    var trackDistStation: Int,
    var trackDistLine: Int,
    var cant : Double,
    var minRadius : Double,
    var rStation : Double,
    var rBranch : Double,
    var rMain : Double,
    var gaugeFrom : Int,
    var gaugeTo : Int,
    var gauge : Double,
    var color : String,
    var guidingRailDist : Double)
{
    fun isNarrow() : Boolean {
        return (gaugeTo < 1450)
    }

    fun scaleString() : String {
        if (Math.floor(scale) == scale) {
            return "1:" + scale.toInt().toString()
        } else {
            return "1:$scale"
        }
    }

    fun protoGaugeString() : String {
        return "" + gaugeFrom.toString() + " - " + gaugeTo.toString() + "mm"
    }

    fun gaugeString() : String {
        if (floor(gauge) == gauge) {
            return gauge.toInt().toString()
        } else {
            return gauge.toString()
        }
    }

    companion object {

        var scales = arrayOf(
            ScaleDescriptor(UUID.randomUUID(),"H0",87.0,"NEM",52,46,1.0,495.0,577.5,660.0,742.5,1250,1699,16.5,"#ff0000",1.5),
            ScaleDescriptor(UUID.randomUUID(), "H0m",87.0,"NEM",52,46,0.8,180.0,240.0, 300.0,360.0, 850,  1249,12.0,"#e54040", 1.3),
            ScaleDescriptor(UUID.randomUUID(),"H0e",87.0,"NEM",52,46,0.6,135.0,180.0,225.0,270.0,650,849,9.0,"#ff8000",1.2),
            ScaleDescriptor(UUID.randomUUID(),"H0i",87.0,"NEM", 52, 46,0.4,97.5,130.0,162.5,195.0, 400, 649,6.5,"#ccb380",0.9),

            ScaleDescriptor(UUID.randomUUID(),"TT",120.0,"NEM",38,34,0.8,360.0, 420.0,480.0,540.0, 1250, 1699, 12.0,"#00ff00",1.3),
            ScaleDescriptor(UUID.randomUUID(),"TTm",120.0,"NEM", 38, 34, 0.6, 135.0, 180.0, 225.0,270.0,850, 1249,9.0,"#4db34d", 1.2),
            ScaleDescriptor(UUID.randomUUID(),"TTe",120.0,"NEM",38,34,0.4,97.5,130.0, 162.5, 195.0, 650,849, 6.5,"#00ffbf", 0.9),

            ScaleDescriptor(UUID.randomUUID(),"N",160.0,"NEM", 28,25, 0.6, 270.0, 315.0, 360.0,405.0,1250,  1699, 9.0, "#0000ff", 1.2),
            ScaleDescriptor(UUID.randomUUID(),"Nm",160.0,"NEM",28, 25,0.4, 97.5,130.0, 162.5, 195.0,850, 1249,6.5,"#0080ff",0.9),

            ScaleDescriptor(UUID.randomUUID(),"Z",220.0,"NEM",21,19,0.4,195.0, 227.5,260.0,292.5,1250, 1699,6.5,"#ff00ff",0.9)

        )

        var selectedScale : ScaleDescriptor = scales[0]


        fun scaleById(id : UUID) : ScaleDescriptor? {
            for(desc in scales) {
                if (desc.id == id) {
                    return desc
                }
            }
            return null
        }


    }
}