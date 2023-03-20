package com.diyaddinkilic.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.diyaddinkilic.besinlerkitabi.R
import com.diyaddinkilic.besinlerkitabi.adapter.BesinRecyclerAdapter
import com.diyaddinkilic.besinlerkitabi.viewModel.besinListesiViewModel

class BesinListesiFragment : Fragment() {
    private lateinit var swipeRefreshlayout:SwipeRefreshLayout
    private lateinit var besinYukleniyor:ProgressBar
    private lateinit var besinHataMesaji: TextView
    private lateinit var besinListRecycler: RecyclerView
    private lateinit var viewModel: besinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(besinListesiViewModel::class.java)
        viewModel.refleshData()

        besinListRecycler = view.findViewById(R.id.besinListRecycler)
        besinYukleniyor = view.findViewById(R.id.besinYukleniyor)
        besinHataMesaji = view.findViewById(R.id.besinHataMesaji)
        swipeRefreshlayout = view.findViewById(R.id.swipeRefreshLayout)

        besinListRecycler.layoutManager = LinearLayoutManager(context)
        besinListRecycler.adapter = recyclerBesinAdapter

        swipeRefreshlayout.setOnRefreshListener {
            besinYukleniyor.visibility=View.VISIBLE
            besinHataMesaji.visibility=View.GONE
            besinListRecycler.visibility=View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshlayout.isRefreshing=false
        }
        observeLiveData()
    }
    fun observeLiveData() {
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                besinListRecycler.visibility = View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(besinler)
            }
        })
        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if (it) {
                    besinHataMesaji.visibility = View.VISIBLE
                    besinListRecycler.visibility=View.GONE
                } else {
                    besinHataMesaji.visibility = View.GONE
                }
            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let {
                if (it) {
                    besinListRecycler.visibility = View.GONE
                    besinHataMesaji.visibility = View.GONE
                    besinYukleniyor.visibility = View.VISIBLE
                } else {
                    besinYukleniyor.visibility = View.GONE
                }
            }
        })
    }
}