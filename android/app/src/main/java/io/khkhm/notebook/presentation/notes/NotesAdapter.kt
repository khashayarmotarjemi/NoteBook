package io.khkhm.notebook.presentation.notes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.khkhm.notebook.R
import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.Note
import kotlinx.android.synthetic.main.list_item_note.view.*


class NotesAdapter(private val notes: ArrayList<Note>,
                   private val context: Context,
                   private val itemListener: NoteItemListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(note: Note) = with(itemView) {

            when (note.color) {
                Color.BLUE ->
                    itemView.note_item_background_card.setCardBackgroundColor(resources.getColor(R.color.blue))

                Color.RED ->
                    itemView.note_item_background_card.setCardBackgroundColor(resources.getColor(R.color.red))

                Color.GREEN ->
                    itemView.note_item_background_card.setCardBackgroundColor(resources.getColor(R.color.green))

                Color.PURPLE ->
                    itemView.note_item_background_card.setCardBackgroundColor(resources.getColor(R.color.purple))

                Color.YELLOW ->
                    itemView.note_item_background_card.setCardBackgroundColor(resources.getColor(R.color.yellow))

            }

            this.note_item_title.text = note.title
            if (note.text.length > 30) {
                this.note_item_sample_text.text = note.text.substring(30)
            } else {
                this.note_item_sample_text.text = note.text

            }
            this.note_item_date.text = note.date.toString()

            this.setOnClickListener {
                itemListener.onItemClick(note)
            }

            this.setOnLongClickListener {
                itemListener.onItemLongClick(note)
                true
            }
        }
    }
}


interface NoteItemListener {
    fun onItemClick(note: Note)
    fun onItemLongClick(note: Note)
}