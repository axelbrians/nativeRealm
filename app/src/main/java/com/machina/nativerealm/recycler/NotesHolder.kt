package com.machina.nativerealm.recycler

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.machina.nativerealm.model.NotesSchema
import com.machina.nativerealm.R
import com.machina.nativerealm.customInterface.OnClickNotes
import kotlin.properties.Delegates

class NotesHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var notesCard = view.findViewById<CardView>(R.id.notesCard)
    private var notesTitle = view.findViewById<TextView>(R.id.notesTitleTV)
    private var notesNote = view.findViewById<TextView>(R.id.notesTV)

    fun bind(notes: NotesSchema, listener: OnClickNotes?){
        if(notes.title.length > 37){
            notesTitle.text = notes.title.substring(0, 37).plus("...")
        }else{
            notesTitle.text = notes.title
        }

        if(notes.note.length > 282) {
            notesNote.text = notes.note.substring(0, 282).plus("...")
        }else{
            notesNote.text = notes.note
        }

        notesCard.setOnClickListener {
            listener?.onClickNotes(it, notes)
        }
    }

}