package com.example.goliathnationalbank.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.goliathnationalbank.R
import com.example.goliathnationalbank.data.lifecycle.EventAsyncResultObserver
import com.example.goliathnationalbank.databinding.FragmentSplashBinding
import com.example.goliathnationalbank.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentSplashBinding

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_splash,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.requestInitialData()
        fetchData()
    }

    private fun fetchData() {
        mainViewModel.getInitialData().observe(viewLifecycleOwner, object : EventAsyncResultObserver<Boolean>() {
            override fun onSuccess(response: Boolean?) {
                hideLoadingView()
                findNavController().navigate(R.id.from_splash_fragment_to_product_list_fragment)
            }

            override fun onError(error: String, response: Boolean?) {
                hideLoadingView()
                showErrorDialog(error)?.show()
            }
        })
    }

    private fun hideLoadingView() {
        binding.splashAnimationLoading.pauseAnimation()
        binding.splashAnimationLoading.visibility = View.GONE
    }


    private fun showErrorDialog(error: String): AlertDialog? {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(getString(R.string.exit_app)) { _, _ ->
                    it.finish()
                }
            }
            builder.setMessage(error)
            builder.create()
        }
        return alertDialog
    }

}