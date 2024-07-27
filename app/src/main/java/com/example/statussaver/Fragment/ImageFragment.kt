package com.example.statussaver.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.statussaver.adapter.ImageAdapter
import com.example.statussaver.databinding.FragmentImageBinding
import com.example.statussaver.factory.StatusViewmodelFactory
import com.example.statussaver.model.MediaModel
import com.example.statussaver.repository.StatusRepo
import com.example.statussaver.utils.SessionManger
import com.example.statussaver.viewmodel.StatusViewModel

class ImageFragment : Fragment() {

    lateinit var mBinding: FragmentImageBinding
    lateinit var statusViewModel: StatusViewModel
    lateinit var statusRepo: StatusRepo
    lateinit var sessionManger: SessionManger
    var adapterData = ArrayList<MediaModel>()
    lateinit var imageAdapter: ImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentImageBinding.inflate(layoutInflater)
        sessionManger = SessionManger(requireContext())
        statusRepo = StatusRepo(requireContext(), sessionManger)
        statusViewModel = ViewModelProvider(
            requireActivity(),
            StatusViewmodelFactory(repo = statusRepo)
        )[StatusViewModel::class.java]
        statusViewModel.getImages()
        imageAdapter = ImageAdapter(adapterData, requireActivity().supportFragmentManager)
        mBinding.imageRecyle.adapter = imageAdapter
        setUpObserver()
        return mBinding.root
    }

    fun setUpObserver() {
        statusViewModel.imageLiveData.observe(viewLifecycleOwner) {
//            adapterData = it
            imageAdapter.updateData(it)
        }
    }
}