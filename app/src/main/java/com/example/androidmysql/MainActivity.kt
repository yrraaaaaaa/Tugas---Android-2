package com.example.androidmysql

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {
    private val api by lazy { ApiRetrofit().endpoint}
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var listContact: RecyclerView
    private lateinit var fabCreate: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,
                                                                             insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,
                systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        setupList()
        setupListener()
    }
    override fun onStart() {
        super.onStart()
        getContact()
    }
    private fun setupView() {
        listContact = findViewById(R.id.RecyclerViewContact)
        fabCreate = findViewById(R.id.fabCreate)
    }
    private fun setupList() {
        contactAdapter = ContactAdapter(arrayListOf(), object :
            ContactAdapter.OnAdapterListener{
            override fun onUpdate(contact: ReadModel.Data) {
                startActivity(
                    Intent(this@MainActivity,
                        UpdateActivity::class.java)
                        .putExtra("contact", contact)
                )
            }
            override fun onDelete(contact: ReadModel.Data) {
                val builder: AlertDialog.Builder =
                    AlertDialog.Builder(this@MainActivity)
                builder
                    .setMessage("Yakin akan menghapus data ${contact.name} ?")
                .setTitle("Hapus Data")
                    .setPositiveButton("Hapus") { dialog, which ->
                        api.delete(contact.id!!)
                            .enqueue(object : Callback<SubmitModel>{
                                override fun onResponse(p0:
                                                        Call<SubmitModel>, p1: Response<SubmitModel>) {
                                    if (p1.isSuccessful) {
                                        val submit = p1.body()
                                        Toast.makeText(
                                            applicationContext,
                                            submit!!.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        getContact()
                                    }
                                }
                                override fun onFailure(p0:
                                                       Call<SubmitModel>, p1: Throwable) {
                                    TODO("Not yet implemented")
                                }
                            })
                    }
                    .setNegativeButton("Batal") { dialog, which ->
                        // Do something else.
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        })
        listContact.adapter = contactAdapter
    }
    private fun setupListener() {
        fabCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }
    private fun getContact() {
        api.data().enqueue(object : Callback<ReadModel> {
            override fun onResponse(p0: Call<ReadModel>, p1:
            Response<ReadModel>) {
                if (p1.isSuccessful) {
                    val listData = p1.body()!!.result
                    contactAdapter.setData(listData)
                    listData.forEach{
                    Log.i("is4", "name: ${it.name}")
                   Log.i("is4", "number: ${it.number}")
                    }
//Log.i("PPM", p1.toString())
                }
            }
            override fun onFailure(p0: Call<ReadModel>, p1: Throwable)
            {
                Log.e("is4", p1.toString())
            }
        })
    }
}
