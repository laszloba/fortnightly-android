package hu.laszloba.fortnightly.ui.toolbar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.databinding.ToolbarBackBinding
import hu.laszloba.fortnightly.extension.getColorFromAttr

class ToolbarWithBackButton : FrameLayout {

    var onActionClickedListener: OnActionClickedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        ToolbarBackBinding.inflate(LayoutInflater.from(context), this)

        background = createBackgroundDrawable()

        elevation = resources.getDimension(R.dimen.common_toolbar_background_elevation)

        setOnClickListener {
            onActionClickedListener?.onBackButtonClicked()
        }
    }

    private fun createBackgroundDrawable(): Drawable {
        val backgroundShape = ToolbarShapes.createBackgroundShape(context)

        val rippleEffectColor = ColorStateList(
            arrayOf(intArrayOf()),
            intArrayOf(context.getColorFromAttr(R.attr.colorAccent))
        )

        return RippleDrawable(rippleEffectColor, backgroundShape, null)
    }

    interface OnActionClickedListener {
        fun onBackButtonClicked()
    }
}
