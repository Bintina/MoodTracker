package com.example.moodtracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
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
import com.example.moodtracker.MyApp.Companion.todayMood
import com.example.moodtracker.MyApp.Companion.twoDaysAgoMood
import com.example.moodtracker.MyApp.Companion.yesterdayMood

class SaveReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        shuffleSavedMoods(context)
    }

    private fun shuffleSavedMoods(context: Context): Context {

        var day0String = preferenceToJson(context, CURRENT_MOOD)
        var day1String = preferenceToJson(context, YESTERDAY_MOOD)
        var day2String = preferenceToJson(context, TWO_DAYS_AGO_MOOD)
        var day3String = preferenceToJson(context, THREE_DAYS_AGO_MOOD)
        var day4String = preferenceToJson(context, FOUR_DAYS_AGO_MOOD)
        var day5String = preferenceToJson(context, FIVE_DAYS_AGO_MOOD)
        var day6String = preferenceToJson(context, SIX_DAYS_AGO_MOOD)
        var day7String = preferenceToJson(context, SEVEN_DAYS_AGO_MOOD)

        day7String = day6String
        day6String = day5String
        day5String = day4String
        day4String = day3String
        day3String = day2String
        day2String = day1String
        day1String = day0String

        val defaultMoodString = objectToJson(defaultMood).toString()
        day0String = defaultMoodString

        jsonToPreference(context, day0String, CURRENT_MOOD)
        jsonToPreference(context, day1String, YESTERDAY_MOOD)
        jsonToPreference(context, day2String, TWO_DAYS_AGO_MOOD)
        jsonToPreference(context, day3String, THREE_DAYS_AGO_MOOD)
        jsonToPreference(context, day4String, FOUR_DAYS_AGO_MOOD)
        jsonToPreference(context, day5String, FIVE_DAYS_AGO_MOOD)
        jsonToPreference(context, day6String, SIX_DAYS_AGO_MOOD)
        jsonToPreference(context, day7String, SEVEN_DAYS_AGO_MOOD)

        initialiseHistoryVariables(context)

        return context
    }
}