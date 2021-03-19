package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.TODAY_DATE
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapter = AsteroidsAdapter(onClickListenerNavigate())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.header.observe(viewLifecycleOwner, Observer { headers ->
            Glide.with(requireActivity()).load(headers.url).into(binding.activityMainImageOfTheDay)
        })

        observeAsteroids()

        binding.asteroidRecycler.adapter = adapter

        viewModel.navigateAsteroid.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.navigateComplete()
            }
        })

        setHasOptionsMenu(true)

        viewModel.header.observe(viewLifecycleOwner, Observer { itensDay ->
            Log.i("INFOHEADER", itensDay.title)
        })

        return binding.root
    }

    private fun observeAsteroids() {
        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroid ->
            adapter.submitList(asteroid)
        })
    }

    private fun onClickListenerNavigate() = AsteroidsAdapter.OnClickListener { asteroid ->
        viewModel.navigate(asteroid)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_all_menu -> {
                Log.i("INFO", "show_all_menu")
                viewModel.showWeek() //
                observeAsteroids()
                return true
            }
            R.id.show_rent_menu -> {
                Log.i("INFO", "show_rent_menu")
                viewModel.showToday() //Chamada no banco passando a data atual
                observeAsteroids()
                return true
            }
            R.id.show_buy_menu -> {
                Log.i("INFO", "show_buy_menu")
                viewModel.showSaved() // getAllAsteroids
                observeAsteroids()
                return true
            }
        }
        return true
    }
}
