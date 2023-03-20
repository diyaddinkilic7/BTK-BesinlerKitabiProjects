package com.diyaddinkilic.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.diyaddinkilic.besinlerkitabi.R
import com.diyaddinkilic.besinlerkitabi.databinding.BesinRecyclerRowBinding
import com.diyaddinkilic.besinlerkitabi.model.Besin
import com.diyaddinkilic.besinlerkitabi.util.gorselIndir
import com.diyaddinkilic.besinlerkitabi.util.placeholderYap
import com.diyaddinkilic.besinlerkitabi.view.BesinListesiFragmentDirections

class BesinRecyclerAdapter(val besinListesi: ArrayList<Besin>) :
    RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>(), BesinClickListener {
    class BesinViewHolder(var view: BesinRecyclerRowBinding) :
        RecyclerView.ViewHolder(view.root) {
        //   val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //   val view = inflater.inflate(R.layout.besin_recycler_row, parent, false)
        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(
            inflater,
            R.layout.besin_recycler_row,
            parent,
            false
        )
        return BesinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.besin = besinListesi[position]
        holder.view.listener = this

        /*
        holder.itemView.findViewById<TextView>(R.id.ismi).text =
            besinListesi.get(position).besinIsim
        holder.itemView.findViewById<TextView>(R.id.kalorisi).text =
            besinListesi.get(position).besinKalori
        holder.itemView.setOnClickListener {
            val action =
                BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(
                    besinListesi.get(position).uuid
                )
            Navigation.findNavController(it).navigate(action)
        }

        besinListesi.get(position).besinGorsel?.let {
            holder.imageView.gorselIndir(
                it,
                placeholderYap(holder.itemView.context)
            )
        }
         */
    }

    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>) {
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }

    override fun besinTiklandi(view: View) {
        val uuid = view.findViewById<TextView>(R.id.besin_uuid).text.toString().toIntOrNull()
        uuid?.let {
            val action =
                BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}