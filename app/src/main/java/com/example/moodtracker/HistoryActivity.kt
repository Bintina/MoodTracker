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

        // Show icon for moods with comment
        showCommentIcon(yesterdayMood, historyBinding.yesterday)
        showCommentIcon(twoDaysAgoMood, historyBinding.twoDays)
        showCommentIcon(threeDaysAgoMood, historyBinding.threeDays)
        showCommentIcon(fourDaysAgoMood, historyBinding.fourDays)
        showCommentIcon(fiveDaysAgoMood, historyBinding.fiveDays)
        showCommentIcon(sixDaysAgoMood, historyBinding.sixDays)
        showCommentIcon(sevenDaysAgoMood, historyBinding.sevenDays)

    }


    fun showCommentIcon(moodObject: Mood, view: TextView) {
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


}