package com.machina.nativerealm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NotesSchema(
        @PrimaryKey
        var id: Int,
        var title: String,
        var note: String
) : RealmObject() {
    constructor() : this(0, "title", "content")
}