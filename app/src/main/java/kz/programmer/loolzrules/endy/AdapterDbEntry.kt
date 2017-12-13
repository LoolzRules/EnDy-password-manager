package kz.programmer.loolzrules.endy

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_db_entry.view.*
import kz.programmer.loolzrules.endy.dataModels.DBEntry

class AdapterDbEntry(private val entries: List<DBEntry>) : RecyclerView.Adapter<AdapterDbEntry.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_db_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(entries[position])
    }

    override fun getItemCount() = entries.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(entry: DBEntry) {
            itemView.tv_resource.text = "Resource: ${entry.getResource()}"
            itemView.tv_username.text = "Username: ${entry.getUsername()}"
            itemView.tv_password.text = "Password: ${entry.getPassword()}"
        }
    }
}
