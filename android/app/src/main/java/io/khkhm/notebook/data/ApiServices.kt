package io.khkhm.notebook.data

import io.khkhm.notebook.domain.Note
import io.khkhm.notebook.domain.NoteBook
import io.reactivex.Single
import retrofit2.http.*


interface ApiServices {
    // get all notebooks
    @GET("/notebook")
    fun fetchAllNotes(): Single<List<NoteBook>>

    // create notebook
    @POST("/notebook/create")
    @FormUrlEncoded
    fun createNewNotebook(@Field("name") name: String,
                          @Field("color") color: String): Single<NoteBook>

    // create note
    @POST("/note/create/{notebookId}")
    @FormUrlEncoded
    fun createNewNoteInNotebook(@Path("notebookId") notebookId: String,
                                @Field("text") text: String,
                                @Field("title") title: String,
                                @Field("color") color: String): Single<Note>

    // update note
    @POST("/note/update/{notebookId}/{noteId}")
    @FormUrlEncoded
    fun updateNoteInNotebook(@Path("notebookId") notebookId: String,
                             @Path("noteId") noteId: String,
                             @Field("text") text: String?,
                             @Field("title") title: String?,
                             @Field("color") color: String?): Single<Note>

    // update notebook
    @POST("/notebook/update/{notebookId}")
    @FormUrlEncoded
    fun updateNotebook(@Path("notebookId") notebookId: String,
                       @Field("name") name: String?,
                       @Field("color") color: String?): Single<NoteBook>

    // remove notebook
    @GET("/notebook/remove/{notebookId}")
    fun removeNotebook(@Path("notebookId") notebookId: String): Single<BaseResponse>

    // remove note
    @GET("/note/remove/{notebookId}/{noteId}")
    fun removeNote(@Path("notebookId") notebookId: String,
                   @Path("noteId") noteId: String): Single<BaseResponse>


}