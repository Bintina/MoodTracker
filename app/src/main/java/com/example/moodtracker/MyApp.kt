package com.example.moodtracker

import android.app.Application
import android.content.SharedPreferences
import android.view.GestureDetector
import android.view.View
import android.widget.ImageView

class MyApp: Application() {


    companion object{



        //View variables
        lateinit var background: View
        lateinit var moodImage: ImageView

        //Image and Background Arrays
        val arrayOfBackgrounds = arrayOf<Int>(
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow
        ).toIntArray()
        val arrayOfImages = arrayOf<Int>(
            R.drawable.smiley_sad,
            R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,
            R.drawable.smiley_happy,
            R.drawable.smiley_super_happy
        ).toIntArray()

        // SharedPreference Variables
        lateinit var currentMood: Mood
        lateinit var moodSharedPref: SharedPreferences
        lateinit var moodJsonString: String
        const val FILE_NAME = "Mood Preferences"
        const val CURRENT_MOOD = "Current Mood"
        const val YESTERDAY_MOOD = "Yesterdays Mood"
        const val TWO_DAYS_AGO_MOOD = "Two Days Ago Mood"
        const val THREE_DAYS_AGO_MOOD = "Three Days Ago Mood"
        const val FOUR_DAYS_AGO_MOOD = "Four Days Ago Mood"
        const val FIVE_DAYS_AGO_MOOD = "Five Days Ago Mood"
        const val SIX_DAYS_AGO_MOOD = "Six Days Ago Mood"
        const val SEVEN_DAYS_AGO_MOOD = "Seven Days Ago Mood"

        //Mood object variables
        val defaultMood:Mood = Mood()
        var todayMood:Mood = Mood()
        var yesterdayMood: Mood = Mood()
        var twoDaysAgoMood: Mood = Mood()
        var threeDaysAgoMood: Mood = Mood()
        var fourDaysAgoMood: Mood = Mood()
        var fiveDaysAgoMood: Mood = Mood()
        var sixDaysAgoMood: Mood = Mood()
        var sevenDaysAgoMood: Mood = Mood()
    }
}