package com.example.statussaver.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.statussaver.model.MediaModel
import com.example.statussaver.repository.StatusRepo
import com.example.statussaver.utils.Constants
import com.example.statussaver.utils.SessionManger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusViewModel(var repo: StatusRepo) : ViewModel() {
    private val wpStatusLiveData get() = repo.whatsAppStatusLiveData


    val imageLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val videoLiveData = MutableLiveData<ArrayList<MediaModel>>()

    private var isPermissionGranted = false

    init {
        val sessionManger = SessionManger(repo.context)
        isPermissionGranted = sessionManger.getIsUserPermissionGranted()
        if (isPermissionGranted) {
            CoroutineScope(Dispatchers.IO).launch {
                repo.getAllStatuses()
            }
        }
    }

    fun getImages() {
        wpStatusLiveData.observe(repo.context as LifecycleOwner) {
            val temp = ArrayList<MediaModel>()
            it.forEach { mediaModel ->
                if (mediaModel.type == "Image") {
                    temp.add(mediaModel)
                }
            }
            imageLiveData.postValue(temp)
        }
    }

    fun getVideos() {
        wpStatusLiveData.observe(repo.context as LifecycleOwner) {
            val temp = ArrayList<MediaModel>()
            it.forEach { mediaModel ->
                if (mediaModel.type == "Video") {
                    temp.add(mediaModel)
                }
            }
            videoLiveData.postValue(temp)
        }
    }

}