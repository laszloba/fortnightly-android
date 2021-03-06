package hu.laszloba.fortnightly.ui.toolbar

import android.content.Context
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import hu.laszloba.fortnightly.R
import hu.laszloba.fortnightly.extension.getColorFromAttr

object ToolbarShapes {

    fun createBackgroundShape(context: Context) =
        MaterialShapeDrawable(
            ShapeAppearanceModel.builder()
                .setBottomRightCorner(
                    CornerFamily.CUT,
                    context.resources.getDimensionPixelSize(R.dimen.common_toolbar_height) / 3f
                )
                .build()
        ).apply {
            setTint(context.getColorFromAttr(R.attr.colorPrimary))
        }
}
