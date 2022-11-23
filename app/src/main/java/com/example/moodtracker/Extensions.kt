package com.example.moodtracker

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MyApp.Companion.moodJsonString
import com.example.moodtracker.MyApp.Companion.moodSharedPref
import com.google.gson.Gson

//Object ->Json, Json ->Preference..................................................................
fun Activity.objectToJson(moodObject: Mood): String {

    moodJsonString = Gson().toJson(moodObject)

    return moodJsonString
}

fun Activity.jsonToPreference(context: Context, moodJsonString: String, key: String) {
    moodSharedPref = context.getSharedPreferences(MyApp.FILE_NAME, AppCompatActivity.MODE_PRIVATE)
    val moodSharedPrefEdit = moodSharedPref.edit()

    moodSharedPrefEdit.putString(key, moodJsonString).apply()
}

//Object ->Preference...............................................................................
fun Activity.objectToPreference(context: Context,moodObject: Mood, key: String) {

    moodJsonString = objectToJson(moodObject)
    jsonToPreference(context,moodJsonString, key)


}

//Preference ->Json. Json ->Object..................................................................
fun Activity.preferenceToJson(context: Context,key: String): String {
    moodSharedPref = context.getSharedPreferences(MyApp.FILE_NAME, AppCompatActivity.MODE_PRIVATE)

    moodJsonString= moodSharedPref.getString(key,"").toString()

return moodJsonString
}
fun Activity.jsonToObject(moodJsonString: String): Mood {

    return Gson().fromJson(moodJsonString, Mood::class.java)
}
//Preference -> Object..............................................................................
fun Activity.preferenceToObject(context: Context,key: String):Mood{
    moodJsonString = preferenceToJson(context,key)

    return jsonToObject(moodJsonString)
}

//Alarm Triggering..................................................................................
fun Activity.triggerAlarm() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val saveMoodRequestCode = 1001
    val intent = Intent(this, SaveReceiver::class.java)
    intent.action = "FOO"


    val alarmStartDelay = 5L
    val alarmIntervalInMillis = 10_000L
    val alarmManagerTriggerTimeInMillis = System.currentTimeMillis() + alarmStartDelay * 1_000L
    val pendingIntent = PendingIntent.getBroadcast(
        this,
        saveMoodRequestCode,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        alarmManagerTriggerTimeInMillis,
        alarmIntervalInMillis,
        pendingIntent
    )

    Toast.makeText(this, "Daily mood save broadcast sent", Toast.LENGTH_LONG).show()
}