package com.example.contentproviders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.contentproviders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getContacts()
    }

    fun getContacts() {
        val Contacts : MutableList<String> = ArrayList()
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do{
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) ?: 0)
                Log.d("getContacts", "name : " + name)
                Contacts.add(name)
            }while (cursor.moveToNext())
            cursor.close()
        }
        binding.autocomplete.setAdapter(ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1, Contacts))
        binding.autocomplete.threshold = 1


        binding.autocomplete.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "Selected Contact : " + parent?.getItemAtPosition(position), Toast.LENGTH_SHORT).show()
            }
        }
    }
}