package com.example.seton.di

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @Multipart
    @POST("/houses")
    suspend fun uploadData(
        @Part("request") request: RequestBody,
        @Part thumbnail: MultipartBody.Part,
        @Part images: List<MultipartBody.Part>
    ): Response<Void>
}

fun createPartFromString(value: String): RequestBody {
    return value.toRequestBody("text/plain".toMediaTypeOrNull())
}

//fun prepareFilePart(context: Context, partName: String, fileUri: Uri): MultipartBody.Part {
//    val file = File(fileUri.path ?: "")
//    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
//}