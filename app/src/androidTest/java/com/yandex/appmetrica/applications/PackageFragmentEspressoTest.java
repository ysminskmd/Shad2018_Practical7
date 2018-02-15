package com.yandex.appmetrica.applications;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class PackageFragmentEspressoTest {

    private final String mPackageName = InstrumentationRegistry.getTargetContext().getPackageName();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loadButtonLoadsCurrentPackage() {
        onView(withId(R.id.load)).perform(click());
        onView(withId(R.id.list)).perform(RecyclerViewActions.scrollTo(withChild(withText(mPackageName))));
        onView(allOf(withId(R.id.content), withText(mPackageName))).check(matches(isDisplayed()));
    }

    @Test
    public void clearButtonClearsPackages() {
        onView(withId(R.id.load)).perform(click());
        onView(withId(R.id.clear)).perform(click());
        onView(withId(R.id.content)).check(doesNotExist());
    }

    @Test
    public void noPackagesByDefault() {
        onView(withId(R.id.content)).check(doesNotExist());
    }
}
