package com.goncharenko.musiczoneapp.fragments;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyMusicFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void login() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_account)).perform(ViewActions.click());
        String email = "gmv.vrn@bk.ru";
        String password = "newpass";

        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.check_music)).perform(ViewActions.click());
    }

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
}