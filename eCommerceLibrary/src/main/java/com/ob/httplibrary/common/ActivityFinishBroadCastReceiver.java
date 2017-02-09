package com.ob.httplibrary.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This broadcast receiver is used to finish all the activities based on action based.
 */
public class ActivityFinishBroadCastReceiver extends BroadcastReceiver {
    private Listener mListener;

    public ActivityFinishBroadCastReceiver() {
    }

    public ActivityFinishBroadCastReceiver(Listener list) {
        super();
        mListener = list;
    }

    @Override
    public void onReceive(Context p_context, Intent p_intent) {
        if (mListener != null) {
            int mAction = p_intent.getIntExtra(KeyConstant.ACTION, -1);
            mListener.destroyActivity(mAction);
        }
    }

    public interface Listener {
        void destroyActivity(final int pAction);
    }
}
