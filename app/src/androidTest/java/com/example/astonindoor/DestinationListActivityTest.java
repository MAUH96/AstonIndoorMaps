//package com.example.astonindoor;
//
//import static android.service.autofill.Validators.not;
//
//
//import org.hamcrest.Matcher;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.greaterThan;
//import static org.hamcrest.Matchers.instanceOf;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.junit.Assert.assertThat;
//
//import androidx.annotation.ContentView;
//import androidx.appcompat.widget.SearchView;
//import androidx.test.espresso.PerformException;
//import androidx.test.espresso.UiController;
//import androidx.test.espresso.ViewAction;
//import androidx.test.espresso.contrib.RecyclerViewActions;
//import androidx.test.espresso.matcher.ViewMatchers;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.filters.MediumTest;
//
//
//import androidx.test.runner.AndroidJUnit4;
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
//import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
//import android.content.ClipData;
//import android.view.View;
//
//import com.example.astonindoor.DestinationSearchList.DestinationListActivity;
//
//
//@MediumTest
//@RunWith(AndroidJUnit4.class)
//
//public class DestinationListActivityTest {
//
//    public ActivityScenarioRule rule = new ActivityScenarioRule<>(NextFloorNavActivity.class);
//
//    /**
//     * scrool down the list to look at an item that does not exist
//     *
//     * taken from official espresso docs: https://developer.android.com/training/testing/espresso/lists
//     */
//    @Test(expected = PerformException.class)
//    public void itemWithText_doesNotExist() {
//        // Attempt to scroll to an item that contains the special text.
//        onView(ViewMatchers.withId(R.id.searchBar_list))
//                // scrollTo will fail the test if no item matches.
//                .perform(RecyclerViewActions.scrollTo(
//                        hasDescendant(withText("not in the list"))
//                ));
//    }
//
//    /**
//     * scroll til the bottom and click on the item
//     *
//     * taken from official espresso docs: https://developer.android.com/training/testing/espresso/lists
//     */
//    @Test
//    public void scrollToItem_inTheBottom() {
//        // First, scroll to the position that needs to be matched and click on it.
//        onView(ViewMatchers.withId(R.id.searchBar_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(22,
//                        click()));
//    }
//
//    /**
//     * scroll till the middle and click on the item
//     *
//     * taken from official espresso docs: https://developer.android.com/training/testing/espresso/lists
//     */
//
//    @Test
//    public void scrollToItem_inTheMiddle() {
//        // First, scroll to the position that needs to be matched and click on it.
//        onView(ViewMatchers.withId(R.id.searchBar_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(15,
//                        click()));
//    }
//
//
//    /**
//     * the normat typeText was not working so I implmented this solution:
//     *
//     * refrence: https://stackoverflow.com/questions/48037060/how-to-type-text-on-a-searchview-using-espresso
//     * @param text
//     * @return
//     */
//    public static ViewAction typeSearchViewText(final String text){
//        return new ViewAction(){
//            @Override
//            public Matcher<View> getConstraints() {
//                //Ensure that only apply if it is a SearchView and if it is visible.
//                return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
//            }
//
//            @Override
//            public String getDescription() {
//                return "Change view text";
//            }
//
//            @Override
//            public void perform(UiController uiController, View view) {
//                ((SearchView) view).setQuery(text,false);
//            }
//        };
//    }
//
//    /**
//     * Test the searchview by using the the above static method.
//     * Writes a query on the search menu
//     */
//
//    @Test
//    public void typeOnSearchView() {
//        // First, scroll to the position that needs to be matched and click on it.
//        onView(withId(R.id.list_searching))
//                .perform(typeSearchViewText("2100"));
//    }
//
//
//
//
//}
