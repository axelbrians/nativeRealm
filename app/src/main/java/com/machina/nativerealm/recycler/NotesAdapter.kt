package com.machina.nativerealm.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.machina.nativerealm.model.NotesSchema
import com.machina.nativerealm.R
import com.machina.nativerealm.customInterface.OnClickNotes
import io.realm.RealmResults

class NotesAdapter(
        private var notesList: RealmResults<NotesSchema>
) : RecyclerView.Adapter<NotesHolder>() {

    var onClickNotes: OnClickNotes? = null

    fun refresh(note: RealmResults<NotesSchema>) {
        this.notesList = note
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_holder, parent, false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(notesList[position]!!, onClickNotes)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}