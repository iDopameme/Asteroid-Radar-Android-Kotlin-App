package com.udacity.asteroidradar.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.getViewModel
import com.udacity.asteroidradar.repository.Repository
import com.udacity.asteroidradar.viewadapters.MainRecyclerAdapter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        getViewModel { MainViewModel(Repository())}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = MainRecyclerAdapter(MainRecyclerAdapter.OnClickListener {
            viewModel.displayAsteroidDetails(it)
        })

        viewModel.isImageLoading.observe(viewLifecycleOwner) { isImageLoading ->
            Log.i(TAG, "isImageLoading $isImageLoading")
            binding.imageLoadingWheel.visibility = if (isImageLoading) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            Log.i(TAG, "isLoading $isLoading")
            binding.statusLoadingWheel.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage == null) {
                binding.loadError.visibility = View.GONE
            } else {
                binding.loadError.visibility = View.VISIBLE
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.imageErrorMessage.observe(viewLifecycleOwner) { imageErrorMessage ->
            if (imageErrorMessage == null) {
                binding.imageLoadError.visibility = View.GONE
            } else {
                binding.imageLoadError.visibility = View.VISIBLE
                Toast.makeText(requireContext(), imageErrorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    // inflates the options menu on the fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Options menu to determine what happens during menu item select
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
