package kz.programmer.loolzrules.endy.fragments


import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*
import kz.programmer.loolzrules.endy.*
import kz.programmer.loolzrules.endy.activities.AddNewEntry
import kz.programmer.loolzrules.endy.dataModels.DBEntry
import kz.programmer.loolzrules.endy.dataModels.DBEntryEncrypted
import kz.programmer.loolzrules.endy.dataModels.rowToEntryParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select


class Main : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.rv_main.adapter = AdapterDbEntry(loadData())
        view.rv_main.layoutManager = LinearLayoutManager(activity, VERTICAL, false)

        view.fab_add_entry.setOnClickListener {
            startActivity(Intent(activity, AddNewEntry::class.java))
        }

        return view
    }


    private fun loadData(): List<DBEntry> {
        val dbEntriesEncrypted = activity.database.use {
            select(DBHelper.TABLE_NAME).exec {
                parseList(rowToEntryParser())
            }
        }

        return if (dbEntriesEncrypted.isEmpty()) emptyList()
        else dbEntriesEncrypted.map { decryptEntry(it) }
    }


    private fun decryptEntry(entry: DBEntryEncrypted): DBEntry {

        val aead = (activity.application as App).aead

        Log.i("LOLF", entry.getResource().toString())

        val decryptedResource = aead?.decrypt(entry.getResource(), null).toString()
        val decryptedUsername = aead?.decrypt(entry.getUsername(), null).toString()
        val decryptedPassword = aead?.decrypt(entry.getPassword(), null).toString()

        return DBEntry(decryptedResource, decryptedUsername, decryptedPassword)
    }


}
