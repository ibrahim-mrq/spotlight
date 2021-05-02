package com.mrq.spotlight;

/**
 * Created by Ibrahim Mrq on 30/04/2021
 */

public interface OnTargetStateChangedListener<T extends Target> {
    /**
     * Called when Target is started
     */
     void onStarted(T target);

    /**
     * Called when Target is started
     */
    void onEnded(T target);
}
