package com.cashchii.production.cplaylist.playlist.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.model.ItemModel
import com.cashchii.production.cplaylist.playlist.view.activity.PlaylistActivity
import com.cashchii.production.cplaylist.playlist.viewmodel.PlaylistViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.activity_playlist.*
import kotlinx.android.synthetic.main.fragment_player.*


/**
 * Created by Panc. on 8/12/2018 AD.
 */
class PlayerFragment : Fragment() {
    private lateinit var playlistActivity: PlaylistActivity
    private var itemModel = ItemModel()
    private var rootView: View? = null
    private var youTubePlayerSupportFragment: YouTubePlayerSupportFragment? = null
    private var mPlayer: YouTubePlayer? = null
    private lateinit var playViewModel: PlaylistViewModel

    companion object {
        fun newInstance(itemModel: ItemModel): PlayerFragment {
            val args = Bundle()
            args.putParcelable(Constant.ITEM_MODEL, itemModel)
            val fragment = PlayerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = View.inflate(context, R.layout.fragment_player, null)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistActivity = activity as PlaylistActivity
        playlistActivity.commonToolbar.bindToolBarAppCompat(playlistActivity, "PLAYER", true)
        playViewModel = ViewModelProviders.of(this).get(PlaylistViewModel::class.java)
        if (arguments != null) {
            itemModel = arguments?.getParcelable(Constant.ITEM_MODEL)!!
        }
        initInstance()
    }

    private fun initInstance() {
        tvTitleName.text = itemModel.title
        tvLink.text = itemModel.link
        if (youTubePlayerSupportFragment == null)
            youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtubeContainer, youTubePlayerSupportFragment).commit()

        playViewModel.getVideoItem(context!!, itemModel.link!!)
                .observe(this, Observer {
                    it?.let { itemModel = it }
                    initYoutube()
                })

    }

    private fun initYoutube() {
        youTubePlayerSupportFragment?.initialize(Constant.YOUTUBE_API, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    mPlayer = player
                    mPlayer?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    Log.d(Constant.TAG, "${itemModel.link}")
                    val videoId = itemModel.link?.split("?v=")?.get(1)
                    if (itemModel.currentMins == 0) {
                        itemModel.isPlaying = true
                        itemModel.isEnding = false
                        mPlayer?.loadVideo(videoId)
                    } else {
                        resumeDialog(videoId)
                    }
                    mPlayer?.play()
                    playerState()
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, error: YouTubeInitializationResult?) {
                val errorMessage = error.toString()
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                Log.d(Constant.TAG, "errorMessage: $errorMessage")
            }
        })
    }

    private fun resumeDialog(videoId: String?) {
        val builder1 = AlertDialog.Builder(context!!)
        builder1.setMessage(getString(R.string.resumePlaying))
        builder1.setTitle(R.string.app_name)
        builder1.setCancelable(false)
        builder1.setPositiveButton(getString(R.string.resumeBtn)) { dialog, id ->
            mPlayer?.loadVideo(videoId, itemModel.currentMins)
            dialog.dismiss()
        }

        builder1.setNegativeButton(getString(R.string.negativeBtn)) { dialog, id ->
            mPlayer?.loadVideo(videoId)
            dialog.dismiss()
        }

        val alert11 = builder1.create()
        alert11.show()
    }

    private fun playerState() {
        mPlayer?.setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
            override fun onAdStarted() {
                Log.d(Constant.TAG, "onAdStarted")
            }

            override fun onLoading() {
                Log.d(Constant.TAG, "onLoading")
            }

            override fun onVideoStarted() {
                Log.d(Constant.TAG, "onVideoStarted")
            }

            override fun onLoaded(videoId: String?) {
                Log.d(Constant.TAG, "onLoaded $videoId")
            }

            override fun onVideoEnded() {
                itemModel.isPlaying = false
                itemModel.isEnding = true
                itemModel.currentMins = 0
                Log.d(Constant.TAG, "onVideoEnded")
            }

            override fun onError(error: YouTubePlayer.ErrorReason?) {
                Log.d(Constant.TAG, "onError $error")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constant.TAG, "pause ${mPlayer?.currentTimeMillis}")
        if (itemModel.isPlaying)
            itemModel.currentMins = mPlayer?.currentTimeMillis!!
        playViewModel.updateVideoItem(context!!, itemModel)
    }

    override fun onStop() {
        super.onStop()
        Log.d(Constant.TAG, "stop")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPlayer?.setFullscreen(true)
        } else {
            mPlayer?.setFullscreen(false)
        }
    }
}