package com.machina.nativerealm

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var notesTitle = view.findViewById<TextView>(R.id.notesTitleTV)
    private var notesNote = view.findViewById<TextView>(R.id.notesTV)

    fun bind(notes: NotesSchema){
        notesTitle.text = notes.title
        notesNote.text = notes.note
    }

}