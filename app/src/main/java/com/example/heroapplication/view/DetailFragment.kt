package com.example.heroapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.heroapplication.R
import com.example.heroapplication.databinding.FragmentDetailBinding
import com.example.heroapplication.model.HeroModel
import com.example.heroapplication.viewModel.DetailViewModel


class DetailFragment : Fragment() {

    private lateinit var viewModel : DetailViewModel
    private var heroId = 0

    private lateinit var dataBinding: FragmentDetailBinding
    private var currentHero: HeroModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            heroId = DetailFragmentArgs.fromBundle(it).heroId
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(heroId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.heroLiveData.observe(viewLifecycleOwner, Observer { hero ->
            currentHero = hero
            hero?.let{
                dataBinding.hero = hero
            }
        })
    }

}