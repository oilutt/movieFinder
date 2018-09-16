package oilutt.sambatechproject.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;

import oilutt.sambatechproject.R;
import oilutt.sambatechproject.utils.AnimationUtils;

abstract class BaseActivity extends MvpAppCompatActivity {

    protected Toolbar toolbar;
    protected boolean runningBackground = false;

    protected abstract int getLayoutResource();

    private boolean isFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
    }

    @Override
    protected void onResume() {
        super.onResume();
        runningBackground = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        runningBackground = true;
        if (isFinish) {
            finish();
        }
    }

    protected void setIsFinish() {
        this.isFinish = true;
    }

    protected void goToActivity(Context context, Class<?> target) {
        Intent intent = new Intent(context, target);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }

    protected void goToActivityCircleRevealForResult(Context context, Class<?> target, View view, int requestCode) {
        Intent intent = new Intent(context, target);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, view, "transition");
            int revealX = (int) (view.getX() + view.getWidth() / 2);
            int revealY = (int) (view.getY() + view.getHeight() / 2);

            intent.putExtra(AnimationUtils.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(AnimationUtils.EXTRA_CIRCULAR_REVEAL_Y, revealY);

            ActivityCompat.startActivityForResult(this, intent, requestCode, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    protected void setUpToolbarText(int title, boolean isBack) {
        setUpToolbarText(getString(title), isBack);
    }

    protected void setUpToolbarText(String title, boolean isBack) {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextAppearance(this, R.style.TextTitleToolbar);
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            if (!TextUtils.isEmpty(title)) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(title);
            } else {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            if (isBack) {
                toolbar.setNavigationIcon(R.drawable.ic_back);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }
    }

    protected void fadeInFadeOut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fadeIn = new Fade(Fade.IN);
            fadeIn.setDuration(1000);
            getWindow().setEnterTransition(fadeIn);

            Fade fadeOut = new Fade(Fade.OUT);
            fadeOut.setDuration(1000);
            getWindow().setExitTransition(fadeOut);
        }
    }
}
