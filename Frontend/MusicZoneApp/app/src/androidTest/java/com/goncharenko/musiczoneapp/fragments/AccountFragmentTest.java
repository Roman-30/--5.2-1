package com.goncharenko.musiczoneapp.fragments;

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
public class AccountFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void login() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_account)).perform(ViewActions.click());
        String email = "gmv.vrn@bk.ru";
        String password = "newpass";

        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(email));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
    }

    @Test
    public void testEditButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.edit))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignOutButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.sign_out))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLogoDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.img_logo))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testAccountNameDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.account_name))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testAccountEmailDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.account_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCheckMusicButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.check_music))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
