package com.example.statussaver.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statussaver.Fragment.Video_playing_fragment
import com.example.statussaver.R
import com.example.statussaver.databinding.ImageLayoutBinding
import com.example.statussaver.model.MediaModel

class ImageAdapter(var data: ArrayList<MediaModel>, var fragementManger: FragmentManager) :
    RecyclerView.Adapter<ImageAdapter.MyView>() {
    inner class MyView(var imageLayoutBinding: ImageLayoutBinding) :
        RecyclerView.ViewHolder(imageLayoutBinding.root) {
        fun bind(mediaModel: MediaModel) {
            Glide.with(imageLayoutBinding.thumbnail.context)
                .load(mediaModel.pathUri.toUri())
                .circleCrop()
                .into(imageLayoutBinding.thumbnail)
            imageLayoutBinding.thumbnail.setOnClickListener {
                val bundle = Bundle()

                bundle.putString("filename", data[position].fileName)
                bundle.putString("filepath", data[position].pathUri)
                bundle.putString("filetype", data[position].type)
                bundle.putBoolean("isDownloaded", data[position].isDownloaded)

                val replacedFragment = Video_playing_fragment()
                replacedFragment.arguments = bundle
                fragementManger.beginTransaction().replace(R.id.container, replacedFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(
            ImageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(mediaModel = data[position])
    }

    fun updateData(latestData: ArrayList<MediaModel>) {
        this.data = latestData
        notifyDataSetChanged()
    }
}