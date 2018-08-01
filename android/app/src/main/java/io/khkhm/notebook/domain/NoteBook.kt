package io.khkhm.notebook.domain

import com.google.gson.annotations.SerializedName
import io.khkhm.notebook.data.BaseResponse
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


data class NoteBook(var name: String,
                    val date: Date = Date(),
                    var color: Color,
                    val notes: ArrayList<Note> = ArrayList(),
                    @SerializedName("_id") val id: String = "") : Serializable, BaseResponse() {
}












