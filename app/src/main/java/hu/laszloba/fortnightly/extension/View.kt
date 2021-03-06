package hu.laszloba.fortnightly.extension

import android.util.TypedValue
import android.view.View

fun View.addRippleEffectOnClick() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}
