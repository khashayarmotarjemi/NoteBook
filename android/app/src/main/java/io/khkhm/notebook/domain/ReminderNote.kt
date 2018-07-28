package io.khkhm.notebook.domain

import java.io.Serializable
import java.util.*

/**
 * Created by khashayar on 7/17/18.
 */
data class ReminderNote(val dueDate: Date,
                        override var text: String,
                        override var title: String,
                        override val date: Date = Date(),
                        override var color: Color) : Note(text, title, date, color), Serializable
