package com.cashchii.production.cplaylist.playlist.view.adapter

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cashchii.production.cplaylist.R
import com.cashchii.production.cplaylist.model.ItemModel
import com.cashchii.production.cplaylist.model.PlaylistModel
import kotlinx.android.synthetic.main.item_play_list.view.*
import kotlinx.android.synthetic.main.item_title_playlist.view.*

/**
 * Created by Panc. on 8/11/2018 AD.
 */
class PlaylistAdapter(val context: Context, val callback: ((ItemModel) -> Unit)?) : ExpandableListAdapter {
    var playlistModel: PlaylistModel = PlaylistModel()

    override fun getChildrenCount(groupPosition: Int): Int {
        return playlistModel.playlists?.get(groupPosition)?.listItem?.size!!
    }

    override fun getGroup(groupPosition: Int): Any {
        return playlistModel.playlists!![groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): ItemModel {
        return playlistModel.playlists?.get(groupPosition)?.listItem?.get(childPosition)!!
    }


    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    private var cView: View? = null
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        cView = convertView
        if (cView == null) {
            cView = LayoutInflater.from(context).inflate(R.layout.item_play_list, null)
        }
        Glide.with(cView!!).load(playlistModel.playlists?.get(groupPosition)?.listItem!![childPosition].thumbImg)
                .apply(RequestOptions().centerCrop()).into(cView!!.imgPreview)
        cView?.tvItemName?.text = playlistModel.playlists?.get(groupPosition)?.listItem!![childPosition].title
        cView?.setOnClickListener { callback?.invoke(playlistModel.playlists?.get(groupPosition)?.listItem!![childPosition]) }
        return cView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    private var gView: View? = null
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        gView = convertView
        if (gView == null) {
            gView = LayoutInflater.from(context).inflate(R.layout.item_title_playlist, null)
        }
        gView!!.tvTitle.text = playlistModel.playlists?.get(groupPosition)?.title
        return gView
    }

    override fun getGroupCount(): Int {
        return if (playlistModel.playlists == null) 0 else playlistModel.playlists!!.size
    }

    override fun onGroupCollapsed(p0: Int) {
    }

    override fun isEmpty(): Boolean {
        return playlistModel.playlists == null
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {

    }

    override fun onGroupExpanded(p0: Int) {
    }

    override fun getCombinedChildId(groupId: Long, childId: Long): Long {
        return childId
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun getCombinedGroupId(groupId: Long): Long {
        return groupId
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
    }

}