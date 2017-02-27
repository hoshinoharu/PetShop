package shop.mgzj.petshop.harubase.tools;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by 星野悠 on 2017/1/29.
 */

public class AnimeTool {
    public interface OverLayController {
        void showOverLay();

        void dismissOverLay();
    }

    public static ObjectAnimator showAnime(final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -view.getMeasuredWidth() * 0.1f, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha(animation.getAnimatedFraction());
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        return animator;
    }

    public static ObjectAnimator dismissAnime(final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0, -view.getMeasuredWidth() * 0.1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha(1 - animation.getAnimatedFraction());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(500);
        return animator;
    }

    public static void startAnime(Animator animator){
        if(animator != null){
            if(animator.isRunning()){
                animator.cancel();
            }
            animator.start();
        }
    }

    public static void startAnimeWhenNotIsRunning(Animator animator){
        if(animator != null){
            if(!animator.isRunning()){
                animator.start();
            }
        }
    }
    public static void stopAnime(Animator animator){
        if(animator != null){
            animator.cancel();
        }
    }

    public static boolean isRunning(Animator animator){
        if(animator == null || animator.isRunning()){
            return true ;
        }
        return false ;
    }
}
