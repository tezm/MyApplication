package com.example.myapplication


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddingTripTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addingTripTest() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.addButton),
                childAtPosition(
                    allOf(
                        withId(R.id.main_screen_layout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.from),
                childAtPosition(
                    allOf(
                        withId(R.id.FromTO),
                        childAtPosition(
                            withId(R.id.new_trip_page),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("22"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.To_field),
                childAtPosition(
                    allOf(
                        withId(R.id.FromTO),
                        childAtPosition(
                            withId(R.id.new_trip_page),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.To_field),
                childAtPosition(
                    allOf(
                        withId(R.id.FromTO),
                        childAtPosition(
                            withId(R.id.new_trip_page),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("23"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.NumberOfPeople),
                childAtPosition(
                    allOf(
                        withId(R.id.new_trip_page),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("2"), closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.addButtonTripPage),
                childAtPosition(
                    allOf(
                        withId(R.id.new_trip_page),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.list_item_text_view), withText("port two"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.PortsRecycleView),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.goOn),
                childAtPosition(
                    allOf(
                        withId(R.id.new_trip_page),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.list_item_text_view), withText("big boat"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.BoatsRecycleView),
                        6
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.list_item_text_view), withText("22 - 23"),
                withParent(withParent(withId(R.id.FirstRecycleView))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("22 - 23")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
