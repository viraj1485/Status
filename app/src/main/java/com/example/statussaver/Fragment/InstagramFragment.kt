package com.example.statussaver.Fragment

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.example.statussaver.R
import com.example.statussaver.databinding.FragmentInstagramBinding
import com.example.statussaver.downloader.AndroidDownloader

// TODO: Rename parameter arguments, choose names that match
class InstagramFragment : Fragment() {
    lateinit var mBinding: FragmentInstagramBinding
    lateinit var config: PRDownloaderConfig
    private var downloadId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentInstagramBinding.inflate(layoutInflater)
        var androidDownloader = AndroidDownloader(requireContext())
        config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(requireContext(), config);
        mBinding.btnDowload.setOnClickListener {
            if (mBinding.edurl.toString().trim().isNotEmpty()) {
//                androidDownloader.download(mBinding.edurl.toString().trim())
                startDownload(mBinding.edurl.toString().trim(), "demo")
            }
        }
        return (mBinding.root)
    }

    private fun startDownload(url: String, fileName: String) {
        downloadId = PRDownloader.download(url, Environment.DIRECTORY_DOWNLOADS, fileName)
            .build()
            .setOnStartOrResumeListener {
                Toast.makeText(requireContext(),"Download",Toast.LENGTH_SHORT).show()
                // Started or resumed
            }
            .setOnPauseListener {
                // Paused
            }
            .setOnCancelListener {
                // Canceled
            }
            .setOnProgressListener { progress ->
                // Update progress bar
                val progressPercent = (progress.currentBytes * 100 / progress.totalBytes).toInt()
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    // Download completed
                }

                override fun onError(error: com.downloader.Error?) {
                    // Handle error
                    Toast.makeText(requireContext(),error?.serverErrorMessage.toString(),Toast.LENGTH_SHORT).show()
                }
            })
    }
}