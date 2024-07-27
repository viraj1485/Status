package com.example.statussaver.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract

fun getFolderPermission(context: Context, REQUESTCODE: Int, initialURI: Uri?) {
    val activity = context as Activity
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialURI)
    activity.startActivityForResult(intent, REQUESTCODE)
}