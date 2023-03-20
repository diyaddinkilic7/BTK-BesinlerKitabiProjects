package com.diyaddinkilic.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diyaddinkilic.besinlerkitabi.R
import com.diyaddinkilic.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.diyaddinkilic.besinlerkitabi.util.gorselIndir
import com.diyaddinkilic.besinlerkitabi.util.placeholderYap
import com.diyaddinkilic.besinlerkitabi.viewModel.besinDetayiViewModel

class BesinDetayiFragment : Fragment() {
    /*
    private lateinit var besinIsim: TextView
    private lateinit var besinKalori: TextView
    private lateinit var besinKarbonhidrat: TextView
    private lateinit var besinProtein: TextView
    private lateinit var besinYag: TextView
     */
    private lateinit var viewModel: besinDetayiViewModel
    private lateinit var dataBinding: FragmentBesinDetayiBinding
    private var besinId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_besin_detayi, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProvider(this).get(besinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {

                dataBinding.secilenBesin = it

                /*
                view?.findViewById<TextView>(R.id.besinIsim)?.text = it.besinIsim
                view?.findViewById<TextView>(R.id.besinKalori)?.text = it.besinKalori
                view?.findViewById<TextView>(R.id.besinKarbonhidrat)?.text = it.besinKarbohidrat
                view?.findViewById<TextView>(R.id.besinProtein)?.text = it.besinProtein
                view?.findViewById<TextView>(R.id.besinYag)?.text = it.besinYag
                context?.let {
                    view?.findViewById<ImageView>(R.id.besinImage)?.let { imageView ->
                        besin.besinGorsel?.let { gorselUrl ->
                            imageView.gorselIndir(gorselUrl, placeholderYap(it))
                        }
                    }
                }
                 */
            }
        })
    }
}