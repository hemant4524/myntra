package com.ob.httplibrary.common;

import android.content.Context;

import com.andoblib.http.HTTPConstant;
import com.andoblib.log.CustomLogHandler;
import com.andoblib.log.LogConstant;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.ob.httplibrary.R;

public class StaticInit {
    private static final String TAG = StaticInit.class.getSimpleName();

    public static void initStaticConstants(final Context pContext, final boolean pDebug) {

        //Crittercism Integration.

        LogConstant.DEBUG = pDebug;

        // Error Constants for http connection.
        com.andoblib.constant.Constants.ERR_NO_INTERNET_CONNECTION = pContext.getString(R.string.err_no_internet_connection);
        com.andoblib.constant.Constants.ERR_CONNECTION_TIMEOUT = pContext.getString(R.string.err_connection_timeout);
        com.andoblib.constant.Constants.ERR_SOMETHING_WENT_WRONG = pContext.getString(R.string.err_something_went_wrong);
        com.andoblib.constant.Constants.ERR_INVALID_RESPONSE = pContext.getString(R.string.err_invalid_response);
        com.andoblib.constant.Constants.ERR_STATUS_CODE = pContext.getString(R.string.err_status_code);
        com.andoblib.constant.Constants.ERR_READ_DATA = pContext.getString(R.string.err_read_data);
        com.andoblib.constant.Constants.ERR_GMAIL_APP_IS_NOT_INSTALLED = pContext.getString(R.string.err_gmail_app_is_not_installed);

        // Connection and Read timeout for http connection and FormHttpConnection class.
        HTTPConstant.CONNECTION_TIME_OUT = 60 * 1000;
        HTTPConstant.READ_TIME_OUT = 60 * 1000;


        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(pContext)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(pContext, config);


        CustomLogHandler.printDebug(TAG, "=====initStaticConstants====");
    }
}
