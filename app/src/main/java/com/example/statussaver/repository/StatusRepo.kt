package com.example.statussaver.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.example.statussaver.model.MediaModel
import com.example.statussaver.utils.SessionManger
import com.example.statussaver.utils.getFileExtensions
import com.example.statussaver.utils.isFileExits

class StatusRepo(
    var context: Context,
    var sessionManger: SessionManger
) {
    val whatsAppStatusLiveData = MutableLiveData<ArrayList<MediaModel>>()

    private val whatsApplist = ArrayList<MediaModel>()

    fun getAllStatuses() {

        val treeUri= sessionManger.getTreeUri()
        context.contentResolver.takePersistableUriPermission(
            treeUri!!.toUri(),
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        val fileDocument = DocumentFile.fromTreeUri(context, treeUri!!.toUri())
        fileDocument?.let {
            it.listFiles().forEach { file ->
                if (file.name != ".nomedia" && file.isFile) {
                    val isDownloaded = context.isFileExits(file.name!!)
                    val type = if (getFileExtensions(file.name!!) == "mp4") {
                        "Video"
                    } else {
                        "Image"
                    }

                    val mediaModel = MediaModel(
                        pathUri = file.uri.toString(),
                        fileName = file.name!!,
                        type = type,
                        isDownloaded = isDownloaded
                    )
                    whatsApplist.add(mediaModel)
                }
            }
            whatsAppStatusLiveData.postValue(whatsApplist)
        }
    }
}