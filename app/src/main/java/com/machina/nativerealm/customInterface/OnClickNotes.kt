package com.machina.nativerealm.customInterface

import android.view.View
import com.machina.nativerealm.model.NotesSchema

interface OnClickNotes {
    fun onClickNotes(view: View, notes: NotesSchema)
}