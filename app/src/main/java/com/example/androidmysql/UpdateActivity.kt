package com.example.androidmysql

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class UpdateActivity : AppCompatActivity() {
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var mbUpdate: MaterialButton
    private val contact by lazy {
        intent.getSerializableExtra("contact") as ReadModel.Data }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,
                                                                             insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,
                systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        setupListener()
    }
    private fun setupView() {
        etName = findViewById(R.id.EditTextName)
        etNumber = findViewById(R.id.EditTextNumber)
        mbUpdate = findViewById(R.id.mbUpdate)
        etName.setText(contact.name)
        etNumber.setText(contact.number)
    }
    private fun setupListener() {
        mbUpdate.setOnClickListener {
            if (etName.text.toString().isNotEmpty() ||
                etNumber.text.toString().isNotEmpty()) {
                Log.i("is4", "name: ${etName.text.toString()}")
                Log.i("is4", "number: ${etNumber.text.toString()}")
                api.update(contact.id!!, etName.text.toString(),
                    etNumber.text.toString())
                    .enqueue(object : Callback<SubmitModel> {
                        override fun onResponse(p0: Call<SubmitModel>,
                                                p1: Response<SubmitModel>) {
                            if (p1.isSuccessful) {
                                val submit = p1.body()
                                Toast.makeText(
                                    applicationContext,
                                    submit!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                        }
                        override fun onFailure(p0: Call<SubmitModel>,
                                               p1: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or Number cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
