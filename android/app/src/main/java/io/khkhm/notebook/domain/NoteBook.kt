package io.khkhm.notebook.domain

import io.khkhm.notebook.data.BaseResponse
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


data class NoteBook(var name: String,
                    val date: Date = Date(),
                    var color: Color,
                    val notes: ArrayList<Note> = ArrayList()) : Serializable, BaseResponse() {
    val id: String = UUID.randomUUID().toString()
}












