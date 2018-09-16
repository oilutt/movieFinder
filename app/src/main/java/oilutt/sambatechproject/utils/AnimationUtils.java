package oilutt.sambatechproject.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;


public final class AnimationUtils {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    public static void circularRevealAnimation(View view, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {

            view.setVisibility(View.INVISIBLE);

            int revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            int revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY, view);
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private static void revealActivity(int x, int y, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(view.getWidth(), view.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            android.animation.Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            circularReveal.start();
        }
    }

    public static void unRevealActivity(View view, Intent intent, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            int revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);

            float finalRadius = (float) (Math.max(view.getWidth(), view.getHeight()) * 1.1);
            android.animation.Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    view, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    view.setVisibility(View.INVISIBLE);
                    activity.finish();
                }
            });

            circularReveal.start();
        }
    }
}