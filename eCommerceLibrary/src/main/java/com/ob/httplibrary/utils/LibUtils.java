package com.ob.httplibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.andoblib.log.CustomLogHandler;
import com.andoblib.util.AppSetting;
import com.ob.httplibrary.common.KeyConstant;

import java.util.List;
import java.util.Set;

public class LibUtils {
    public static void sendLogout(Context pContext) {
        AppSetting.clearPreference(pContext);
        Intent mIntent = new Intent(KeyConstant.BROAD_CAST_ACTION_NAME);
        mIntent.putExtra(KeyConstant.ACTION, KeyConstant.ACTION_LOGOUT_FINISH);
        pContext.sendBroadcast(mIntent);
    }

    public static boolean checkUserAsLogin(Context context) {

        return AppSetting.getString(context, KeyConstant.PREF_ACCESS_TOKEN, null) != null && AppSetting.getString(context, KeyConstant.PREF_USER_ID, null) != null;
    }

    /**
     * Method to log all keys and values in a bundle
     *
     * @param bundle Bundle to be logged
     */
    public static void logAll(Bundle bundle) {
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                CustomLogHandler.printVerbose("", "key => " + key + ",  Val => " + bundle.get(key));
            }
        }
    }

    /**
     * Method to log all values in List
     *
     * @param list List to be logged
     */
    public static void logAll(List list) {
        if (list != null) {
            for (int pos = 0; pos < list.size(); pos++) {
                CustomLogHandler.printVerbose("", "pos => " + pos + ",  Val => " + list.get(pos));
            }
        }
    }
}
