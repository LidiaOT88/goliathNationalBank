package com.example.goliathnationalbank.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.goliathnationalbank.R
import com.example.goliathnationalbank.other.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_host_activity)
    }

    override fun onDestroy() {
        deleteDatabaseFile(this, Constants.DATABASE_NAME)
        super.onDestroy()
    }

    fun deleteDatabaseFile(context: Context, databaseName: String) {
        val databases = File(context.applicationInfo.dataDir.toString() + "/databases")
        val db = File(databases, databaseName)
        if (db.delete()) println("Database deleted") else println("Failed to delete database")
        val journal = File(databases, "$databaseName-journal")
        if (journal.exists()) {
            if (journal.delete()) println("Database journal deleted") else println("Failed to delete database journal")
        }
    }

}