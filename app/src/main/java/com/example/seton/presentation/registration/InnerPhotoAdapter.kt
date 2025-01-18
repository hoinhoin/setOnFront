package com.example.seton.presentation.registration

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.seton.R
import com.example.seton.databinding.ItemInnerPhotoBinding

class InnerPhotoAdapter(
    private val items: List<Uri>,
    //private val onDelete: (Uri) -> Unit
) : RecyclerView.Adapter<InnerPhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemInnerPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val uri = items[position]
        holder.bind(uri)
    }

    override fun getItemCount(): Int = items.size

    inner class PhotoViewHolder(private val binding: ItemInnerPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            binding.ivRegistrationPhoto.setImageURI(uri)
//            binding.ivDeletePhoto.setOnClickListener {
//                onDelete(uri)
//            }
        }
    }
}
