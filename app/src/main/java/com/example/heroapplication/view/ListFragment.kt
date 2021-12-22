package com.example.heroapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.heroapplication.R
import com.example.heroapplication.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val heroesListAdapter = HeroRecyclerViewAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        heroList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = heroesListAdapter
        }

        refreshLayout.setOnRefreshListener {
            heroList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
//            viewModel.refreshByCache()
            refreshLayout.isRefreshing = false
        }

        observerViewModel()
    }

    fun observerViewModel() {
        viewModel.heroes.observe(viewLifecycleOwner, Observer { heroes ->
            heroes?.let {
                heroList.visibility = View.VISIBLE
                heroesListAdapter.updateDogList(heroes)
            }
        })

        viewModel.heroesLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    heroList.visibility = View.GONE
                }
            }
        })
    }
}