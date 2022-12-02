package com.example.moodtracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.example.moodtracker.MyApp.Companion.sevenDaysAgoMood
import com.example.moodtracker.MyApp.Companion.sixDaysAgoMood
import com.example.moodtracker.MyApp.Companion.threeDaysAgoMood
import com.example.moodtracker.MyApp.Companion.twoDaysAgoMood
import com.example.moodtracker.MyApp.Companion.yesterdayMood

class SaveReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val d = Log.d(
            "SaveAlarmLog",
            "Save Receiver Broadcast Recieved"
        )
        //shuffleSavedMoods(context)
    }

    fun shuffleSavedMoods(context: Context): Context {

        Log.d(
            "Broadcast Log",
            "Broadcast received"
        )
        var todayJson = objectToJson(currentMood)
        var yesterdayJson = objectToJson(yesterdayMood)
        var day2Json = objectToJson(twoDaysAgoMood)
        var day3Json = objectToJson(threeDaysAgoMood)
        var day4Json = objectToJson(fourDaysAgoMood)
        var day5Json = objectToJson(fiveDaysAgoMood)
        var day6Json = objectToJson(sixDaysAgoMood)
        var day7Json = objectToJson(sevenDaysAgoMood)

        val defaultJson = objectToJson(defaultMood)

        day7Json = day6Json
        day6Json = day5Json
        day5Json = day4Json
        day4Json = day3Json
        day3Json = day2Json
        day2Json = yesterdayJson
        yesterdayJson = todayJson
        todayJson = defaultJson

        jsonToPreference(context, day7Json, SEVEN_DAYS_AGO_MOOD)
        jsonToPreference(context, day6Json, SIX_DAYS_AGO_MOOD)
        jsonToPreference(context, day5Json, FIVE_DAYS_AGO_MOOD)
        jsonToPreference(context, day4Json, FOUR_DAYS_AGO_MOOD)
        jsonToPreference(context, day3Json, THREE_DAYS_AGO_MOOD)
        jsonToPreference(context, day2Json, TWO_DAYS_AGO_MOOD)
        jsonToPreference(context, yesterdayJson, YESTERDAY_MOOD)
        jsonToPreference(context, todayJson, CURRENT_MOOD)

        return context
    }
}