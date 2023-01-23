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
import android.widget.LinearLayout
import android.widget.TextView
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
import java.util.*

//..................................................................................................
//Methods that convert Objects to Json and  Json to Shared Preference...............................
fun objectToJson(moodObject: Mood): String {
    moodJsonString = Gson().toJson(moodObject)

    return moodJsonString
}

fun jsonToPreference(context: Context, moodJsonString: String, key: String) {
    moodSharedPref = context.getSharedPreferences(MyApp.FILE_NAME, MODE_PRIVATE)
    val moodSharedPrefEdit = moodSharedPref.edit()

    moodSharedPrefEdit.putString(key, moodJsonString).apply()
}

//Method that converts Object to Shared Preference..................................................
fun Activity.objectToPreference(context: Context, moodObject: Mood, key: String) {
    moodJsonString = objectToJson(moodObject)

    jsonToPreference(context, moodJsonString, key)
}

//Methods converting Preference to Json and Json to Objects.........................................
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

//Method converting Shared Preferences to Objects...................................................
fun preferenceToObject(context: Context, key: String): Mood {
    moodJsonString = preferenceToJson(context, key)
    var moodObject = defaultMood

    moodObject = jsonToObject(moodJsonString)

    return moodObject
}

//Initializing Mood Objects.........................................................................
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

//..................................................................................................
//Alarm Triggering..................................................................................
fun Activity.triggerAlarm() {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val saveMoodRequestCode = 1001
    val intent = Intent(this, SaveReceiver::class.java)
    intent.action = "FOO"

    //get calendar instance
    val alarmTime = Calendar.getInstance()
    alarmTime.set(Calendar.HOUR_OF_DAY, 24)
    alarmTime.set(Calendar.MINUTE, 0)
    alarmTime.set(Calendar.SECOND, 0)
    alarmTime.set(Calendar.MILLISECOND, 0)

    if (System.currentTimeMillis() > alarmTime.timeInMillis) {
        alarmTime.timeInMillis =
            alarmTime.timeInMillis + 24 * 60 * 60 * 1000// Okay, then tomorrow ...
    }

    val alarmIntervalInMillis = AlarmManager.INTERVAL_DAY //AlarmManager,INTERVAL_DAY
    val pendingIntent = PendingIntent.getBroadcast(
        this,
        saveMoodRequestCode,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        alarmTime.timeInMillis,
        alarmIntervalInMillis,
        pendingIntent
    )
}

//..................................................................................................
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

//History View methods..............................................................................
//History bar heights...............................................................................
fun Activity.setHistoryBarHeights(view: View, layout: LinearLayout) {
    val metrics = resources.displayMetrics
    val densityDpi = metrics.densityDpi

    val height = metrics.heightPixels.toInt()
    val heightdp = height / (densityDpi / 160).toInt()
    val heightPerBar = ((heightdp * (densityDpi / 160)) - 200) / 7

    val params = view.layoutParams
    params.height = heightPerBar
}

//History bar colors................................................................................
fun Activity.setHistoryBarColors(view: View, moodObject: Mood) {
    view.setBackgroundColor(getColor(MyApp.arrayOfBackgrounds[moodObject.moodScore]))
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

//Show comments in history screen...................................................................
fun Activity.showCommentIcon(moodObject: Mood, view: TextView) {
    val moodComment = moodObject.moodComment
    val textView = view

    if (!moodComment.isNullOrEmpty()) {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_comment_black_48px,
            0
        )
        view.setOnClickListener {
            Toast.makeText(this, moodComment, Toast.LENGTH_LONG).show()
        }
    }
}


