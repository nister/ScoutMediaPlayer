package com.example.scoutmediaplayer.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

import com.example.scoutmediaplayer.BR

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("items", "itemLayoutResId")
    fun <T> setRecyclerViewAdapter(recyclerView: RecyclerView, items: List<T>?, itemLayoutResId: Int) {
        recyclerView.adapter?.let {
            @Suppress("UNCHECKED_CAST")
            (recyclerView.adapter as MyRecyclerAdapter<T>).setItems(items)
        } ?: run {
            if (items?.isNotEmpty() == true) {
                recyclerView.adapter = MyRecyclerAdapter(items, itemLayoutResId)
            }
        }
    }

    class MyRecyclerAdapter<T>(items: List<T>, private val mItemViewId: Int) : RecyclerView.Adapter<MyViewHolder>() {
        private val myItems = ArrayList<T>()

        init {
            myItems.addAll(items)
        }

        fun setItems(items: List<T>?) {
            myItems.clear()
            items?.let { myItems.addAll(it) }
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), mItemViewId, parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            if (position < myItems.size) {
                holder.bind(myItems[position])
            }
        }

        override fun getItemCount(): Int {
            return myItems.size
        }
    }

    class MyViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Any?) {
            item?.let {
                binding.setVariable(BR.viewModel, it)
                binding.executePendingBindings()
            }
        }
    }
}


