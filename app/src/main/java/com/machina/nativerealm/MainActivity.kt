package com.machina.nativerealm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var FloatBtn: FloatingActionButton
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var realm: Realm



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notesRV: RecyclerView = findViewById(R.id.notesRV)

        realm = Realm.getDefaultInstance()
        clearItem()
        val query = realm.where<NotesSchema>().findAll()
        notesAdapter = NotesAdapter(query)

       notesRV.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
            itemAnimator = DefaultItemAnimator()
        }

        FloatBtn = findViewById(R.id.addNotesBtn)
        FloatBtn.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        Log.d("debugging", "onresume main")
        val query = realm.where<NotesSchema>().findAll()
        this.notesAdapter.addNote(query)
    }


    override fun onClick(v: View?) {
        startActivity(Intent(this, AddNotesActivity::class.java))
    }
//
//    private fun addItem(titleText: String, noteText: String){
//        realm.executeTransaction{
//            // Get the current max id in the EntityName table
//            val id: Number? = realm.where<NotesSchema>().max("id")
//            // If id is null, set it to 1, else set increment it by 1
//            val nextId = if (id == null) 1 else id.toInt() + 1
//
//
//            val newNote = realm.createObject<NotesSchema>(nextId)
//            newNote.title = titleText
//            newNote.note = noteText
//            realm.copyToRealmOrUpdate(newNote)
//
//            val query = realm.where<NotesSchema>().findAll()
//            this.notesAdapter.addNote(query)
//        }
//    }

    private fun clearItem(){
        realm.executeTransaction{
            val query = realm.where<NotesSchema>().findAll()
            query.deleteAllFromRealm()
        }
    }
}