package com.machina.nativerealm

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var floatBtn: FloatingActionButton
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var realm: Realm
    private lateinit var entries: RealmResults<NotesSchema>
    private lateinit var confirmDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.listNotesToolbar))

        val notesRV: RecyclerView = findViewById(R.id.notesRV)

        setRealm()
        notesRV.apply{
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = notesAdapter
            itemAnimator = DefaultItemAnimator()
        }
        setViewReference()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

//    function to inflate item in toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list_notes, menu)
        return true
    }

//    handle action item in toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.action_deleteAll -> {
//                create a confirmation dialog when pressed
                confirmDialog = AlertDialog.Builder(this).create()
                confirmDialog.apply {
                    setTitle("Delete all notes?")

//                  confirming the action
                    setButton(DialogInterface.BUTTON_POSITIVE,"Delete all") {
//                        DialogInterface.OnClickListener
                            dialog, id ->
                            clearItem()
                            refresh()
                    }

//                  cancel the action
                    setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") {
//                        DialogInterface.OnClickListener
                            dialog, id ->
                            dialog.dismiss()
                    }
                }
//              show the dialog that created
                confirmDialog.show()
                true
            } //end of action_deleteAll

            else -> super.onOptionsItemSelected(item)
        }
    }

//    initiate btn reference and clickListener
    private fun setViewReference(){
        floatBtn = findViewById(R.id.addNotesBtn)
        floatBtn.setOnClickListener(this)
    }

//    realm transaction to delete all records
    private fun clearItem(){
        realm.executeTransaction{
            val query = realm.where<NotesSchema>().findAll()
            query.deleteAllFromRealm()
        }
    }

//    method to refresh dataRecord in recyclerView
    private fun refresh(){
        val query = realm.where<NotesSchema>().findAll()
        this.notesAdapter.refresh(query)
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this, AddNotesActivity::class.java))
    }

    private fun setRealm(){
        realm = Realm.getDefaultInstance()
        entries = realm.where<NotesSchema>().findAll()
        entries.addChangeListener {
            query -> notesAdapter.refresh(query)
        }
        notesAdapter = NotesAdapter(entries)
    }
}