package com.goncharenko.musiczoneapp.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.goncharenko.musiczoneapp.activities.NewPasswordActivity;
import com.goncharenko.musiczoneapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class NewPasswordActivityTest {
    @Rule
    public ActivityScenarioRule<NewPasswordActivity> activityRule = new ActivityScenarioRule<>(NewPasswordActivity.class);

    @Test
    public void testElementsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cancel_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testInvalidPasswordErrorMessage() {
        String invalidPassword = "";

        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).perform(ViewActions.typeText(invalidPassword));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).check(matches(hasErrorText("Enter the password")));
    }

    @Test
    public void testInvalidPasswordErrorMessage1() {
        String invalidPassword = "";
        String rightPassword = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).perform(ViewActions.typeText(rightPassword));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_input)).perform(ViewActions.typeText(invalidPassword));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_input)).check(matches(hasErrorText("Enter the password")));
    }

    @Test
    public void testInvalidPasswordErrorMessage2() {
        String invalidPassword = "12345";

        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).perform(ViewActions.typeText(invalidPassword));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).check(matches(hasErrorText("The password must contain at least 6 characters")));
    }

    @Test
    public void testInvalidPasswordErrorMessage3() {
        String invalidPassword = "12345";
        String rightPassword = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).perform(ViewActions.typeText(rightPassword));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_input)).perform(ViewActions.typeText(invalidPassword));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_input)).check(matches(hasErrorText("The password must contain at least 6 characters")));
    }


    @Test
    public void testPasswordVerificationFailure() {
        String password = "TestPassword";
        String wrongPassword = "WrongPassword";

        Espresso.onView(ViewMatchers.withId(R.id.new_password_input)).perform(ViewActions.typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.verify_password_input)).perform(ViewActions.typeText(wrongPassword));

        Espresso.onView(ViewMatchers.withId(R.id.verify_password_button)).perform(ViewActions.click());
        // Проверяем, что всплывающее сообщение отображается с ожидаемым текстом
        onView(ViewMatchers.withId(R.id.verify_password_input)).check(ViewAssertions.matches(hasErrorText("The password does not match the one typed before")));
    }
}
