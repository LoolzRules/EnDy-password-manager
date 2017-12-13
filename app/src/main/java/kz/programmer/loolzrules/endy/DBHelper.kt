package kz.programmer.loolzrules.endy

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.BLOB
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.createTable

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "LibraryDatabase", null, 1) {

    companion object {

        const val TABLE_NAME = "pswds"
        const val COLUMN_RESOURCES = "resources"
        const val COLUMN_USERNAMES = "usernames"
        const val COLUMN_PASSWORDS = "passwords"

        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.createTable(TABLE_NAME, true,
                COLUMN_RESOURCES to BLOB,
                COLUMN_USERNAMES to BLOB,
                COLUMN_PASSWORDS to BLOB)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

}

val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)