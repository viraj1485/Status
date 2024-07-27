package com.example.statussaver.Fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.statussaver.R
import com.example.statussaver.databinding.FragmentVideoPlayingFragmentBinding
import com.example.statussaver.model.MediaModel
import com.example.statussaver.utils.saveStatus

class Video_playing_fragment : Fragment() {

    lateinit var mBinding: FragmentVideoPlayingFragmentBinding
    var toggle = true
    lateinit var mediaModel: MediaModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentVideoPlayingFragmentBinding.inflate(layoutInflater)
        arguments?.let {
            val filename = it.getString("filename", null)
            val filepath = it.getString("filepath", null)
            val filetype = it.getString("filetype", null)
            val isDownloaed = it.getBoolean("isDownloaded", false)

            if (filetype == "Image") {
                mBinding.imageview.visibility = View.VISIBLE
                mBinding.videoView.visibility = View.GONE
                mBinding.toggle.visibility = View.GONE
                Glide.with(requireContext())
                    .load(filepath.toUri())
                    .into(mBinding.imageview)
            } else {
                mBinding.imageview.visibility = View.GONE
                mBinding.videoView.visibility = View.VISIBLE
                mBinding.toggle.visibility = View.VISIBLE
                mBinding.videoView.setVideoURI(filepath.toUri())
                mBinding.videoView.start()
                mBinding.videoView.setOnCompletionListener {
                    requireActivity().onBackPressed()
                }
            }

            mediaModel =
                MediaModel(
                    pathUri = filepath,
                    fileName = filename,
                    type = filetype,
                    isDownloaded = isDownloaed
                )

        }
        mBinding.toggle.setOnClickListener {
            if (toggle) {
                toggle = false
                mBinding.toggle.setImageResource(R.drawable.ic_play_icon)
                mBinding.videoView.pause()
            } else {
                toggle = true
                mBinding.toggle.setImageResource(R.drawable.ic_paus_icon)
                mBinding.videoView.start()
            }
        }
        mBinding.downloadLinear.setOnClickListener {
            if (requireActivity().saveStatus(mediaModel)) {
                Toast.makeText(requireContext(), "Status Saved Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return mBinding.root
    }
}