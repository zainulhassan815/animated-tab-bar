package com.dreamers.bottombar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import com.dreamers.bottombar.library.AnimatedBottomBar
import com.google.appinventor.components.annotations.DesignerProperty
import com.google.appinventor.components.annotations.SimpleEvent
import com.google.appinventor.components.annotations.SimpleFunction
import com.google.appinventor.components.annotations.SimpleProperty
import com.google.appinventor.components.common.PropertyTypeConstants
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent
import com.google.appinventor.components.runtime.AndroidViewComponent
import com.google.appinventor.components.runtime.ComponentContainer
import com.google.appinventor.components.runtime.EventDispatcher

@Suppress("FunctionName")
class BottomBar(container: ComponentContainer) : AndroidNonvisibleComponent(container.`$form`()) {

    private val context: Context = container.`$context`()
    private var bottomBar: AnimatedBottomBar? = null

    private var backgroundColor: Int = Color.parseColor("#FFFFFF") /* White  */
    private var tabColor: Int = Color.parseColor("#4C4C4C") /* Dark Grey  */
    private var selectedColor: Int = Color.parseColor("#02B3EF") /* Blue 500 */
    private var disabledColor: Int = Color.parseColor("#9E9E9E") /* Light Grey  */
    private var rippleColor: Int = Color.parseColor("#6ED1FE") /* Blue 300  */
    private var indicatorColor: Int = Color.parseColor("#02B3EF") /* Blue 700 */

    private var tabType: String = TAB_TYPE_ICON
    private var elevation: Float = 2f
    private var indicatorMargin: Int = 0
    private var indicatorHeight: Int = 2.px()
    private var indicatorPosition: String = INDICATOR_TOP
    private var rippleEnabled: Boolean = true

    private var iconSize: Int = 24.px()
    private var textSize: Int = 14.sp()

    private var typeface: Typeface = Typeface.DEFAULT
    private var animationDuration: Int = 400
    private var interpolator: String = FAST_OUT_SLOW_IN
    private var indicatorShape: String = SQUARE_INDICATOR

    private var badgeBackground: Int = Color.parseColor("#02B3EF") /* Blue 700 */
    private var badgeTextColor: Int = Color.parseColor("#FFFFFF") /* White */
    private var badgeTextSize: Int = 10.sp()
    private var badgeAnimationDuration: Int = 150

    private var tabAnimation: String = ANIMATION_SLIDE
    private var selectedTabAnimation: String = ANIMATION_SLIDE
    private var indicatorAnimation: String = ANIMATION_SLIDE
    private var badgeAnimation: String = ANIMATION_SCALE

    companion object {
        const val TAB_TYPE_TEXT = "Text"
        const val TAB_TYPE_ICON = "Icon"
        const val INDICATOR_TOP = "Top"
        const val INDICATOR_BOTTOM = "Bottom"
        const val FAST_OUT_SLOW_IN = "Fast Out Slow In"
        const val FAST_OUT_LINEAR_IN = "Fast Out Linear In"
        const val LINEAR_OUT_SLOW_IN = "Linear Out Slow In"
        const val SQUARE_INDICATOR = "Square Indicator"
        const val ROUND_INDICATOR = "Round Indicator"
        const val INVISIBLE_INDICATOR = "Invisible Indicator"
        const val ANIMATION_SLIDE = "Slide Animation"
        const val ANIMATION_FADE = "Fade Animation"
        const val ANIMATION_NONE = "No Animation"
        const val ANIMATION_SCALE = "Scale Animation"
    }

