package io.khkhm.notebook.domain

import io.khkhm.notebook.data.BaseResponse
import java.io.Serializable
import java.util.*

/**
 * Created by khashayar on 7/17/18.
 */
open class Content(open var title: String,
                   open val date: Date = Date(),
                   open var color: Color) : Serializable, BaseResponse() {
    val id: String = UUID.randomUUID().toString()
}