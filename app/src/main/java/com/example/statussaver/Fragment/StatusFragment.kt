package com.example.statussaver.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.statussaver.R
import com.example.statussaver.adapter.MediaAdapter
import com.example.statussaver.databinding.FragmentStatusBinding
import com.example.statussaver.factory.StatusViewmodelFactory
import com.example.statussaver.repository.StatusRepo
import com.example.statussaver.utils.Constants
import com.example.statussaver.utils.SessionManger
import com.example.statussaver.utils.getFolderPermission
import com.example.statussaver.viewmodel.StatusViewModel


class StatusFragment : Fragment() {

    lateinit var mBinding: FragmentStatusBinding

    private val WHATSAPP_REQUEST_CODE = 100
    lateinit var sessionManger: SessionManger
    lateinit var statusViewModel: StatusViewModel
    lateinit var statusRepo: StatusRepo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManger = SessionManger(requireContext())
        mBinding = FragmentStatusBinding.inflate(layoutInflater)
        mBinding.tablayout.setupWithViewPager(mBinding.pager)
        statusRepo = StatusRepo(requireContext(), sessionManger)
        statusViewModel = ViewModelProvider(
            requireActivity(),
            StatusViewmodelFactory(repo = statusRepo)
        )[StatusViewModel::class.java]
        if (!sessionManger.getIsUserPermissionGranted()) {
            getFolderPermission(
                context = requireContext(),
                WHATSAPP_REQUEST_CODE,
                Constants.getWhatsappUri()
            )
        }

        prepareViewPager(
            mBinding.pager,
            listOf("Image", "Video"),
            listOf(ImageFragment(), VideoFragment())
        )
        return mBinding.root
    }

    private fun prepareViewPager(pager: ViewPager, listOf: List<String>, fragment: List<Fragment>) {

        pager.adapter = MediaAdapter(
            childFragmentManager,
            listOf,
            fragment
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data?.data
            if (requestCode == WHATSAPP_REQUEST_CODE) {
                sessionManger.saveTreeUri(uri.toString())
                sessionManger.isPermissionGranted(true)
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                statusRepo.getAllStatuses()
            }
        }
    }

}