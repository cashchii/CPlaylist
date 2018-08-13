package com.cashchii.production.cplaylist.playlist.view.activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.common.dialog.CustomDialog
import com.cashchii.production.cplaylist.playlist.view.fragment.PlaylistFragment

class PlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        Constant.replaceFragment(supportFragmentManager, PlaylistFragment.newInstance(), R.id.playlistContainer, Constant.FM_TAG.FM_PLAYLIST)
    }

    private fun exitDialog() {
        CustomDialog().showDialog(this, getString(R.string.exitString), getString(R.string.positiveBtn)) {
            when (it) {
                Constant.BTN.POSITIVE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAndRemoveTask()
                    } else {
                        finishAffinity()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        Log.d(Constant.TAG, "back")
        if (supportFragmentManager.backStackEntryCount != 0) {
            super.onBackPressed()
        } else {
            exitDialog()
        }
    }
}
