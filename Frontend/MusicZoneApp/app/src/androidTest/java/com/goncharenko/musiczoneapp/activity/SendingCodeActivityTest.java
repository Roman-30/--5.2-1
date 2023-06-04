package com.goncharenko.musiczoneapp.activity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.goncharenko.musiczoneapp.R;
import com.goncharenko.musiczoneapp.activities.SendingCodeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class SendingCodeActivityTest {

    @Rule
    public ActivityScenarioRule<SendingCodeActivity> activityRule = new ActivityScenarioRule<>(SendingCodeActivity.class);

    @Test
    public void testElementsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.code_input)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.send_code_button)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.verify_code_button)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cancel_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidEmailErrorMessage() {
        String invalidEmail = "";

        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        Espresso.onView(ViewMatchers.withId(R.id.send_code_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(matches(hasErrorText("Enter email")));
    }

    @Test
    public void testInvalidEmailErrorMessage1() {
        String invalidEmail = "invalid-email";

        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        Espresso.onView(ViewMatchers.withId(R.id.send_code_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(matches(hasErrorText("Enter the correct email")));
    }

    @Test
    public void testInvalidEmailErrorMessage2() {
        String invalidEmail = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";

        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        Espresso.onView(ViewMatchers.withId(R.id.send_code_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(matches(hasErrorText("The email is too long")));
    }

    @Test
    public void testInvalidCodeErrorMessage() {
        String invalidCode = "";

        Espresso.onView(ViewMatchers.withId(R.id.code_input)).perform(ViewActions.typeText(invalidCode));
        Espresso.onView(ViewMatchers.withId(R.id.verify_code_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.code_input)).check(matches(hasErrorText("Enter the code")));
    }

    @Test
    public void testInvalidCodeErrorMessage1() {
        String invalidCode = "123";

        Espresso.onView(ViewMatchers.withId(R.id.code_input)).perform(ViewActions.typeText(invalidCode));
        Espresso.onView(ViewMatchers.withId(R.id.verify_code_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.code_input)).check(matches(hasErrorText("The code must contain at least 6 characters")));
    }
}
