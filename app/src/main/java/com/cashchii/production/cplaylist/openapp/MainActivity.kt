package com.cashchii.production.cplaylist.openapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.openapp.viewmodel.OpenAppViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OpenAppViewModel().loadingPlaylist(this)
    }
}
