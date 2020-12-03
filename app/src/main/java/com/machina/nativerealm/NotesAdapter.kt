package com.machina.nativerealm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults

class NotesAdapter(
        private var notesList: RealmResults<NotesSchema>
) : RecyclerView.Adapter<NotesHolder>() {

    fun addNote(note: RealmResults<NotesSchema>) {
        this.notesList = note
        notifyDataSetChanged()
    }

    fun removeItem(note: NotesSchema) {
        notifyDataSetChanged()
    }

    fun clear() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_holder, parent, false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(notesList[position]!!)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}