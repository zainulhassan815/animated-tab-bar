package com.dreamers.bottombar

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.animation.Interpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.dreamers.bottombar.library.AnimatedBottomBar
import com.google.appinventor.components.runtime.Form
import com.google.appinventor.components.runtime.ReplForm
import java.io.FileInputStream
import java.io.InputStream
import android.os.Build as OsBuild

const val LOG_TAG = "BOTTOM_BAR"

/* Get tab type from string */
internal fun getTabTypeFromString(type: String) = when (type) {
    BottomBar.TAB_TYPE_ICON -> AnimatedBottomBar.TabType.ICON
    BottomBar.TAB_TYPE_TEXT -> AnimatedBottomBar.TabType.TEXT
    else -> AnimatedBottomBar.TabType.ICON
}

internal fun getIndicatorPositionFromString(position: String) = when (position) {
    BottomBar.INDICATOR_TOP -> AnimatedBottomBar.IndicatorLocation.TOP
    BottomBar.INDICATOR_BOTTOM -> AnimatedBottomBar.IndicatorLocation.BOTTOM
    else -> AnimatedBottomBar.IndicatorLocation.TOP
}

internal fun getDrawable(form: Form, fileName: String): Drawable? {
    return try {
        val inputStream: InputStream? = getAsset(form, fileName)
        val drawable: Drawable = Drawable.createFromStream(inputStream, null)
        inputStream?.close()
        drawable
    } catch (e: Exception) {
        Log.v(LOG_TAG, "getDrawable : Error = $e")
        null
    }
}

internal fun getAsset(form: Form, file: String): InputStream? {
    val context = form.`$context`()
    val isDebugMode = form is ReplForm
    return try {
        if (isDebugMode) {
            val path: String = getAssetPath(context, file)
            Log.v(LOG_TAG, "getAsset | Filepath = $path")
            FileInputStream(path)
        } else {
            context.assets.open(file)
        }
    } catch (e: Exception) {
        Log.e(LOG_TAG, "getAsset | Debug Mode : $isDebugMode | Error : $e")
        null
    }
}

internal fun getTypeface(form: Form, asset: String): Typeface {
    val context = form.`$context`()
    val isDebugMode = form is ReplForm
    return try {
        if (isDebugMode) {
            val path = getAssetPath(context, asset)
            Typeface.createFromFile(path)
        } else {
            val path = form.getAssetPath(asset)
            Typeface.createFromFile(path)
        }
    } catch (e: Exception) {
        Log.e(LOG_TAG, "getTypeface | Failed to get typeface from path : $asset with error : $e")
        Typeface.DEFAULT
    }
}

private fun getAssetPath(context: Context, file: String) = when {
    context.javaClass.name.contains("makeroid") -> {
        if (OsBuild.VERSION.SDK_INT >= OsBuild.VERSION_CODES.Q) {
            context.getExternalFilesDir(null).toString() + "/assets/$file"
        } else {
            "/storage/emulated/0/Kodular/assets/$file"
        }
    }
    else -> context.getExternalFilesDir(null).toString() + "/assets/$file"
}

/** Convert dp to px */
internal fun Int.px(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        android.content.res.Resources.getSystem().displayMetrics
    ).toInt()
}

/** Convert sp to px */
internal fun Int.sp(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        android.content.res.Resources.getSystem().displayMetrics
    ).toInt()
}

internal fun getInterpolatorFromString(interpolator: String): Interpolator = when (interpolator) {
    BottomBar.FAST_OUT_SLOW_IN -> FastOutSlowInInterpolator()
    BottomBar.FAST_OUT_LINEAR_IN -> FastOutLinearInInterpolator()
    BottomBar.LINEAR_OUT_SLOW_IN -> LinearOutSlowInInterpolator()
    else -> FastOutSlowInInterpolator()
}

internal fun getIndicatorShapeFromString(shape: String) = when (shape) {
    BottomBar.SQUARE_INDICATOR -> AnimatedBottomBar.IndicatorAppearance.SQUARE
    BottomBar.ROUND_INDICATOR -> AnimatedBottomBar.IndicatorAppearance.ROUND
    BottomBar.INVISIBLE_INDICATOR -> AnimatedBottomBar.IndicatorAppearance.INVISIBLE
    else -> AnimatedBottomBar.IndicatorAppearance.SQUARE
}

internal fun getTabAnimationFromString(animation: String) = when (animation) {
    BottomBar.ANIMATION_SLIDE -> AnimatedBottomBar.TabAnimation.SLIDE
    BottomBar.ANIMATION_FADE -> AnimatedBottomBar.TabAnimation.FADE
    BottomBar.ANIMATION_NONE -> AnimatedBottomBar.TabAnimation.NONE
    else -> AnimatedBottomBar.TabAnimation.SLIDE
}

internal fun getIndicatorAnimationFromString(animation: String) = when (animation) {
    BottomBar.ANIMATION_SLIDE -> AnimatedBottomBar.IndicatorAnimation.SLIDE
    BottomBar.ANIMATION_FADE -> AnimatedBottomBar.IndicatorAnimation.FADE
    BottomBar.ANIMATION_NONE -> AnimatedBottomBar.IndicatorAnimation.NONE
    else -> AnimatedBottomBar.IndicatorAnimation.SLIDE
}

internal fun getBadgeAnimationFromString(animation: String) = when (animation) {
    BottomBar.ANIMATION_NONE -> AnimatedBottomBar.BadgeAnimation.NONE
    BottomBar.ANIMATION_FADE -> AnimatedBottomBar.BadgeAnimation.FADE
    BottomBar.ANIMATION_SCALE -> AnimatedBottomBar.BadgeAnimation.SCALE
    else -> AnimatedBottomBar.BadgeAnimation.SCALE
}