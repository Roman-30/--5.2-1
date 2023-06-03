package com.goncharenko.musiczoneapp.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class PlayerFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void login() {
        Espresso.onView(withId(R.id.navigation_player)).perform(ViewActions.click());
    }

    @Test
    public void testSongTitle() {
        Espresso.onView(withId(R.id.song_title)).check(matches(isDisplayed()));
    }

    @Test
    public void testSongAuthor() {
        Espresso.onView(withId(R.id.song_author)).check(matches(isDisplayed()));
    }

    @Test
    public void testSeekBar() {
        Espresso.onView(withId(R.id.seek_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testCurrentTime() {
        Espresso.onView(withId(R.id.current_time)).check(matches(isDisplayed()));
    }

    @Test
    public void testTotalTime() {
        Espresso.onView(withId(R.id.total_time)).check(matches(isDisplayed()));
    }

    @Test
    public void testPreviousButton() {
        Espresso.onView(withId(R.id.previous)).check(matches(isDisplayed()));
    }

    @Test
    public void testNextButton() {
        Espresso.onView(withId(R.id.next)).check(matches(isDisplayed()));
    }

    @Test
    public void testPausePlayButton() {
        Espresso.onView(withId(R.id.pause_play)).check(matches(isDisplayed()));
    }
}
