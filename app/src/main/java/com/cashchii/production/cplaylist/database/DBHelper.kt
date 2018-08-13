package com.cashchii.production.cplaylist.database

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import com.cashchii.production.cplaylist.common.Constant
import com.cashchii.production.cplaylist.model.ItemModel
import com.cashchii.production.cplaylist.model.PlaylistModel

/**
 * Created by Panc. on 8/11/2018 AD.
 */
@Database(entities = [ItemModel::class, PlaylistModel::class], version = Constant.DB_VERSION)
@TypeConverters(ModelConverter::class)
abstract class DBHelper : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        fun getDB(context: Context): DBHelper = Room.databaseBuilder(context, DBHelper::class.java, Constant.DB_NAME).build()
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}