package com.goncharenko.musiczoneapp.activity;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.CustomIntro;
import com.goncharenko.musiczoneapp.activities.MainActivity;
import com.goncharenko.musiczoneapp.fragments.SearchMusicFragment;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void clickButtonHome(){
        onView(withId(R.id.navigation_home)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void clickButtonPlayer(){
        onView(withId(R.id.navigation_player)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void clickButtonAccount(){
        onView(withId(R.id.navigation_account)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void checkFragmentDisplayedOnNavigationItemClick() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_player)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.frame_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFragmentSwitchOnNavigationItemClick() {
        // Проверка переключения фрагментов при нажатии на элементы навигации
        Espresso.onView(ViewMatchers.withId(R.id.navigation_player)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.navigation_account)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.navigation_home)).perform(ViewActions.click());

        Espresso.onView(withId(R.id.frame_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFragmentVisibilityAfterOrientationChange() {
        // Проверка видимости фрагментов после изменения ориентации устройства
        Espresso.onView(ViewMatchers.withId(R.id.navigation_player)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.frame_layout)).check(matches(isDisplayed()));

        // Симуляция изменения ориентации
        activityScenarioRule.getScenario().onActivity(activity -> activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));

        Espresso.onView(withId(R.id.frame_layout)).check(matches(isDisplayed()));
    }
}
