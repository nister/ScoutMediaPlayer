package com.example.scoutmediaplayer.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.databinding.FragmentPlaylistItemBinding
import com.example.scoutmediaplayer.view.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SongRecyclerViewAdapter(
    private val values: List<Song>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SongRecyclerViewAdapter.SongViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(id: Int, song: Song)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {

        return SongViewHolder(
            FragmentPlaylistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.songTitle
        holder.duration.text = item.songDuration
        holder.root.setOnClickListener { onItemClickListener.onItemClick(position, item) }
    }

    override fun getItemCount(): Int = values.size

    inner class SongViewHolder(
        binding: FragmentPlaylistItemBinding,
        onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val root: View = binding.root
        val title: TextView = binding.songTitle
        val duration: TextView = binding.songDuration
        val artist: TextView = binding.songArtist




        override fun toString(): String {
            return super.toString() + " '" + title.text + "'" + " - " + artist + " - " + duration
        }
    }

}