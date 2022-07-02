package com.dreamers.bottombar.library.utils

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

internal fun Context.getResourceId(id: Int): Int {
    val resolvedAttr = TypedValue()
    theme.resolveAttribute(id, resolvedAttr, true)
    return resolvedAttr.run { if (resourceId != 0) resourceId else data }
}

internal fun ValueAnimator.fixDurationScale() {
    try {
        ValueAnimator::class.java.getMethod(
            "setDurationScale",
            Float::class.javaPrimitiveType
        ).invoke(this, 1f)
    } catch (_: Throwable) {
    }
}

internal val Int.dpPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

internal val Int.spPx: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity).roundToInt()