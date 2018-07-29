package io.khkhm.notebook.data

import io.khkhm.notebook.domain.NoteBook
import io.reactivex.Single
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by khashayar on 7/29/18.
 */
interface ApiServices {
    @GET("/notebook")
    fun fetchAllNotes() : Single<List<NoteBook>>
}