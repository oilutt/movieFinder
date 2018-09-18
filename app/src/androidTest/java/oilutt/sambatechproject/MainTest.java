package oilutt.sambatechproject;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import oilutt.sambatechproject.view.activity.MainActivity;
import oilutt.sambatechproject.view.activity.MovieDetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainTest extends AbstractActivityViewTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            return new Intent(Intent.ACTION_MAIN);
        }
    };

    @Test
    public void testMovieDetailActivity() throws Exception {
        //O TEMPO DE LOADING ATRAPALHA O TESTE
        waitId(R.id.rv_movies, 2000);
        onView(withRecyclerView(R.id.rv_movies).atPosition(1)).perform(waitId(R.id.rv_movies, 2000), click());

        onView(withId(R.id.fav)).perform(click());
        onView(withId(R.id.img_movie)).perform(click());
        matchToolbarTitle("");

        pressBack();
        Assert.assertTrue(getActivityInstance() instanceof MovieDetailActivity);
        onView(withId(R.id.fav)).perform(click());

        pressBack();
        Assert.assertTrue(getActivityInstance() instanceof MainActivity);
    }

    @Test
    public void testFilterActivity() throws Exception {
        onView(withId(R.id.filter)).perform(click());
        matchToolbarTitle(getActivityInstance().getString(R.string.filter));

        onView(withId(R.id.rb_popularity)).perform(click());
        onView(withId(R.id.rb_popularity)).check(matches(isChecked()));
        onView(withId(R.id.rb_average_votes)).perform(click());
        onView(withId(R.id.rb_average_votes)).check(matches(isChecked()));
        onView(withId(R.id.rb_popularity)).check(matches(isNotChecked()));
        onView(withId(R.id.rb_release)).perform(click());
        onView(withId(R.id.rb_release)).check(matches(isChecked()));
        onView(withId(R.id.rb_average_votes)).check(matches(isNotChecked()));

        onView(withId(R.id.btn_filter)).perform(click());

        Assert.assertTrue(getActivityInstance() instanceof MainActivity);
    }

    @Test
    public void testFilterFavActivity() throws Exception {
        onView(withId(R.id.filter)).perform(click());
        matchToolbarTitle(getActivityInstance().getString(R.string.filter));

        onView(withId(R.id.rb_fav)).perform(click());
        onView(withId(R.id.rb_fav)).check(matches(isChecked()));

        onView(withId(R.id.btn_filter)).perform(click());

        Assert.assertTrue(getActivityInstance() instanceof MainActivity);
    }

    @Test
    public void testMainActivity() throws Exception {
        matchToolbarTitle(getActivityInstance().getString(R.string.app_name));
    }
}
