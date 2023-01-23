package com.example.moodtracker

import org.junit.Assert.assertTrue
import org.junit.Test

internal class MoodTest {
    @Test
    fun checkMoodScore() {
        assertTrue(Mood().moodScore in 0..4)
    }
}