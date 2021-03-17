package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

//    private lateinit var recyclerView: RecyclerView
//    private var adapterAsteroidsAdapter: AsteroidsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.header.observe(viewLifecycleOwner, Observer { headers ->
            Glide.with(requireActivity()).load(headers.url).into(binding.activityMainImageOfTheDay)
        })

        val adapterAsteroidsAdapter = AsteroidsAdapter()

        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroid ->
            adapterAsteroidsAdapter.submitList(asteroid)
        })

        binding.asteroidRecycler.adapter = adapterAsteroidsAdapter

        setHasOptionsMenu(true)

        viewModel.header.observe(viewLifecycleOwner, Observer { itensDay ->
            Log.i("INFOHEADER", itensDay.title)
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
