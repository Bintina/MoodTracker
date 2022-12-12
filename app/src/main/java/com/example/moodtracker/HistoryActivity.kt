package com.example.moodtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(historyBinding.root)

        
        //Sett historyBar colors
        setHistoryBarColors(historyBinding.yesterday, yesterdayMood)
        setHistoryBarColors(historyBinding.twoDays, twoDaysAgoMood)
        setHistoryBarColors(historyBinding.threeDays, threeDaysAgoMood)
        setHistoryBarColors(historyBinding.fourDays, fourDaysAgoMood)
        setHistoryBarColors(historyBinding.fiveDays, fiveDaysAgoMood)
        setHistoryBarColors(historyBinding.sixDays, sixDaysAgoMood)
        setHistoryBarColors(historyBinding.sevenDays, sevenDaysAgoMood)

//        /Set history bar widths
        setHistoryBarWidth(historyBinding.yesterday, yesterdayMood)
        setHistoryBarWidth(historyBinding.twoDays, twoDaysAgoMood)
        setHistoryBarWidth(historyBinding.threeDays, threeDaysAgoMood)
        setHistoryBarWidth(historyBinding.fourDays, fourDaysAgoMood)
        setHistoryBarWidth(historyBinding.fiveDays, fiveDaysAgoMood)
        setHistoryBarWidth(historyBinding.sixDays, sixDaysAgoMood)
        setHistoryBarWidth(historyBinding.sevenDays, sevenDaysAgoMood)


    }


    fun showCommentIcon() {

    }
}