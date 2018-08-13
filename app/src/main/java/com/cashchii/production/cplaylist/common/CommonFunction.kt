package com.cashchii.production.cplaylist.common

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by Panc. on 8/12/2018 AD.
 */
open class CommonFunction {
    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int, tag: String) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commit()
    }

    fun replaceFragmentWithBackstack(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int, tag: String) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun getStatusBarHeight(activity: Activity): Int {
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}