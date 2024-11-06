package paba.materi.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterRecView (private val listwayang: ArrayList<wayang>) : RecyclerView.Adapter<adapterRecView.ListViewHolder> () {
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var _namaWayang = itemView.findViewById<TextView>(R.id.namaWayangTV)
        var _karakterWayang = itemView.findViewById<TextView>(R.id.karakterWayangTV)
        var _deskripsiWayang = itemView.findViewById<TextView>(R.id.deskripsiWayangTV)
        var _gambarWayang = itemView.findViewById<TextView>(R.id.gambarWayangIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listwayang.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var wayang = listwayang[position]

        holder._namaWayang.setText(wayang.nama)
        holder._deskripsiWayang.setText(wayang.deskripsi)
        holder._karakterWayang.setText(wayang.karakter)
    }
}