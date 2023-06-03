package com.goncharenko.musiczoneapp.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.goncharenko.musiczoneapp.activities.RegistrationAccountActivity;
import com.goncharenko.musiczoneapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RegistrationAccountActivityTest {

    @Rule
    public ActivityScenarioRule<RegistrationAccountActivity> activityRule = new ActivityScenarioRule<>(RegistrationAccountActivity.class);

    @Test
    public void testElementsDisplayed() {
        onView(ViewMatchers.withId(R.id.first_name_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.second_name_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.nickname_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.phone_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.sign_up)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withId(R.id.back)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testInvalidNameInputMessage(){
        String invalidName = "";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(invalidName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.first_name_input)).check(matches(hasErrorText("Enter a name")));
    }

    @Test
    public void testInvalidNameInputMessage1(){
        String invalidName = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(invalidName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.first_name_input)).check(ViewAssertions.matches(hasErrorText("The name is too long")));
    }

    @Test
    public void testInvalidNameInputMessage2(){
        String invalidName = "JHjgk1554";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(invalidName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.first_name_input)).check(ViewAssertions.matches(hasErrorText("The name can contain only letters")));
    }

    @Test
    public void testInvalidSurnameNameInputMessage(){
        String rightName = "Name";
        String invalidSurName = "";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(invalidSurName));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.second_name_input)).check(ViewAssertions.matches(hasErrorText("Enter your surname name")));
    }

    @Test
    public void testInvalidSurnameNameInputMessage1(){
        String rightName = "Name";
        String invalidSurName = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(invalidSurName));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.second_name_input)).check(ViewAssertions.matches(hasErrorText("The surname is too long")));
    }

    @Test
    public void testInvalidSurnameNameInputMessage2(){
        String rightName = "Name";
        String invalidSurName = "JHjgk1554";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(invalidSurName));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.second_name_input)).check(ViewAssertions.matches(hasErrorText("The surname can contain only letters")));
    }

    @Test
    public void testInvalidNicknameNameInputMessage(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String invalidNickName = "";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(invalidNickName));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.nickname_input)).check(ViewAssertions.matches(hasErrorText("Enter your nickname")));
    }

    @Test
    public void testInvalidNicknameNameInputMessage1(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String invalidNickName = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(invalidNickName));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.nickname_input)).check(ViewAssertions.matches(hasErrorText("The nickname is too long")));
    }

    @Test
    public void testInvalidNicknameNameInputMessage2(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String invalidNickName = "JHjgk1554___!";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(invalidNickName));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.nickname_input)).check(ViewAssertions.matches(hasErrorText("A nickname can contain only letters and numbers")));
    }

    @Test
    public void testInvalidEmailInputMessage(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String invalidEmail = "";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(hasErrorText("Enter email")));
    }

    @Test
    public void testInvalidEmailInputMessage1(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String invalidEmail = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(hasErrorText("The email is too long")));
    }

    @Test
    public void testInvalidEmailInputMessage2(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String invalidEmail = "invalid-email";
        String rightPhone = "11111111111";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(invalidEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(hasErrorText("Enter the correct email")));
    }

    @Test
    public void testInvalidPhoneInputMessage(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String invalidPhone = "";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(invalidPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.phone_input)).check(ViewAssertions.matches(hasErrorText("Enter your phone number")));
    }

    @Test
    public void testInvalidPhoneInputMessage1(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String invalidPhone = "qqqqqqqqqqqqqqqqqqqqqqqq";
        String rightPassword = "123456";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(invalidPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(rightPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.phone_input)).check(ViewAssertions.matches(hasErrorText("Enter the correct phone number (11 digits)")));
    }

    @Test
    public void testInvalidPasswordInputMessage(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String invalidPassword = "";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(invalidPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(hasErrorText("Enter the password")));
    }

    @Test
    public void testInvalidPasswordInputMessage1(){
        String rightName = "Name";
        String rightSurname = "Surname";
        String rightNickname = "Nickname";
        String rightEmail = "mail@ma.il";
        String rightPhone = "11111111111";
        String invalidPassword = "asd";

        onView(ViewMatchers.withId(R.id.first_name_input)).perform(ViewActions.typeText(rightName));
        onView(ViewMatchers.withId(R.id.second_name_input)).perform(ViewActions.typeText(rightSurname));
        onView(ViewMatchers.withId(R.id.nickname_input)).perform(ViewActions.typeText(rightNickname));
        onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(rightEmail));
        onView(ViewMatchers.withId(R.id.phone_input)).perform(ViewActions.typeText(rightPhone));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText(invalidPassword));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.sign_up)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(hasErrorText("The password must contain at least 6 characters")));
    }

}
