package com.example.moodtracker

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
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


        //Set history bar Heights
        setHistoryBarHeights(historyBinding.yesterday, historyBinding.historyLinear)
        setHistoryBarHeights(historyBinding.twoDays, historyBinding.historyLinear)
        setHistoryBarHeights(historyBinding.threeDays, historyBinding.historyLinear)
        setHistoryBarHeights(historyBinding.fourDays, historyBinding.historyLinear)
        setHistoryBarHeights(historyBinding.fiveDays, historyBinding.historyLinear)
        setHistoryBarHeights(historyBinding.sixDays, historyBinding.historyLinear)
        setHistoryBarHeights(historyBinding.sevenDays, historyBinding.historyLinear)

        //Set history bar colors
        setHistoryBarColors(historyBinding.yesterday, yesterdayMood)
        setHistoryBarColors(historyBinding.twoDays, twoDaysAgoMood)
        setHistoryBarColors(historyBinding.threeDays, threeDaysAgoMood)
        setHistoryBarColors(historyBinding.fourDays, fourDaysAgoMood)
        setHistoryBarColors(historyBinding.fiveDays, fiveDaysAgoMood)
        setHistoryBarColors(historyBinding.sixDays, sixDaysAgoMood)
        setHistoryBarColors(historyBinding.sevenDays, sevenDaysAgoMood)

        //Set history bar widths
        setHistoryBarWidth(historyBinding.yesterday, yesterdayMood)
        setHistoryBarWidth(historyBinding.twoDays, twoDaysAgoMood)
        setHistoryBarWidth(historyBinding.threeDays, threeDaysAgoMood)
        setHistoryBarWidth(historyBinding.fourDays, fourDaysAgoMood)
        setHistoryBarWidth(historyBinding.fiveDays, fiveDaysAgoMood)
        setHistoryBarWidth(historyBinding.sixDays, sixDaysAgoMood)
        setHistoryBarWidth(historyBinding.sevenDays, sevenDaysAgoMood)

        // Show icon for moods with comment
        showCommentIcon(yesterdayMood, historyBinding.yesterday)
        showCommentIcon(twoDaysAgoMood, historyBinding.twoDays)
        showCommentIcon(threeDaysAgoMood, historyBinding.threeDays)
        showCommentIcon(fourDaysAgoMood, historyBinding.fourDays)
        showCommentIcon(fiveDaysAgoMood, historyBinding.fiveDays)
        showCommentIcon(sixDaysAgoMood, historyBinding.sixDays)
        showCommentIcon(sevenDaysAgoMood, historyBinding.sevenDays)

    }
}