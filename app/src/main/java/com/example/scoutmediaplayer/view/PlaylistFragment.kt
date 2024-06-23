package com.example.scoutmediaplayer.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.scoutmediaplayer.data.Song
import com.example.scoutmediaplayer.databinding.FragmentPlaylistListBinding
import com.example.scoutmediaplayer.domain.SongsRepositoryImpl
import com.example.scoutmediaplayer.view.SongRecyclerViewAdapter.OnItemClickListener
import com.example.scoutmediaplayer.viewmodel.PlaylistFragmentViewModel

/**
 * A fragment representing a list of Items.
 */
class PlaylistFragment : Fragment() {

    private lateinit var playlistFragmentListener: PlaylistFragmentListener
    private val songsList: ArrayList<Song> = ArrayList()
    private lateinit var adapter: SongRecyclerViewAdapter
    private lateinit var songRepository: SongsRepositoryImpl
    private lateinit var viewBinding: FragmentPlaylistListBinding
    private var columnCount = 1

    interface PlaylistFragmentListener {
        fun onSongSelected(id: Int, song: Song)
    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val LOG_TAG = "PlaylistFragment"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PlaylistFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        songRepository = SongsRepositoryImpl()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        playlistFragmentListener = context as PlaylistFragmentListener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPlaylistListBinding.inflate(layoutInflater, container, false)
        viewBinding.viewModel = PlaylistFragmentViewModel(songRepository)
        val view = viewBinding.root
//        viewBinding.playlistAdd.setOnClickListener { addSongs() }
        // Set the adapter
        var onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(id: Int, song: Song) {
                Log.e(LOG_TAG, "onItemClick: $id")
                playlistFragmentListener.onSongSelected(id, song)
            }

        }
        adapter = SongRecyclerViewAdapter(songsList, onItemClickListener)
        viewBinding.playlistRecyclerview.adapter = adapter
        viewBinding.playlistRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = this@PlaylistFragment.adapter
//            }
//        }
        return view
    }

//    private fun addSongs() {
//
//    }


}