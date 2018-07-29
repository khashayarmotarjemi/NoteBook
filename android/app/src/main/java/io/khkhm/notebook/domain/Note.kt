package io.khkhm.notebook.domain

import io.khkhm.notebook.data.BaseResponse
import java.io.Serializable
import java.util.*

/**
 * Created by khashayar on 7/17/18.
 */
open class Note(open var text: String,
                override var title: String,
                override val date: Date = Date(),
                override var color: Color) : Content(title, date, color), Serializable
