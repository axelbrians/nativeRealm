package com.machina.nativerealm

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.machina.nativerealm.model.NotesSchema
import io.realm.Realm
import io.realm.kotlin.createObject

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

//    launch editItem onDestroy
    override fun onDestroy() {
        super.onDestroy()
        val titleText = titleForm.text.toString()
        val noteText = noteForm.text.toString()

        if(titleText.isNotEmpty() || noteText.isNotEmpty()) {
            editItem(titleText, noteText)
        }
        Log.d("debugging", "onDestroy add note")
    }



    //  handle edit item in realm record
    private fun editItem(titleText: String, noteText: String){
        val id = intent.getIntExtra("EXTRA_ID", 0)
        realm.executeTransaction{
            val newNote = realm.where(NotesSchema::class.java).equalTo("id", id).findFirst()
            if (newNote != null) {
                newNote.title = titleText
                newNote.note = noteText
            }
            Log.d(null, "edited item with id: $id")
        }
    }
    //    set reference for each form
    private fun setViewReference(){
        titleForm = findViewById(R.id.titleForm)
        noteForm = findViewById(R.id.noteForm)

        titleForm.setText(intent.getStringExtra("EXTRA_TITLE"))
        noteForm.setText(intent.getStringExtra("EXTRA_NOTE"))
    }
}
