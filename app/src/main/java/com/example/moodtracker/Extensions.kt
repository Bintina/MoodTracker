package com.example.moodtracker

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MyApp.Companion.CURRENT_MOOD
import com.example.moodtracker.MyApp.Companion.FIVE_DAYS_AGO_MOOD
import com.example.moodtracker.MyApp.Companion.FOUR_DAYS_AGO_MOOD
import com.example.moodtracker.MyApp.Companion.SEVEN_DAYS_AGO_MOOD
import com.example.moodtracker.MyApp.Companion.SIX_DAYS_AGO_MOOD
import com.example.moodtracker.MyApp.Companion.THREE_DAYS_AGO_MOOD
import com.example.moodtracker.MyApp.Companion.TWO_DAYS_AGO_MOOD
import com.example.moodtracker.MyApp.Companion.YESTERDAY_MOOD
import com.example.moodtracker.MyApp.Companion.currentMood
import com.example.moodtracker.MyApp.Companion.defaultMood
import com.example.moodtracker.MyApp.Companion.fiveDaysAgoMood
import com.example.moodtracker.MyApp.Companion.fourDaysAgoMood
import com.example.moodtracker.MyApp.Companion.moodJsonString
import com.example.moodtracker.MyApp.Companion.moodSharedPref
import com.example.moodtracker.MyApp.Companion.sevenDaysAgoMood
import com.example.moodtracker.MyApp.Companion.sixDaysAgoMood
import com.example.moodtracker.MyApp.Companion.threeDaysAgoMood
import com.example.moodtracker.MyApp.Companion.todayMood
import com.example.moodtracker.MyApp.Companion.twoDaysAgoMood
import com.example.moodtracker.MyApp.Companion.yesterdayMood
import com.google.gson.Gson

//Object ->Json, Json ->Preference..................................................................
fun objectToJson(moodObject: Mood): String {

    moodJsonString = Gson().toJson(moodObject)

    return moodJsonString
}

fun jsonToPreference(context: Context, moodJsonString: String, key: String) {
    moodSharedPref = context.getSharedPreferences(MyApp.FILE_NAME, MODE_PRIVATE)
    val moodSharedPrefEdit = moodSharedPref.edit()

    moodSharedPrefEdit.putString(key, moodJsonString).apply()
}

//Object ->Preference...............................................................................
fun Activity.objectToPreference(context: Context, moodObject: Mood, key: String) {

    moodJsonString = objectToJson(moodObject)
    jsonToPreference(context, moodJsonString, key)


}

//Preference ->Json. Json ->Object..................................................................
fun preferenceToJson(context: Context, key: String): String {
    moodSharedPref = context.getSharedPreferences(MyApp.FILE_NAME, AppCompatActivity.MODE_PRIVATE)


    moodJsonString = moodSharedPref.getString(key, "").toString()

    if (moodJsonString.isNullOrEmpty()) {
        moodJsonString = objectToJson(defaultMood)
    }

    return moodJsonString
}

fun jsonToObject(moodJsonString: String): Mood {

    return Gson().fromJson(moodJsonString, Mood::class.java)
}

//Preference -> Object..............................................................................
fun preferenceToObject(context: Context, key: String): Mood {
    moodJsonString = preferenceToJson(context, key)
    var moodObject = defaultMood

    moodObject = jsonToObject(moodJsonString)

    return moodObject
}
//initialitions
fun initialiseHistoryVariables(context: Context) {
    todayMood = preferenceToObject(context, CURRENT_MOOD)
    yesterdayMood = preferenceToObject(context, YESTERDAY_MOOD)
    twoDaysAgoMood = preferenceToObject(context, TWO_DAYS_AGO_MOOD)
    threeDaysAgoMood = preferenceToObject(context, THREE_DAYS_AGO_MOOD)
    fourDaysAgoMood = preferenceToObject(context, FOUR_DAYS_AGO_MOOD)
    fiveDaysAgoMood = preferenceToObject(context, FIVE_DAYS_AGO_MOOD)
    sixDaysAgoMood = preferenceToObject(context, SIX_DAYS_AGO_MOOD)
    sevenDaysAgoMood = preferenceToObject(context, SEVEN_DAYS_AGO_MOOD)
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

//Dialog Builder....................................................................................
fun Activity.buildDialog() {
    val builder = AlertDialog.Builder(this)
    val inflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.dialog_layout, null)
    val moodReasonEt = dialogLayout.findViewById<EditText>(R.id.mood_reason_et)

    with(builder) {
        setTitle("Make a note")
        setPositiveButton("Save") { dialog, which ->
            currentMood.moodComment = moodReasonEt.text.toString()
            Log.d("EtSave", "Comment saved? ${currentMood.moodComment}")
            objectToPreference(context, currentMood, CURRENT_MOOD)
        }
        setNegativeButton("Cancel") { dialog, which ->
            Log.d("Main", "Negative button clicked")
        }
        setView(dialogLayout)
        show()
    }
}

//..................................................................................................
//Navigation........................................................................................
fun Activity.goToHistory() {
    val intent = Intent(this, HistoryActivity::class.java)
    startActivity(intent)

}

//History View methods..............................................................................
fun Activity.setHistoryBarColors(view: View, moodObject: Mood) {
    view.setBackgroundColor(getColor(MyApp.arrayOfBackgrounds[moodObject.moodScore]))
}

//Reset Home Screen to default mood.................................................................
fun setMood() {
    MyApp.moodImage.setImageResource(MyApp.arrayOfImages[currentMood.moodScore])
    MyApp.background.setBackgroundColor((MyApp.arrayOfBackgrounds[currentMood.moodScore]))
}
//History bar widths................................................................................
fun Activity.setHistoryBarWidth(view: View, moodObject: Mood) {

    //Extract screen width
    val screenWidth = resources.displayMetrics.widthPixels.toInt()
    val widthIncrement = screenWidth / 5

    val historyScore = moodObject.moodScore + 1

    val params = view.layoutParams
    params.width = widthIncrement * historyScore
}
