package com.example.tdm2_tp2_exo4.services

import com.example.tdm2_tp2_exo4.Task
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @GET("todos")
    fun getTasklist(): Call<List<Task>>
    @GET("todos/{id}")
    fun getTask(@Path("id") id: String): Call<Task>
    @POST("todos")
    fun addTask(@Body newTask: Task): Call<Task>

    @DELETE("todos/{id}")
    fun deleteTask(@Path("id") id: String): Call<Unit>
}