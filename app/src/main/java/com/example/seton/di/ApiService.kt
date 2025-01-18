package com.example.seton.di

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @Multipart
    @POST("/houses") // 서버 엔드포인트 경로
    suspend fun uploadData(
        @Part("title") title: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part("size") size: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tags") tags: RequestBody,
        @Part thumbnail: MultipartBody.Part,
        @Part images: List<MultipartBody.Part>
    )
}

fun createPartFromString(value: String): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
}

//fun prepareFilePart(context: Context, partName: String, fileUri: Uri): MultipartBody.Part {
//    val file = File(fileUri.path ?: "")
//    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
//}