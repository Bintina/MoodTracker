package com.example.moodtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MyApp.Companion.currentMood
import com.example.moodtracker.MyApp.Companion.fiveDaysAgoMood
import com.example.moodtracker.MyApp.Companion.fourDaysAgoMood
import com.example.moodtracker.MyApp.Companion.sevenDaysAgoMood
import com.example.moodtracker.MyApp.Companion.sixDaysAgoMood
import com.example.moodtracker.MyApp.Companion.threeDaysAgoMood
import com.example.moodtracker.MyApp.Companion.twoDaysAgoMood
import com.example.moodtracker.MyApp.Companion.yesterdayMood
import com.example.moodtracker.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    lateinit var historyBinding: ActivityHistoryBinding

    /*  lateinit var screenWidth: Int
      lateinit var widthIncrement: Int*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(historyBinding.root)

        //Extract screen width
        /* screenWidth = resources.displayMetrics.widthPixels
         widthIncrement = screenWidth/5
 */

        //Sett historyBar colors
        setHistoryBarColors(historyBinding.yesterday, yesterdayMood)
        setHistoryBarColors(historyBinding.twoDays, twoDaysAgoMood)
        setHistoryBarColors(historyBinding.threeDays, threeDaysAgoMood)
        setHistoryBarColors(historyBinding.fourDays, fourDaysAgoMood)
        setHistoryBarColors(historyBinding.fiveDays, fiveDaysAgoMood)
        setHistoryBarColors(historyBinding.sixDays, sixDaysAgoMood)
        setHistoryBarColors(historyBinding.sevenDays, sevenDaysAgoMood)

    }

    fun setHistoryBarWidth(moodObject: Mood) {

        val historyScore = moodObject.moodScore + 1

        /* val params = layoutParams
         layoutParams.width = widthIncrement * historyScore
         layoutParams = params*/
    }


    fun showCommentIcon() {

    }
}