package com.machina.nativerealm

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.machina.nativerealm.model.NotesSchema
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class EditNotesActivity : AppCompatActivity() {

    private lateinit var titleForm: EditText
    private lateinit var noteForm: EditText
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notes)

//      setUp toolbar
        setSupportActionBar(findViewById(R.id.addNotesToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        realm = Realm.getDefaultInstance()
        setViewReference()
    }

    //    launch addItem() when paused, is not perfect yet
    override fun onPause() {
        super.onPause()
        Log.d("debugging", "onPause add note")
    }

    override fun onDestroy() {
        super.onDestroy()
        val titleText = titleForm.text.toString()
        val noteText = noteForm.text.toString()

        if(titleText.isNotEmpty() || noteText.isNotEmpty()) {
            addItem(titleText, noteText)
        }
        Log.d("debugging", "onDestroy add note")
    }



    //  handle adding item to realm db record
    private fun addItem(titleText: String, noteText: String){
        realm.executeTransaction{
            val newNote = realm.createObject<NotesSchema>()
            newNote.title = titleText
            newNote.note = noteText
            realm.copyToRealmOrUpdate(newNote)


            Log.d(null, "added item to realm")
        }
    }

    //    set reference for each form
    private fun setViewReference(){
        titleForm = findViewById(R.id.titleForm)
        noteForm = findViewById(R.id.noteForm)
    }
}
