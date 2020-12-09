package com.machina.nativerealm.customInterface

import android.view.View
import androidx.cardview.widget.CardView
import com.machina.nativerealm.model.NotesSchema

interface OnClickNotes {
    fun onClickNotes(view: View, notes: NotesSchema)
}