    // Create Bottom Bar
    @SimpleFunction(description = "Create bottom bar in a view")
    fun Create(view: AndroidViewComponent) {
        bottomBar = AnimatedBottomBar(context).also { bar ->
            bar.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            bar.setBackgroundColor(backgroundColor)
            bar.tabColor = tabColor
            bar.tabColorSelected = selectedColor
            bar.tabColorDisabled = disabledColor
            bar.rippleColor = rippleColor
            bar.indicatorColor = indicatorColor

            bar.selectedTabType = getTabTypeFromString(tabType)
            bar.elevation = elevation
            bar.indicatorMargin = indicatorMargin
            bar.indicatorHeight = indicatorHeight
            bar.indicatorLocation = getIndicatorPositionFromString(indicatorPosition)
            bar.rippleEnabled = rippleEnabled

            bar.iconSize = iconSize
            bar.textSize = textSize
            bar.typeface = typeface
            bar.animationDuration = animationDuration
            bar.animationInterpolator = getInterpolatorFromString(interpolator)
            bar.indicatorAppearance = getIndicatorShapeFromString(indicatorShape)

            bar.badgeBackgroundColor = badgeBackground
            bar.badgeTextColor = badgeTextColor
            bar.badgeTextSize = badgeTextSize

            bar.tabAnimation = getTabAnimationFromString(tabAnimation)
            bar.tabAnimationSelected = getTabAnimationFromString(selectedTabAnimation)
            bar.indicatorAnimation = getIndicatorAnimationFromString(indicatorAnimation)
            bar.badgeAnimation = getBadgeAnimationFromString(badgeAnimation)
            bar.badgeAnimationDuration = badgeAnimationDuration

            bar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
                override fun onTabSelected(
                    lastIndex: Int,
                    lastTab: AnimatedBottomBar.Tab?,
                    newIndex: Int,
                    newTab: AnimatedBottomBar.Tab
                ) {
                    OnTabSelected(newIndex + 1, newTab.id)
                }
            })
        }

        (view.view.parent as ViewGroup).addView(bottomBar)
    }

    // Add Tab
    @SimpleFunction(description = "Add a new tab")
    fun AddTab(icon: String, title: String, id: Int) {
        val tab = bottomBar?.createTab(
            icon = getDrawableFromPath(context, icon),
            title = title,
            id = id
        )
        tab?.let { bottomBar?.addTab(it) }
    }

    // Add Tab At Index
    @SimpleFunction(description = "Add a new tab at specific index")
    fun AddTabAt(index: Int, icon: String, title: String, id: Int) {
        val tab = bottomBar?.createTab(
            icon = getDrawableFromPath(context, icon),
            title = title,
            id = id
        )
        tab?.let { bottomBar?.addTabAt(index - 1, tab) }
    }

    // Remove Tab at index
    @SimpleFunction(description = "Remove tab at specific index")
    fun RemoveTab(index: Int) {
        bottomBar?.removeTabAt(index - 1)
    }

    // Remove Tab by id
    @SimpleFunction(description = "Remove tab with id")
    fun RemoveTabById(id: Int) {
        bottomBar?.removeTabById(id)
    }

    // Select Tab at index
    @SimpleFunction(description = "Select tab at index")
    fun SelectTab(index: Int, animate: Boolean) {
        bottomBar?.selectTabAt(index - 1, animate)
    }

    // Select Tab by id
    @SimpleFunction(description = "Select tab at index")
    fun SelectTabById(id: Int, animate: Boolean) {
        bottomBar?.selectTabById(id, animate)
    }

    // Set enabled
    @SimpleFunction(description = "Enabled or Disable a tab")
    fun SetEnabled(index: Int, enabled: Boolean) {
        bottomBar?.setTabEnabledAt(index - 1, enabled)
    }

    // Set enabled by id
    @SimpleFunction(description = "Enabled or Disable a tab")
    fun SetEnabledById(id: Int, enabled: Boolean) {
        bottomBar?.selectTabById(id, enabled)
    }

    // Set badge at index
    @SimpleFunction(description = "Set badge at index")
    fun SetBadge(index: Int, text: String) {
        val badge = AnimatedBottomBar.Badge(text = text)
        bottomBar?.setBadgeAtTabIndex(index - 1, badge)
    }

    // Set badge by id
    @SimpleFunction(description = "Set badge by id")
    fun SetBadgeById(id: Int, text: String) {
        val badge = AnimatedBottomBar.Badge(text = text)
        bottomBar?.setBadgeAtTabId(id, badge)
    }

    // Clear Badge
    @SimpleFunction(description = "Clear badge at index")
    fun ClearBadge(index: Int) {
        bottomBar?.clearBadgeAtTabIndex(index - 1)
    }

    // Clear Badge
    @SimpleFunction(description = "Clear badge by id")
    fun ClearBadgeById(id: Int) {
        bottomBar?.clearBadgeAtTabId(id)
    }

    // On Tab Selected
    @SimpleEvent(description = "Event raised when a tab is selected")
    fun OnTabSelected(index: Int, id: Int) {
        EventDispatcher.dispatchEvent(this, "OnTabSelected", index, id)
    }

    // Background Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFFFFFFFF"
    )
    @SimpleProperty(description = "Set background color for bottom bar")
    fun BackgroundColor(color: Int) {
        backgroundColor = color
        bottomBar?.setBackgroundColor(color)
    }

    // Tab Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF4C4C4C"
    )
    @SimpleProperty(description = "Set tab color")
    fun TabColor(color: Int) {
        tabColor = color
        bottomBar?.tabColor = color
    }

    // Selected Tab Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF02B3EF"
    )
    @SimpleProperty(description = "Set selected tab color")
    fun SelectedColor(color: Int) {
        selectedColor = color
        bottomBar?.tabColorSelected = color
    }

    // Disabled Tab Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF9E9E9E"
    )
    @SimpleProperty(description = "Set disabled tab color")
    fun DisabledColor(color: Int) {
        disabledColor = color
        bottomBar?.tabColorDisabled = color
    }

    // Ripple Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF6ED1FE"
    )
    @SimpleProperty(description = "Set ripple color for tab")
    fun RippleColor(color: Int) {
        rippleColor = color
        bottomBar?.rippleColor = color
    }

    // Ripple Enabled
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
        defaultValue = "True"
    )
    @SimpleProperty(description = "Enabled/Disable ripple")
    fun RippleEnabled(enabled: Boolean) {
        rippleEnabled = enabled
        bottomBar?.rippleEnabled = enabled
    }

    // Indicator Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF02B3EF"
    )
    @SimpleProperty(description = "Set indicator color")
    fun IndicatorColor(color: Int) {
        indicatorColor = color
        bottomBar?.indicatorColor = color
    }

    // Tab Type
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [TAB_TYPE_TEXT, TAB_TYPE_ICON],
        defaultValue = TAB_TYPE_ICON
    )
    @SimpleProperty(description = "Set tab type. Default value is $TAB_TYPE_ICON")
    fun TabType(type: String) {
        tabType = type
        bottomBar?.selectedTabType = getTabTypeFromString(type)
    }

    // Tab Types
    @SimpleProperty(description = "Tab Type : $TAB_TYPE_ICON")
    fun TabTypeIcon() = TAB_TYPE_ICON

    @SimpleProperty(description = "Tab Type : $TAB_TYPE_TEXT")
    fun TabTypeText() = TAB_TYPE_TEXT

    // Bottom bar Elevation
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT,
        defaultValue = "2"
    )
    @SimpleProperty(description = "Set bottom app elevation")
    fun Elevation(elevation: Float) {
        this.elevation = elevation
        bottomBar?.elevation = elevation
    }

    // Indicator Margin
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "0"
    )
    @SimpleProperty(description = "Set indicator margin")
    fun IndicatorMargin(margin: Int) {
        indicatorMargin = margin.px()
        bottomBar?.indicatorMargin = indicatorMargin
    }

    // Indicator Height
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "2"
    )
    @SimpleProperty(description = "Set indicator height")
    fun IndicatorHeight(height: Int) {
        indicatorHeight = height.px()
        bottomBar?.indicatorHeight = indicatorHeight
    }

    // Indicator Position
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [INDICATOR_BOTTOM, INDICATOR_TOP],
        defaultValue = INDICATOR_TOP,
    )
    @SimpleProperty(description = "Set indicator position. Default value is $INDICATOR_TOP")
    fun IndicatorPosition(position: String) {
        indicatorPosition = position
        bottomBar?.indicatorLocation = getIndicatorPositionFromString(position)
    }

    // Indicator Positions
    @SimpleProperty(description = "Indicator position : $INDICATOR_TOP")
    fun IndicatorTop() = INDICATOR_TOP

    @SimpleProperty(description = "Indicator position : $INDICATOR_BOTTOM")
    fun IndicatorBottom() = INDICATOR_BOTTOM

    // Indicator Shape
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [SQUARE_INDICATOR, ROUND_INDICATOR, INVISIBLE_INDICATOR],
        defaultValue = SQUARE_INDICATOR
    )
    @SimpleProperty(description = "")
    fun IndicatorShape(shape: String) {
        indicatorShape = shape
        bottomBar?.indicatorAppearance = getIndicatorShapeFromString(shape)
    }

    // Indicator Shape Options
    @SimpleProperty(description = "Indicator Appearance : $SQUARE_INDICATOR")
    fun SquareIndicator() = SQUARE_INDICATOR

    @SimpleProperty(description = "Indicator Appearance : $ROUND_INDICATOR")
    fun RoundIndicator() = ROUND_INDICATOR

    @SimpleProperty(description = "Indicator Appearance : $INVISIBLE_INDICATOR")
    fun InvisibleIndicator() = INVISIBLE_INDICATOR

    // Icon Size
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "24"
    )
    @SimpleProperty(description = "Set icon size")
    fun IconSize(size: Int) {
        iconSize = size.px()
        bottomBar?.iconSize = iconSize
    }

    // Text size
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "14"
    )
    @SimpleProperty(description = "Set text size")
    fun TextSize(size: Int) {
        textSize = size.sp()
        bottomBar?.textSize = textSize
    }

    // Typeface
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET,
    )
    @SimpleProperty(description = "Set custom font typeface")
    fun Typeface(asset: String) {
        typeface = getTypeface(context, asset)
        bottomBar?.typeface = typeface
    }

    // Animation Duration
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "400"
    )
    @SimpleProperty(description = "Set animation duration in milliseconds")
    fun AnimationDuration(duration: Int) {
        animationDuration = duration
        bottomBar?.animationDuration = duration
    }

    // Interpolator
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [FAST_OUT_SLOW_IN, FAST_OUT_LINEAR_IN, LINEAR_OUT_SLOW_IN],
        defaultValue = FAST_OUT_SLOW_IN
    )
    @SimpleProperty(description = "Set interpolator")
    fun Interpolator(interpolator: String) {
        this.interpolator = interpolator
        bottomBar?.animationInterpolator = getInterpolatorFromString(interpolator)
    }

    // Badge Background Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFF02B3EF"
    )
    @SimpleProperty(description = "Set badge background color")
    fun BadgeBackground(color: Int) {
        badgeBackground = color
        bottomBar?.badgeBackgroundColor = color
    }

    // Badge Text Color
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR,
        defaultValue = "&HFFFFFFFF"
    )
    @SimpleProperty(description = "Set badge text color")
    fun BadgeTextColor(color: Int) {
        badgeTextColor = color
        bottomBar?.badgeTextColor = color
    }

    // Badge Text Size
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "10"
    )
    @SimpleProperty(description = "Set badge text color")
    fun BadgeTextSize(size: Int) {
        badgeTextSize = size.sp()
        bottomBar?.badgeTextSize = badgeTextSize
    }

    // Badge Animation Duration
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
        defaultValue = "150"
    )
    @SimpleProperty(description = "Set badge animation duration")
    fun BadgeAnimationDuration(duration: Int) {
        badgeAnimationDuration = duration
        bottomBar?.badgeAnimationDuration = duration
    }

    // Default Interpolator
    @SimpleProperty(description = "Interpolator : $FAST_OUT_SLOW_IN")
    fun FastOutSlowIn() = FAST_OUT_SLOW_IN

    @SimpleProperty(description = "Interpolator : $FAST_OUT_LINEAR_IN")
    fun FastOutLinearIn() = FAST_OUT_LINEAR_IN

    @SimpleProperty(description = "Interpolator : $LINEAR_OUT_SLOW_IN")
    fun LinearOutSlowIn() = LINEAR_OUT_SLOW_IN

    // Tab Animation
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [ANIMATION_FADE, ANIMATION_SLIDE, ANIMATION_NONE],
        defaultValue = ANIMATION_SLIDE
    )
    @SimpleProperty(description = "Set tab animation")
    fun TabAnimation(animation: String) {
        tabAnimation = animation
        bottomBar?.tabAnimation = getTabAnimationFromString(animation)
    }

    // Selected Tab Animation
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [ANIMATION_FADE, ANIMATION_SLIDE, ANIMATION_NONE],
        defaultValue = ANIMATION_SLIDE
    )
    @SimpleProperty(description = "Set selected tab animation")
    fun SelectedTabAnimation(animation: String) {
        selectedTabAnimation = animation
        bottomBar?.tabAnimationSelected = getTabAnimationFromString(animation)
    }

    // Indicator Animation
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [ANIMATION_FADE, ANIMATION_SLIDE, ANIMATION_NONE],
        defaultValue = ANIMATION_SLIDE
    )
    @SimpleProperty(description = "Set selected tab animation")
    fun IndicatorAnimation(animation: String) {
        indicatorAnimation = animation
        bottomBar?.indicatorAnimation = getIndicatorAnimationFromString(animation)
    }

    // Badge Animation
    @DesignerProperty(
        editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
        editorArgs = [ANIMATION_FADE, ANIMATION_SCALE, ANIMATION_NONE],
        defaultValue = ANIMATION_SCALE
    )
    @SimpleProperty(description = "Set badge animation")
    fun BadgeAnimation(animation: String) {
        badgeAnimation = animation
        bottomBar?.badgeAnimation = getBadgeAnimationFromString(animation)
    }

    // Animation Options
    @SimpleProperty(description = ANIMATION_SLIDE)
    fun AnimationSlide() = ANIMATION_SLIDE

    @SimpleProperty(description = ANIMATION_FADE)
    fun AnimationFade() = ANIMATION_FADE

    @SimpleProperty(description = ANIMATION_NONE)
    fun AnimationNone() = ANIMATION_NONE

    @SimpleProperty(description = "$ANIMATION_SCALE (Note: Scale animation is available only for badge)")
    fun AnimationScale() = ANIMATION_SCALE
}
