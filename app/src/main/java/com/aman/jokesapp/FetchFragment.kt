package com.aman.jokesapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


class FetchFragment : Fragment() {

    private lateinit var joke:List<Jokes>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
        ): View {
        val view:View=inflater.inflate(R.layout.fragment_fetch, container, false)

        var rv:RecyclerView=view.findViewById(R.id.rvJokes)
        var ja:JokesAdapter= JokesAdapter(joke)

        rv.layoutManager=LinearLayoutManager(context)
        rv.adapter=ja




        RetrofitInstance.api.getTodos().enqueue(object : Callback<List<Jokes>>{

            override fun onResponse(call: Call<List<Jokes>>, response: Response<List<Jokes>>) {
                if(response.message()=="OK") {
                    joke = response.body()!!

                    Log.d("apicall", joke.toString() )

                    ja = JokesAdapter(joke)
                    ja.notifyDataSetChanged()
                }
                else{
                      Log.d("apicall", response.message() )

                }

            }

            override fun onFailure(call: Call<List<Jokes>>, t: Throwable) {
                Log.d("apicall", t.message.toString() )
            }
        })

        return view
    }

    }
