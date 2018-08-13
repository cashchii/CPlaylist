package com.cashchii.production.cplaylist.common

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.cashchii.production.cplaylist.R

/**
 * Created by Panc. on 8/12/2018 AD.
 */
class CommonToolbar : Toolbar {

    private var titleTextView: TextView? = null
    private var screenWidth: Int = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        screenWidth = getScreenSize().x
        titleTextView = TextView(context)
        val face = ResourcesCompat.getFont(context, R.font.db_moment_x_bd)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleTextView?.setTextAppearance(R.style.ToolbarTitle)
            titleTextView?.typeface = face
        } else {
            titleTextView?.setTextColor(ContextCompat.getColor(context, R.color.white))
            titleTextView?.typeface = face
            titleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f)
        }

        addView(titleTextView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val location = IntArray(2)
        titleTextView?.getLocationOnScreen(location)
        titleTextView?.translationX = titleTextView?.translationX?.plus((-location[0] + screenWidth / 2 - titleTextView?.width!! / 2))!!
    }

    fun bindToolBarAppCompat(activity: AppCompatActivity, title: String, hasBack: Boolean) {
        setCommonTitle(title)
        activity.setSupportActionBar(this)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setDisplayShowHomeEnabled(false)
        if (Build.VERSION.SDK_INT in 19..20) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else if (Build.VERSION.SDK_INT >= 21) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        }

        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        setPadding(0, Constant.getStatusBarHeight(activity), 0, 0)

        if (hasBack) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setDisplayShowHomeEnabled(true)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { activity.onBackPressed() }
        }
    }

    fun setCommonTitle(title: String) {
        titleTextView?.text = title
    }

    fun getCurrentTitle(): String {
        return titleTextView?.text!!.toString()
    }

    private fun getScreenSize(): Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val screenSize = Point()
        display.getSize(screenSize)

        return screenSize
    }

}