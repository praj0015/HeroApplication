package com.example.heroapplication.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.heroapplication.R
import com.example.heroapplication.databinding.ItemHeroBinding
import com.example.heroapplication.model.HeroModel
import com.example.heroapplication.retrofit.HeroApiService
import com.example.heroapplication.retrofit.RestAPI
import com.example.heroapplication.util.getProgressDrawable
import com.example.heroapplication.util.loadImage
import kotlinx.android.synthetic.main.item_hero.view.*

class HeroRecyclerViewAdapter(val heroList: ArrayList<HeroModel>) :
    RecyclerView.Adapter<HeroRecyclerViewAdapter.HeroViewHolder>(), HeroClickListener {

    class HeroViewHolder(var view: ItemHeroBinding) : RecyclerView.ViewHolder(view.root)

    fun updateDogList(newHeroList: List<HeroModel>) {
        heroList.clear()
        heroList.addAll(newHeroList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(DataBindingUtil.inflate<ItemHeroBinding>
            (LayoutInflater.from(parent.context),
                R.layout.item_hero,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.view.hero = heroList[position]
        holder.view.clickListener = this
//        val curHero = heroList[position]
//        holder.itemView.name.text = curHero.localized_name
//        curHero.img = RestAPI.BASE_URL + curHero.img
//        holder.itemView.imageView.loadImage(curHero.img, getProgressDrawable(holder.itemView.imageView.context))
    }

    override fun onHeroClicked(v: View) {
        val id = v.heroId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.heroId = id
        Navigation.findNavController(v).navigate(action)
    }

    override fun getItemCount(): Int {
        return heroList.size
    }
}