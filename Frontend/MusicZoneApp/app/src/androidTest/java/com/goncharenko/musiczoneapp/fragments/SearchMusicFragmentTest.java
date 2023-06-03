package com.goncharenko.musiczoneapp.fragments;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchMusicFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLogoDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.logo))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchInputDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.input_search))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchResultsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testElementPositioning() {
        Espresso.onView(ViewMatchers.withId(R.id.logo))
                .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(R.id.layout))));
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout))
                .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(R.id.layout))));
        Espresso.onView(ViewMatchers.withId(R.id.input_search))
                .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(R.id.linearLayout))));
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
                .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(R.id.linearLayout))));
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
                .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(R.id.layout))));
    }
}