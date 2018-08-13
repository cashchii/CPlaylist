package com.cashchii.production.cplaylist.playlist.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.playlist.view.activity.PlaylistActivity
import com.cashchii.production.cplaylist.playlist.view.adapter.PlaylistAdapter
import com.cashchii.production.cplaylist.playlist.viewmodel.PlaylistViewModel
import kotlinx.android.synthetic.main.activity_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.*

/**
 * Created by Panc. on 8/11/2018 AD.
 */
class PlaylistFragment : Fragment() {
    private var rootView: View? = null
    private lateinit var playlistActivity: PlaylistActivity
    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var playlistViewModel: PlaylistViewModel
    private var isInit = false

    companion object {
        fun newInstance(): PlaylistFragment {
            return PlaylistFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = View.inflate(context, R.layout.fragment_playlist, null)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistActivity = activity as PlaylistActivity
        playlistActivity.commonToolbar.bindToolBarAppCompat(playlistActivity, "PLAYLISTS", false)
        playlistViewModel = ViewModelProviders.of(this).get(PlaylistViewModel::class.java)
        if (!isInit)
            initInstance()
    }

    private fun initInstance() {
        isInit = true
        playlistAdapter = PlaylistAdapter(context!!) {
            Log.d(Constant.TAG, it.title)
            Constant.replaceFragmentWithBackstack(playlistActivity.supportFragmentManager, PlayerFragment.newInstance(it), R.id.playlistContainer, Constant.FM_TAG.FM_PLAYER)
        }

        playlistViewModel.getPlaylist(context!!).observe(this, Observer {
            it?.let { playlistModel ->
                playlistAdapter.playlistModel = playlistModel
                expandedPlaylist.setAdapter(playlistAdapter)
            }
        })
    }
}