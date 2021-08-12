package com.example.astonindoor;

import androidx.fragment.app.testing.FragmentScenario;
import static org.hamcrest.Matchers.allOf;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class MapsActivityTest {
    FragmentScenario fragment = FragmentScenario.launch(NavMapFragment.class);




    @Test
    public void useAppContext() {
        // Context of the app under test.
        fragment.recreate();


    }

    /**
     * cech if the image is launched
     */
    @Test
    public void imageViewTest() {
        onView(withId(R.id.tryImage))
                .check(matches(isDisplayed()));

    }
    /**
     * Test current position middle button
     */
    @Test
    public void useButton() {
        onView(withId(R.id.CurrentPostionButton)).perform(click()).check(matches((isEnabled())));
    }



    @Test
    public void currentPositionTest() {

        // Testing the string results of the autocompletetextview
        onView(withId(R.id.currentPostion))
                .check(matches(withText("2100")));
    }

    /**
     * Test popup of the autocompletetextview
     */
    @Test
    public void currentPositionBox_listPopUp_Test() {

        // Now we may want to explicitly tap on a completion. We must override Espresso's
        // default window selection heuristic with our own.
        onView(withText("2100"))
                .inRoot(isPlatformPopup())
                .perform(click());
    }


    /**
     * Test popup of the autocompletetextview
     */

    @Test
    public void currentPositionBox_listPopUp_Test2() {

        // useful because some of the completions may not be part of the View Hierarchy
        // unless you scroll around the list.
        onData(allOf(instanceOf(String.class), is("308")))
                .inRoot(isPlatformPopup())
                .perform(click());
    }

    /**
     * Test scroll of the autocompletetextview
     */
    @Test
    public void testList_Scroll(){
        //scrolling test
        onView(withId(R.id.currentPostion))
                .perform(scrollTo())
                .perform(typeText("2100"));
    }

}




