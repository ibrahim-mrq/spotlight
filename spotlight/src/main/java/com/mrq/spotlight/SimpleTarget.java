package com.mrq.spotlight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * Created by Ibrahim Mrq on 30/04/2021
 */

public class SimpleTarget implements Target {

    private PointF point;
    private float radius;
    private View view;
    private OnTargetStateChangedListener listener;

    /**
     * Constructor
     */
    private SimpleTarget(PointF point, float radius, View view, OnTargetStateChangedListener listener) {
        this.point = point;
        this.radius = radius;
        this.view = view;
        this.listener = listener;
    }

    @Override
    public PointF getPoint() {
        return point;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public OnTargetStateChangedListener getListener() {
        return listener;
    }

    /**
     * Builder class which makes it easier to create {@link SimpleTarget}
     */
    public static class Builder extends AbstractBuilder<Builder, SimpleTarget> {

        @Override
        protected Builder self() {
            return this;
        }

        private static final int ABOVE_SPOTLIGHT = 0;
        private static final int BELOW_SPOTLIGHT = 1;

        private String title;
        private String description;

        /**
         * Constructor
         */
        public Builder(@NonNull Activity context) {
            super(context);
        }

        /**
         * Set the title text shown on Spotlight
         *
         * @param title title shown on Spotlight
         * @return This Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the description text shown on Spotlight
         *
         * @param description title shown on Spotlight
         * @return This Builder
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Create the {@link SimpleTarget}
         *
         * @return the created SimpleTarget
         */
        @Override
        public SimpleTarget build() {
            if (getContext() == null) {
                throw new RuntimeException("context is null");
            }
            View view = getContext().getLayoutInflater().inflate(R.layout.layout_spotlight, null);
            ((TextView) view.findViewById(R.id.title)).setText(title);
            ((TextView) view.findViewById(R.id.description)).setText(description);
            PointF point = new PointF(startX, startY);
            calculatePosition(point, radius, view);
            return new SimpleTarget(point, radius, view, listener);
        }

        /**
         * calculate the position of title and description based off of where the spotlight reveals
         */
        private void calculatePosition(final PointF point, final float radius, View spotlightView) {
            float[] areas = new float[2];
            Point screenSize = new Point();
            ((WindowManager) spotlightView.getContext()
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(screenSize);

            areas[ABOVE_SPOTLIGHT] = point.y / screenSize.y;
            areas[BELOW_SPOTLIGHT] = (screenSize.y - point.y) / screenSize.y;

            int largest;
            if (areas[ABOVE_SPOTLIGHT] > areas[BELOW_SPOTLIGHT]) {
                largest = ABOVE_SPOTLIGHT;
            } else {
                largest = BELOW_SPOTLIGHT;
            }

            final LinearLayout layout = ((LinearLayout) spotlightView.findViewById(R.id.container));
            layout.setPadding(100, 0, 100, 0);
            switch (largest) {
                case ABOVE_SPOTLIGHT:
                    spotlightView.getViewTreeObserver()
                            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    layout.setY(point.y - radius - 100 - layout.getHeight());
                                }
                            });
                    break;
                case BELOW_SPOTLIGHT:
                    layout.setY((int) (point.y + radius + 100));
                    break;
            }
        }
    }
}