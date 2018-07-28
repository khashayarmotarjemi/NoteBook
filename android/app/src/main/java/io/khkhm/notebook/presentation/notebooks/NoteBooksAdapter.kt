package io.khkhm.notebook.presentation.notebooks

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.khkhm.notebook.R
import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.NoteBook
import kotlinx.android.synthetic.main.list_item_notebook.view.*


class NoteBookAdapter(private val notebooks: ArrayList<NoteBook>,
                      private val context: Context,
                      private val itemListener: NoteBookItemListener) : RecyclerView.Adapter<NoteBookAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return notebooks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_notebook, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notebooks[position])

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(notebook: NoteBook) = with(itemView) {

            when (notebook.color) {
                Color.BLUE ->
                    notebook_card_background.setBackgroundColor(resources.getColor(R.color.blue))

                Color.RED ->
                    notebook_card_background.setBackgroundColor(resources.getColor(R.color.red))

                Color.GREEN ->
                    notebook_card_background.setBackgroundColor(resources.getColor(R.color.green))

                Color.PURPLE ->
                    notebook_card_background.setBackgroundColor(resources.getColor(R.color.purple))

                Color.YELLOW ->
                    notebook_card_background.setBackgroundColor(resources.getColor(R.color.yellow))

            }

            this.notebook_item_name.text = notebook.name

            this.notebook_item_remove.setOnClickListener {
                itemListener.onItemRemoveClick(notebook)
                notebooks.remove(notebook)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, itemCount)
            }

            this.setOnClickListener {
                itemListener.onItemClick(notebook)
            }

            this.setOnLongClickListener {
                itemListener.onItemLongClick(notebook)
                true
            }
        }
    }
}


interface NoteBookItemListener {
    fun onItemClick(notebook: NoteBook)
    fun onItemLongClick(notebook: NoteBook)
    fun onItemRemoveClick(notebook: NoteBook)
}