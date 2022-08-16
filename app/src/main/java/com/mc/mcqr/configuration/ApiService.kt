package com.mc.mcqr.configuration

import com.mc.mcqr.models.Book
import com.mc.mcqr.models.BookRequest
import com.mc.mcqr.models.LoginRequest
import com.mc.mcqr.models.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Paths.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    //  @GET(Paths.BOOKS_URL)
   //  fun getBooks(@Header("Authorization") token: String, @Path("ad_user_id") ad_user_id: Int): Call<Book>
    @GET(Paths.BOOKS_URL)
    fun getBooks(@Header("Authorization") token: String): Call<List<Book>>


}