package com.slava_110.androidkotlinpractice.tasks.serverdata.ui

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
import com.slava_110.androidkotlinpractice.databinding.FragmentServerDataListBinding
import com.slava_110.androidkotlinpractice.tasks.serverdata.repository.Answer
import com.slava_110.androidkotlinpractice.tasks.serverdata.repository.ServerDataDao
import com.slava_110.androidkotlinpractice.util.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class ServerDataListFragment : Fragment(R.layout.fragment_server_data_list) {
    private val viewBinding by viewBinding(FragmentServerDataListBinding::bind)
    private val yesNoDatabase by inject<ServerDataDao>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ServerDataListAdapter()

        viewBinding.recyclerView2.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val list = yesNoDatabase.getAll()
            withContext(Dispatchers.Main) {
                adapter.submitList(list)
            }
        }

        viewBinding.backButton3.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private class ServerDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.server_data_item_text)

        fun bind(text: String) {
            textView.text = text
        }
    }

    private class ServerDataListAdapter: ListAdapter<Answer, ServerDataViewHolder>(AnswersComparator) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerDataViewHolder =
            ServerDataViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_server_data_list_item, parent, false)
            )

        override fun onBindViewHolder(holder: ServerDataViewHolder, position: Int) {
            val cur = getItem(position)
            holder.bind(cur.yesOrNo)
        }

    }

    object AnswersComparator: DiffUtil.ItemCallback<Answer>() {

        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem.yesOrNo == newItem.yesOrNo

    }
}