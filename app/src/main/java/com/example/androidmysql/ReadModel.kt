package com.example.androidmysql
import java.io.Serializable
data class ReadModel (
    val result: List<Data>
) {
    data class Data (val id: String?, val name: String?, val number:
    String?) : Serializable
}