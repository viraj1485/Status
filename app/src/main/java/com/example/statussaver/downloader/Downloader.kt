package com.example.statussaver.downloader

import android.net.Uri

interface Downloader {
    fun download(uri: String): Long
}