package com.cashchii.production.cplaylist.playlist.view.activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.playlist.view.fragment.PlaylistFragment

class PlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)
        Constant.replaceFragment(supportFragmentManager, PlaylistFragment.newInstance(), R.id.playlistContainer, Constant.FM_TAG.FM_PLAYLIST)
    }

    private fun exitDialog() {
        val builder1 = AlertDialog.Builder(this)
        builder1.setMessage(getString(R.string.exitString))
        builder1.setTitle(R.string.app_name)
        builder1.setCancelable(false)
        builder1.setPositiveButton(getString(R.string.positiveBtn)) { dialog, id ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask()
            } else {
                finishAffinity()
            }
        }

        builder1.setNegativeButton(getString(R.string.negativeBtn)) { dialog, id ->
            dialog.cancel()

        }

        val alert11 = builder1.create()
        alert11.show()
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
