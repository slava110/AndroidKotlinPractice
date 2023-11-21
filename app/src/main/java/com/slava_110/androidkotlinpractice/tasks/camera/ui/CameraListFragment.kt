package com.slava_110.androidkotlinpractice.tasks.camera.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slava_110.androidkotlinpractice.R
import com.slava_110.androidkotlinpractice.databinding.FragmentCameraListBinding
import com.slava_110.androidkotlinpractice.util.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class CameraListFragment: Fragment(R.layout.fragment_camera_list) {
    private val viewBinding by viewBinding(FragmentCameraListBinding::bind)
    private val viewModel by viewModel<CameraListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CameraListAdapter()

        viewBinding.recyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val dates = viewModel.getDates()

            withContext(Dispatchers.Main) {
                adapter.submitList(dates)
            }
        }

        viewBinding.butBack15.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    class CameraListAdapter: ListAdapter<String, CameraListViewHolder>(StringComparator) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraListViewHolder =
            CameraListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_camera_list_item, parent, false) as TextView
            )


        override fun onBindViewHolder(holder: CameraListViewHolder, position: Int) {
            val cur = getItem(position)
            holder.bind(cur)
        }

    }

    object StringComparator: DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.contentEquals(newItem)

    }

    class CameraListViewHolder(view: TextView): RecyclerView.ViewHolder(view) {
        private val textView: TextView = view

        fun bind(text: String) {
            textView.text = text
        }
    }
}