package com.example.cocktails.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktails.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cocktailAdapter = RecyclerCocktailAdapter().apply {
            setGetCocktailOnClickCallBack {
                vm.openEditOrDetailFragment(it)
            }
        }
        with(binding){
            rvHome.apply {
                layoutManager = LinearLayoutManager(view.context)
                adapter = cocktailAdapter.apply {
                }
            }

            vm.listMergedLiveData.observe(viewLifecycleOwner){
                cocktailAdapter.submitList(it){
                    rvHome.scrollToPosition(0)
                }
            }

            vm.userMessageLiveData.observe(viewLifecycleOwner){
                if(it != 0)
                    Toast
                        .makeText(
                            view.context,
                            view.context.getString(it),
                            Toast.LENGTH_SHORT
                        )
                        .show()
            }

            vm.userNavigationLiveData.observe(viewLifecycleOwner){
                it?.let {
                    findNavController().navigate(it)
                }
            }

            btnMainAdd.setOnClickListener {
                vm.openNewArticleFragment()
            }


        }
    }

    override fun onResume() {
        super.onResume()
        vm.getNewLocalList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}