package kr.goldenmine.disasterservicebackend.collector

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DisasterDetectionServer {
    @Multipart
    @POST("/disasterdetect/")
    fun request(
        @Part image: MultipartBody.Part, // 업로드할 파일
    ): Call<DisasterDetectionResponse>
}