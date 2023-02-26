package com.example.myacronymapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myacronymapplication.data.Lf
import com.example.myacronymapplication.databinding.LongformItemBinding

class LongFormMainAdapter : RecyclerView.Adapter<LongFormMainAdapter.LongFormMainViewHolder>() {

    var lfList: List<Lf> = listOf()

    inner class LongFormMainViewHolder(binding: LongformItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: LongformItemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LongFormMainViewHolder {
        val binding =
            LongformItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LongFormMainViewHolder(binding)
    }

    override fun getItemCount(): Int = lfList.size

    override fun onBindViewHolder(holder: LongFormMainViewHolder, position: Int) {
        holder.binding.apply {
            longformText.text = lfList.get(position).lf
            sinceText.text = lfList.get(position).since?.let {
                it.toString()
            }
            varRecycler.visibility = View.VISIBLE
            val adapter = VariationLongFormAdapter()
            varRecycler.adapter = adapter
            lfList.get(position).vars?.let {
                if (it.size > 1) {
                    adapter.setVarList(it.subList(1,it.size))
                } else {
                    varRecycler.visibility = View.GONE
                }

            }
        }
    }

    fun setLFList(l: List<Lf>) {
        lfList = l
        notifyDataSetChanged()
    }
}