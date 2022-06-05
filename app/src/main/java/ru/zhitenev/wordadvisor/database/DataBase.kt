package ru.zhitenev.wordadvisor.database

import android.content.Context
import android.database.Cursor
import android.util.Log
import java.util.*

class DataBase(private val context: Context) {
    // This is an example, remember to use a background thread in production.
    private val myDatabase = ActsDbHelper(context).readableDatabase
    private var words = TreeMap<Int, Vector<String>>()

    fun get_words(count : Int) : Vector<String>? {
        if(!words.containsKey(count)) {
            val vec = Vector<String>()
            val query : String = if(count == -1) {
                "SELECT word,size FROM words"
            } else {
                String.format("SELECT word,size FROM words WHERE size = %d", count)
            }
            var cursor : Cursor? = null
            try {
                cursor = myDatabase.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    do {
                        vec.addElement(cursor.getString(0))
                    } while (cursor.moveToNext())
                }
                words[count] = vec
            } catch (e: Exception) {
                Log.e("DataBase", e.toString())
            } finally {
                cursor?.close()
            }
        }

        return words.get(count)
    }

}