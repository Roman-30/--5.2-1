package com.goncharenko.musiczoneapp.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;

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
public class EntryFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void login() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_account)).perform(ViewActions.click());
    }

    @Test
    public void testEmailInputDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.email_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testPasswordInputDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.password_input))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignInButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRecoverPasswordButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.recover_password))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUpButtonDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testInvalidEmailInputMessage(){
        String invalidEmail = "";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(hasErrorText("Enter email")));
    }

    @Test
    public void testInvalidEmailInputMessage1(){
        String invalidEmail = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(hasErrorText("The email is too long")));
    }

    @Test
    public void testInvalidEmailInputMessage2(){
        String invalidEmail = "invalid-email";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(hasErrorText("Enter the correct email")));
    }

    @Test
    public void testInvalidPasswordInputMessage(){
        String rightEmail = "mail@ma.il";
        String invalidPassword = "";

        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(invalidPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(hasErrorText("Enter the password")));
    }

    @Test
    public void testInvalidPasswordInputMessage1(){
        String rightEmail = "mail@ma.il";
        String invalidPassword = "asd";

        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(invalidPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_in_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(hasErrorText("The password must contain at least 6 characters")));
    }
}
