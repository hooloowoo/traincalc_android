package eu.highball.traincalc.ui

import ScaleDescriptor
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import eu.highball.traincalc.R


class ScaleSelectorButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.button_scale_selector, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        try {
            val txtName = ta.getString(R.styleable.CustomView_name)
            val txtScale = ta.getString(R.styleable.CustomView_scale)
            val txtNorm = ta.getString(R.styleable.CustomView_norm)
/*            val drawableId = ta.getResourceId(R.styleable.CustomView_image, 0)
            if (drawableId != 0) {
                val drawable = AppCompatResources.getDrawable(context, drawableId)
                val ivImage : ImageView = findViewById(R.id.image_thumb)
                ivImage.setImageDrawable(drawable)
            }*/
            val tvName : TextView = findViewById(R.id.txtScaleName)
            tvName.text = txtName

            val tvScale : TextView = findViewById(R.id.txtScaleScale)
            tvScale.text = txtScale

            val tvNorm : TextView = findViewById(R.id.txtScaleNorm)
            tvNorm.text = txtNorm


        } finally {
            ta.recycle()
        }
    }

    fun setParams(descr : ScaleDescriptor ) {
        val color = Color.parseColor(descr.color);
        val tvName : TextView = findViewById(R.id.txtScaleName)
        tvName.text = descr.name
        tvName.setTextColor(color)
        val tvScale : TextView = findViewById(R.id.txtScaleScale)
        tvScale.text = descr.scaleString()
        tvScale.setTextColor(color)
        val tvNorm : TextView = findViewById(R.id.txtScaleNorm)
        tvNorm.text = descr.standard
        tvNorm.setTextColor(color)



    }


    fun setName(name : String ) {
        val tvName : TextView = findViewById(R.id.txtScaleName)
        tvName.text = name

    }
}
