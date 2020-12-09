package com.machina.nativerealm

import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.machina.nativerealm.customInterface.OnClickNotes
import com.machina.nativerealm.model.NotesSchema
import com.machina.nativerealm.recycler.GridSpacingItemDecoration
import com.machina.nativerealm.recycler.NotesAdapter
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where


class MainActivity : AppCompatActivity(), View.OnClickListener, OnClickNotes {

    private lateinit var floatBtn: FloatingActionButton
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var realm: Realm
    private lateinit var entries: RealmResults<NotesSchema>
    private lateinit var confirmDialog: AlertDialog
    private lateinit var notesRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

//        // Enable Activity Transitions. Optionally enable Activity transitions in your
//        // theme with <item name=”android:windowActivityTransitions”>true</item>.
//        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//
//        // Attach a callback used to capture the shared elements from this Activity to be used
//        // by the container transform transition
//        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
//
//        // Keep system bars (status bar, navigation bar) persistent throughout the transition.
//        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.listNotesToolbar))

        notesRV = findViewById(R.id.notesRV)
        setRealm()
        setAdapter()


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
                    setButton(DialogInterface.BUTTON_POSITIVE, "Delete all") {
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

//    listen to floating action btn (fab)
    override fun onClick(v: View?) {
//        val intent = Intent(this, AddNotesActivity::class.java)
//        val options = ActivityOptions.makeSceneTransitionAnimation(
//            this,
//            findViewById(R.id.start_view),
//            "shared_element_container" // The transition name to be matched in Activity B.
//        )
//
//        startActivity(intent, options.toBundle())

        startActivity(Intent(this, AddNotesActivity::class.java))
    }
//    listen to recycler view item click
    override fun onClickNotes(view: View, notes: NotesSchema) {
        val intent = Intent(this, EditNotesActivity::class.java)
        intent.putExtra("EXTRA_ID", notes.id)
        intent.putExtra("EXTRA_TITLE", notes.title)
        intent.putExtra("EXTRA_NOTE", notes.note)

        startActivity(intent)
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

    //    initiate btn reference and clickListener
    private fun setViewReference(){
        floatBtn = findViewById(R.id.addNotesBtn)
        floatBtn.setOnClickListener(this)
    }

    private fun setRealm(){
        realm = Realm.getDefaultInstance()
        entries = realm.where<NotesSchema>().findAll()
        entries.addChangeListener {
            query -> notesAdapter.refresh(query)
        }
    }

    private fun setAdapter(){

        notesAdapter = NotesAdapter(entries)
        notesAdapter.onClickNotes = this

        val spacing = 20
        val staggeredGrid = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        staggeredGrid.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        notesRV.apply{
            layoutManager = staggeredGrid
            adapter = notesAdapter
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(GridSpacingItemDecoration(spacing))
        }
    }
}