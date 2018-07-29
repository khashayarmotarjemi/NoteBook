package io.khkhm.notebook.data

import android.content.Context
import io.khkhm.notebook.domain.NoteBook
import io.reactivex.Single

/**
 * Created by khashayar on 7/29/18.
 */
object Repository {
    var apiServices: ApiServices? = null
    private val apiNotInitError = "Repository haven't been initialized"

    fun init(context: Context) {
        apiServices =
                ApiClient.getClient(context.applicationContext)
                        .create(ApiServices::class.java)

    }

    fun getAllNotebooks(): Single<List<NoteBook>> {
        if (checkApiServicesInitialized()) {
            return apiServices!!.fetchAllNotes()
        } else {
            throw IllegalStateException(apiNotInitError)
        }
    }

    private inline fun checkApiServicesInitialized(): Boolean {
        if (apiServices == null) {
            return false
        }
        return true
    }

}