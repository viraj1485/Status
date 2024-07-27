package com.example.statussaver.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.statussaver.adapter.VideoAdapter
import com.example.statussaver.databinding.FragmentVideoBinding
import com.example.statussaver.factory.StatusViewmodelFactory
import com.example.statussaver.model.MediaModel
import com.example.statussaver.repository.StatusRepo
import com.example.statussaver.utils.SessionManger
import com.example.statussaver.viewmodel.StatusViewModel

class VideoFragment : Fragment() {

    lateinit var mBinding: FragmentVideoBinding
    lateinit var sessionManger: SessionManger
    lateinit var statusViewModel: StatusViewModel
    lateinit var statusRepo: StatusRepo
    lateinit var videoAdapter: VideoAdapter
    var adapterData = ArrayList<MediaModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentVideoBinding.inflate(layoutInflater)
        sessionManger = SessionManger(requireContext())
        statusRepo = StatusRepo(requireContext(), sessionManger)
        statusViewModel = ViewModelProvider(
            requireActivity(),
            StatusViewmodelFactory(repo = statusRepo)
        )[StatusViewModel::class.java]
        statusViewModel.getVideos()
        videoAdapter=VideoAdapter(adapterData,requireActivity().supportFragmentManager)
        mBinding.imageRecyle.adapter = videoAdapter
        setUpObserver()
        return mBinding.root
    }

    fun setUpObserver() {
        statusViewModel.videoLiveData.observe(viewLifecycleOwner) {
            videoAdapter.updateData(it)
        }
    }
}