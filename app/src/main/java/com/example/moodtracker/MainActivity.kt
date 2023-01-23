package com.example.moodtracker

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.MyApp.Companion.CURRENT_MOOD
import com.example.moodtracker.MyApp.Companion.FILE_NAME
import com.example.moodtracker.MyApp.Companion.arrayOfBackgrounds
import com.example.moodtracker.MyApp.Companion.arrayOfImages
import com.example.moodtracker.MyApp.Companion.currentMood
import com.example.moodtracker.MyApp.Companion.moodSharedPref
import com.google.gson.Gson
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    //View initialising
    lateinit var background: View
    lateinit var moodImage: ImageView
    private lateinit var noteButton: ImageView
    private lateinit var historyActivityBtn: ImageView

    //Gesture Detect measurements
    lateinit var gestureDetector: GestureDetector
    var y1: Float = 0.0f
    var y2: Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }

    //media player for sound effect.............................................................
    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View variables
        background = findViewById(R.id.mood_container)
        moodImage = findViewById(R.id.emoji_image)
        noteButton = findViewById(R.id.custom_note)
        historyActivityBtn = findViewById(R.id.mood_history)

        //Gesture Detector
        gestureDetector = GestureDetector(this, this)

        //On click listeners
        noteButton.setOnClickListener {
            buildDialog()
            saveCommentAndMood()
        }
        historyActivityBtn.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)

        }
        //Initialize Shared Prefs and Shared Pref values
        moodSharedPref = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        initialiseMood()
        triggerAlarm()
        initialiseHistoryVariables(this)
        setMood()
    }

    //Instantiate neutral mood or retrieve Shared Pref mood.........................................
    private fun initialiseMood(): Mood {

        val currentMoodString = moodSharedPref.getString(CURRENT_MOOD, null)

        if (currentMoodString.isNullOrEmpty()) {
            currentMood = Mood()
        } else {
            currentMood = Gson().fromJson<Mood>(currentMoodString, Mood::class.java)
        }

        return currentMood
    }

    //Set view background and image.................................................................
    private fun setMood() {
        moodImage.setImageResource(arrayOfImages[currentMood.moodScore])
        background.setBackgroundColor(getColor(arrayOfBackgrounds[currentMood.moodScore]))
    }

    //Save mood object to Shared
    fun saveCommentAndMood() {
        objectToPreference(this, currentMood, CURRENT_MOOD)
    }

    //..............................................................................................
    //Play Sounds...................................................................................
    fun playHappierSound(context: Context) {
        mp = android.media.MediaPlayer.create(context, R.raw.more_happy_tone)

        mp.start()
        mp.isLooping = false
        mp.setOnCompletionListener { mp.release() }

    }

    fun playSadderSound(context: Context) {
        mp = android.media.MediaPlayer.create(context, R.raw.less_happy_tone)

        mp.start()
        mp.isLooping = false
        mp.setOnCompletionListener { mp.release() }
    }
/*
Credits to sound mp3 source. less_happy_tone is from
<a href="https://pixabay.com/users/u_31vnwfmzt6-31480456/?utm_source=link-attribution&amp;utm_medium
=referral&amp;utm_campaign=music&amp;utm_content=126626">u_31vnwfmzt6</a> from
<a href="https://pixabay.com//?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign
=music&amp;utm_content=126626">Pixabay</a>.
more_happy_tone is from
<a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign
=music&amp;utm_content=6346">Pixabay</a>.
*/

    //Gesture Detection.............................................................................
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when (event?.action) {
            0 -> {
                y1 = event.y
            }
            1 -> {
                y2 = event.y

                val valueY: Float = y2 - y1
                if (abs(valueY) > MIN_DISTANCE) {
                    if (currentMood.moodScore < 4) {

                        if (y1 > y2) {
                            playHappierSound(this)
                            currentMood.moodScore += 1
                            setMood()
                        }
                    }
                    if (currentMood.moodScore > 0) {

                        if (y1 < y2) {
                            playSadderSound(this)
                            currentMood.moodScore -= 1
                            setMood()
                        }
                    }
                }
            }

        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onStop() {
        super.onStop()
        saveCommentAndMood()

    }
}