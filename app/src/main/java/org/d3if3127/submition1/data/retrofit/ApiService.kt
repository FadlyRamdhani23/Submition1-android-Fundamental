package org.d3if3127.submition1.data.retrofit

import org.d3if3127.submition1.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGithubUser(@Query("q")users: String): Call<GithubResponse>


}