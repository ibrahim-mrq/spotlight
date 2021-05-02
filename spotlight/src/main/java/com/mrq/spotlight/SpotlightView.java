package com.mrq.spotlight;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by Ibrahim Mrq on 30/04/2021
 */

class SpotlightView extends FrameLayout {

    private final Paint paint = new Paint();
    private final Paint spotPaint = new Paint();
    private PointF point = new PointF();
    private ValueAnimator animator;
    private OnSpotlightStateChangedListener listener;

    /**
     * Listener to control Target state
     */
    interface OnSpotlightStateChangedListener {
        /**
         * Called when Target closed completely
         */
        void onTargetClosed();

        /**
         * Called when Target is Clicked
         */
        void onTargetClicked();
    }

    /**
     * sets listener to {@link SpotlightView}
     */
    public void setOnSpotlightStateChangedListener(OnSpotlightStateChangedListener l) {
        this.listener = l;
    }

    public SpotlightView(@NonNull Context context) {
        super(context, null);
        init();
    }

    public SpotlightView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public SpotlightView(@NonNull Context context, @Nullable AttributeSet attrs,
                         @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * prepares to show this Spotlight
     */
    private void init() {
        bringToFront();
        setWillNotDraw(false);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        spotPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animator != null && !animator.isRunning() && (float) animator.getAnimatedValue() > 0) {
                    if (listener != null) listener.onTargetClicked();
                }
            }
        });
    }

    /**
     * draws black background and trims a circle
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.background));
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        if (animator != null) {
            canvas.drawCircle(point.x, point.y, (float) animator.getAnimatedValue(), spotPaint);
        }
    }

    /**
     * starts an animation to show a circle
     *
     * @param x         initial position x where the circle is showing up
     * @param y         initial position y where the circle is showing up
     * @param radius    radius of the circle
     * @param duration  duration of the animation
     * @param animation type of the animation
     */
    void turnUp(float x, float y, float radius, long duration, TimeInterpolator animation) {
        this.point.set(x, y);
        animator = ValueAnimator.ofFloat(0f, radius);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                SpotlightView.this.invalidate();
            }
        });
        animator.setInterpolator(animation);
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * starts an animation to close the circle
     *
     * @param radius    radius of the circle
     * @param duration  duration of the animation
     * @param animation type of the animation
     */
    void turnDown(float radius, long duration, TimeInterpolator animation) {
        animator = ValueAnimator.ofFloat(radius, 0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                SpotlightView.this.invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) listener.onTargetClosed();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setInterpolator(animation);
        animator.setDuration(duration);
        animator.start();
    }
}