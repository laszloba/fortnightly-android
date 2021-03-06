package hu.laszloba.fortnightly.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ToolbarBackgroundImage : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    private val backgroundShape =
        ToolbarShapes
            .createBackgroundShape(context)
            .apply {
                interpolation = 0f
            }

    init {
        background = backgroundShape
    }

    fun setShapeInterpolation(interpolation: Float) {
        backgroundShape.interpolation = interpolation
    }
}
