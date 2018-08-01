package io.khkhm.notebook.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import javax.sql.StatementEvent

/**
 * Created by khashayar on 7/17/18.
 */
open class Note(open var text: String,
                var title: String,
                val date: Date = Date(),
                sColor: String,
                @SerializedName("_id") var id: String = "") : Serializable {
    var color = Color.valueOf(sColor)

    var notebookId : String = ""

}
