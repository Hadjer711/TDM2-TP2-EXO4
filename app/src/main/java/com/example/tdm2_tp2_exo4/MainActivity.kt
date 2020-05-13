package com.example.tdm2_tp2_exo4

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.tdm2_tp2_exo4.services.TaskService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var itemlist = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)


        load.setOnClickListener{
            loadTasks()
            tasklist.adapter =  adapter
            adapter.notifyDataSetChanged()
        }


        /*add.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create<TaskService>(TaskService::class.java)
            service.getTask("1").enqueue(object: Callback<Task> {
                override fun onResponse(call: Call<Task>, response: retrofit2.Response<Task>?) {
                    if ((response != null) && (response.code() == 200)) {
                        var task = response.body()
                        editText.setText(task!!.title)
                        Toast.makeText(this@MainActivity, "Succès", Toast.LENGTH_LONG).show()

                    }

                }

                override fun onFailure(call: Call<Task>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Echec", Toast.LENGTH_LONG).show()
                }
            })
        }*/

        // ajouter une tache
        add.setOnClickListener {
            itemlist.add(editText.text.toString())
            tasklist.adapter =  adapter
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }


        tasklist.setOnItemClickListener { adapterView, view, i, l ->
            tasklist.setItemChecked(i, true)
            adapter.notifyDataSetChanged()
            android.widget.Toast.makeText(this, "You Selected the item --> "+itemlist.get(i), android.widget.Toast.LENGTH_SHORT).show()
        }

        //sup une tache
        delete.setOnClickListener {
            val position: SparseBooleanArray = tasklist.checkedItemPositions
            val count = tasklist.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {
            itemlist.clear()
            adapter.notifyDataSetChanged()

        }


    }

    private fun loadTasks(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create<TaskService>(TaskService::class.java)
        service.getTasklist().enqueue(object: Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: retrofit2.Response<List<Task>>?) {
                if ((response != null) && (response.code() == 200)) {
                    var taskList = response.body()
                    var size = taskList!!.size
                    println(size)
                    for(i in 1..7){
                        itemlist.add(taskList!!.get(i).title)
                    }
                    println(taskList)


                    Toast.makeText(this@MainActivity, "Succès", Toast.LENGTH_LONG).show()

                }

            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Echec", Toast.LENGTH_LONG).show()
            }
        })



    }







}
