package com.gosp.apps.hn.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gosp.apps.hn.data.models.NewsModel
import com.gosp.apps.hn.databinding.FragmentNewsListBinding
import com.gosp.apps.hn.main.MainViewModel
import com.gosp.apps.hn.ui.adapters.NewsAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.gosp.apps.hn.R
import com.gosp.apps.hn.data.database.entities.NewsEntity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.dataList.observe(viewLifecycleOwner) { setDataAdapter(it) }

        binding.srNews.setOnRefreshListener {
                viewModel.getDataInDataBase(requireContext())
                binding.srNews.isRefreshing = false
        }


        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvData)
    }

    // Set data to RecycleView
    private fun setDataAdapter(list: ArrayList<NewsEntity>) {
        binding.rvData.apply {
            setHasFixedSize(true)
            adapter = NewsAdapter(list,requireContext()
            ) { url ->
                viewModel.urlNews = url
                findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment())
            }
        }
    }

   override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private val simpleCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            hideNewsFromDataBase(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun hideNewsFromDataBase(position: Int){
        lifecycleScope.launch(Dispatchers.IO) {
            val hideNews: NewsEntity? = viewModel.dataList.value?.get(position)
            if (hideNews != null) {
                hideNews.hidden = true
                viewModel.hideNews(hideNews)
                refreshNewsOnView(position)
            }
        }
    }

    private fun refreshNewsOnView(position: Int){
        viewModel.dataList.value?.removeAt(position)
        binding.rvData.apply{
            adapter?.notifyItemRemoved(position)
            Snackbar.make(this,"Publicación eliminada",Snackbar.LENGTH_LONG).setBackgroundTint(
                ContextCompat.getColor(requireActivity(), R.color.red)).show()
        }
    }
}