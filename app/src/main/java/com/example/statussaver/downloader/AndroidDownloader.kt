package com.example.statussaver.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import java.net.URL
import java.net.URLConnection

class AndroidDownloader(
    var context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun download(uri: String): Long {
        val request=DownloadManager.Request(uri.toUri())
            .setMimeType("video/mp4")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(uri)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Insta.mp4")

        return downloadManager.enqueue(request)
    }

    fun getMimeType(urlString: String): String? {
        return try {
            val url = URL(urlString)
            val connection: URLConnection = url.openConnection()
            connection.connect()
            connection.contentType
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}