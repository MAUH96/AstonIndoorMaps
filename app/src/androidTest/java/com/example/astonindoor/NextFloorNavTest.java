//package com.example.astonindoor;
//
//import static android.service.autofill.Validators.not;
//
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.hamcrest.Matchers.greaterThan;
//import static org.hamcrest.Matchers.instanceOf;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.junit.Assert.assertThat;
//
//import androidx.annotation.ContentView;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.filters.MediumTest;
//
//
//import androidx.test.runner.AndroidJUnit4;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//
//public class NextFloorNavTest {
//
//    public ActivityScenarioRule rule = new ActivityScenarioRule<>(NextFloorNavActivity.class);
//
//    /**
//     * test if the image is launched on the view
//     * @throws Exception
//     */
//    @Test
//    public void viewIsPresent() throws Exception {
//        onView(withId(R.id.mapImage))
//                .check(matches(isDisplayed()));
//
//    }
//
//
//}
//
