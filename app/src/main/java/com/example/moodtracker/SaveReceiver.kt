package com.example.moodtracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class SaveReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val d = Log.d(
            "SaveAlarmLog",
            "Save Receiver Broadcast Recieved"
        )
    //shuffleSavedMoods(context)
    }

    fun shuffleSavedMoods(context: Context) {

        Log.d("Broadcast Log",
        "Broadcast received")

    }
}