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
class AddMultiplePortsTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addMultiplePortsTest() {
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
                withId(R.id.list_item_text_view), withText("port three"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.PortsRecycleView),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textViewHere), withText("port three"),
                withParent(
                    allOf(
                        withId(R.id.new_trip_page),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("port three")))

        val appCompatButton3 = onView(
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
        appCompatButton3.perform(click())

        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.list_item_text_view), withText("port four"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.PortsRecycleView),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.textViewHere), withText("port three\nport four"),
                withParent(
                    allOf(
                        withId(R.id.new_trip_page),
                        withParent(withId(android.R.id.content))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("port three port four")))
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
