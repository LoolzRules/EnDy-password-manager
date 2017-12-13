package kz.programmer.loolzrules.endy.dataModels

import android.util.Log
import org.jetbrains.anko.db.RowParser


class rowToEntryParser : RowParser<DBEntryEncrypted> {

    override fun parseRow(columns: Array<Any?>): DBEntryEncrypted {
        Log.i("LOLF", columns[0].toString().toByteArray().toString())
        return DBEntryEncrypted(
                columns[0].toString().toByteArray(),
                columns[1].toString().toByteArray(),
                columns[2].toString().toByteArray()
        )
    }

}