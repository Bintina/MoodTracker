package com.example.moodtracker

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
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
    private lateinit var background: View
    private lateinit var moodImage: ImageView
    private lateinit var noteButton: ImageView
    private lateinit var historyActivity: ImageView

    //Gesture Detect measurements
    lateinit var gestureDetector: GestureDetector
    var y1: Float = 0.0f
    var y2: Float = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views. view variable allocations
        background = findViewById(R.id.mood_container)
        moodImage = findViewById(R.id.emoji_image)
        noteButton = findViewById(R.id.custom_note)
        historyActivity = findViewById(R.id.mood_history)

        //Gestures
        gestureDetector = GestureDetector(this, this)

        //OnClickListeners
        noteButton.setOnClickListener {
            //TODO buildDialog()

        }
        historyActivity.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            //may want a back button to come back.
        }
        //Initialize SharedPref
        moodSharedPref = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        initialiseMood()
        setMood()
        triggerAlarm()
    }

    //instantiate current mood and save to Shared Preferences.......................................
    private fun initialiseMood(): Mood {
        val currentMoodString = moodSharedPref.getString(CURRENT_MOOD, null)

        if (currentMoodString != null) {
            currentMood = Gson().fromJson<Mood>(currentMoodString, Mood::class.java)
        } else {
            currentMood = Mood()
        }
        return currentMood
    }

    //Set View Elements.............................................................................
    fun setMood() {

        moodImage.setImageResource(arrayOfImages[currentMood.moodScore])
        background.setBackgroundColor(getColor(arrayOfBackgrounds[currentMood.moodScore]))
    }

    //Gesture Detection.............................................................................

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)

        when (event?.action) {
            //start swipe
            0 -> {
                y1 = event.y
            }
            //end swipe
            1 -> {
                y2 = event.y

                val valueY: Float = y2 - y1
                if (abs(valueY) > MIN_DISTANCE) {
                    //bottom to top swipe
                    if (currentMood.moodScore < 4) {

                        if (y1 > y2) {
                            //+ mood score
                            currentMood.moodScore += 1
                            setMood()
                            Toast.makeText(this, "Bottom swipe", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //top to bottom swipe
                    if (currentMood.moodScore > 0) {

                        if (y1 < y2) {
                            //- mood score
                            currentMood.moodScore -= 1
                            setMood()
                            Toast.makeText(this, "Top swipe", Toast.LENGTH_SHORT).show()
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

}