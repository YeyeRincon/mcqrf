package com.mc.mcqr.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mc.mcqr.R
import com.mc.mcqr.configuration.ApiClient
import com.mc.mcqr.configuration.SessionManager
import com.mc.mcqr.models.Book
import com.mc.mcqr.models.LoginRequest
import com.mc.mcqr.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    lateinit var bookList: MutableList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        var authToken = "";
        var id = 0;

        // Login
        apiClient.getApiService(this)
            .login(LoginRequest(username = "pruebas", password = 123456))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("main", "login error")
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    authToken = loginResponse?.token.toString();
                    id = 1000003;
                    Log.d("main", "response code : " + response.code())
                    Log.d("main", "Authorization: " + authToken)

                    if (response.code() == 200 && authToken != "") {
                        Log.d("main", "Login is successful!")
                        sessionManager.saveAuthToken(authToken)

                        var token2:String ="Bearer " + authToken
                        getBookList(id, token2)
                    } else {
                        Log.e("main", "login is not allowed")
                    }
                }

            })
    }

    fun getBookList(id: Int, token2: String) {
        apiClient.getApiService(this).getBooks(token = token2)
            .enqueue(object : Callback<List<Book>> {
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    Log.e("main", "Se revento  XD")
                }


                override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                    if (response.code() == 200) {
                        Log.d("main", "book list received")
                        bookList = (response.body() as MutableList<Book>?)!!
                        Log.d("main", bookList.toString())
                    } else {
                        Log.e("main", "Aquí voy XD " + response.code())
                    }
                }
            })
    }

}


