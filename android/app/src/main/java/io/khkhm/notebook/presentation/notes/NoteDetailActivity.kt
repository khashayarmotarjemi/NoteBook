package io.khkhm.notebook.presentation.notes

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import io.khkhm.notebook.R
import io.khkhm.notebook.data.BaseResponse
import io.khkhm.notebook.data.NoteRepository
import io.khkhm.notebook.domain.Color
import io.khkhm.notebook.domain.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note_detail.*
import timber.log.Timber

class NoteDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val note = intent.extras[EXTRA_NOTE] as Note

/*
        note_detail_note_color.setBackgroundColor(resources.getColor())
*/

        when (note.color) {
            Color.BLUE ->
                note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.blue))

            Color.RED ->
                note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.red))

            Color.GREEN ->
                note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.green))

            Color.PURPLE ->
                note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.purple))

            Color.YELLOW ->
                note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.yellow))

        }

        note_detail_note_color.setOnClickListener {
            val colors = arrayOf<CharSequence>("blue", "red", "green", "purple", "yellow")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pick a color!")
            builder.setItems(colors, DialogInterface.OnClickListener { dialogInterface, i ->
                when (i) {
                    0 -> {
                        note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.blue))
                        NoteRepository.updateNote(note, newColor = Color.BLUE)
                    }

                    1 -> {
                        note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.red))
                        NoteRepository.updateNote(note, newColor = Color.RED)
                    }

                    2 -> {
                        note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.green))
                        NoteRepository.updateNote(note, newColor = Color.GREEN)
                    }

                    3 -> {
                        note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.purple))
                        NoteRepository.updateNote(note, newColor = Color.PURPLE)
                    }

                    4 -> {
                        note_detail_note_color.setCardBackgroundColor(resources.getColor(R.color.yellow))
                        NoteRepository.updateNote(note, newColor = Color.YELLOW)
                    }
                }
            })
            builder.show()
        }

        note_detail_note_date.text = note.date.toString()

        note_detail_note_title.setText(note.title)
        note_detail_note_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                NoteRepository.updateNote(note, newText = p0.toString())
            }
        })

        note_detail_remove_note.setOnClickListener {
            NoteRepository.removeNote(note).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<BaseResponse>() {

                        override fun onSuccess(baseResponse: BaseResponse) {
                            NavUtils.navigateUpFromSameTask(this@NoteDetailActivity)

                        }

                        override fun onError(e: Throwable) {
                            Timber.e(e)
                        }
                    })
        }

        note_detail_text.setText(note.text)

        note_detail_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                NoteRepository.updateNote(note, newText = p0.toString())
            }
        })



    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    override fun onBackPressed() {
/*
        super.onBackPressed()
*/
        Timber.e("it worked")
        NavUtils.navigateUpFromSameTask(this@NoteDetailActivity)

    }


    /**
     * Dispatch onPause() to fragments.
     */
    override fun onPause() {
        super.onPause()
        super.onStop()
        NoteRepository.syncNoteBooksAndRun {}
    }

    companion object {
        private val EXTRA_NOTE = "extra.note.io"

        fun newIntent(packageContext: Context, note: Note): Intent {
            val intent = Intent(packageContext, NoteDetailActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }
    }
}
