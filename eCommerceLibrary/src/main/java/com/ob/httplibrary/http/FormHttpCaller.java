package com.ob.httplibrary.http;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.andoblib.customexception.CustomException;
import com.andoblib.customexception.ExceptionConstant;
import com.andoblib.log.CustomLogHandler;
import com.andoblib.util.AppSetting;
import com.ob.httplibrary.common.APIType;
import com.ob.httplibrary.common.KeyConstant;
import com.ob.httplibrary.utils.LibUtils;
import com.ob.httplibrary.vo.BaseVo;
import com.ob.httplibrary.vo.FilesToUpload;
import com.ob.httplibrary.vo.NameValuePair;

import java.io.Reader;
import java.util.List;

public class FormHttpCaller extends AsyncTask<Void, Void, ResponseVo> {
    // private static final String TAG = HttpCaller.class.getSimpleName();

    public static final String ACCESS_TOKEN_KEY = "access_token";


    public static AlertDialog mAlertDia;
    private String mUrl = null;
    private int mApiType = 0;
    private List<NameValuePair> mPostData = null;
    private List<FilesToUpload> mFilesToUpload;
    private Reader mReader = null;
    private HttpResponseHandler mListener = null;
    private Context mContext = null;
    private int mRequestCode;
    private boolean mIsTaskRunning = false;
    private boolean isAuthorized = true;

    /**
     * This is the http web service caller that extends AsyncTask to perform long running task in background.
     *
     * @param pContext     The context of the activity.
     * @param pUrl         The url that we want to call.
     * @param pPostData    Post data as list of NameValuePair.
     * @param pRequestCode The request code used to identify the request when we get the response.
     * @param pListener    The response listener.
     */
    public FormHttpCaller(final Context pContext, final String pUrl, int apiTYpe, final List<NameValuePair> pPostData, final int pRequestCode, final HttpResponseHandler pListener) {
        this(pContext, pUrl, apiTYpe, pPostData, null, pRequestCode, pListener, true);
    }

    public FormHttpCaller(final Context pContext, final String pUrl, int apiTYpe, final List<NameValuePair> pPostData, final int pRequestCode, final HttpResponseHandler pListener, boolean addToken) {
        this(pContext, pUrl, apiTYpe, pPostData, null, pRequestCode, pListener, addToken);
    }

    public FormHttpCaller(final Context pContext, final String pUrl, int apiTYpe, final List<NameValuePair> pPostData, final List<FilesToUpload> filesToUpload, final int pRequestCode, final HttpResponseHandler pListener) {
        this(pContext, pUrl, apiTYpe, pPostData, filesToUpload, pRequestCode, pListener, true);
    }

    public FormHttpCaller(final Context pContext, final String pUrl, int apiTYpe, final List<NameValuePair> pPostData, final List<FilesToUpload> filesToUpload, final int pRequestCode, final HttpResponseHandler pListener, boolean addToken) {
        mUrl = pUrl;
        mPostData = pPostData;
        mListener = pListener;
        mContext = pContext;
        mRequestCode = pRequestCode;
        this.mFilesToUpload = filesToUpload;

        // Here add all common key-value pair which are used in each and every web services, in each web service calling.
        if (addToken && LibUtils.checkUserAsLogin(mContext)) {
            // WS is POST.
            if (apiTYpe == APIType.METHOD_POST && mPostData != null) {
                mPostData.add(new NameValuePair(ACCESS_TOKEN_KEY, getAccessToken(pContext)));
            }
            // WS is GET.
            else {
                // Url like "http://172.16.99.231/afteryou/web/app_dev.php/api/v1/get-gallery?dateTime=2014-09-29 09:09:09"
                if (mUrl.contains("?")) {
                    mUrl = mUrl + "&" + ACCESS_TOKEN_KEY + "=" + getAccessToken(pContext);
                }
                // Url like "http://172.16.99.231/afteryou/web/app_dev.php/api/v1/get-gallery"
                else {
                    mUrl = mUrl + "?" + ACCESS_TOKEN_KEY + "=" + getAccessToken(pContext);
                }
            }
        }

        if (mUrl.contains(" ")) {
            mUrl = mUrl.replace(" ", "%20");
        }
    }

    public static String getAccessToken(Context pContext) {
        return AppSetting.getString(pContext, KeyConstant.PREF_ACCESS_TOKEN, "");
    }


    /**
     * A custom execute method to run the AsyncTask.
     */
    public void execute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            super.execute();
        }
    }

    @Override
    protected ResponseVo doInBackground(Void... params) {
        ResponseVo responseVo = new ResponseVo();
        try {
            if (!isCancelled()) {
                if (mApiType == APIType.METHOD_POST && mPostData != null) {
                    if (mFilesToUpload == null)
                        mReader = new FormHttpConnection().getInputStream(mContext, mUrl, mPostData);
                    else
                        mReader = new FormHttpConnection().getInputStream(mContext, mUrl, mPostData, mFilesToUpload);
                } else
                    mReader = new FormHttpConnection().getInputStream(mContext, mUrl + attachedGetParams(mPostData));

            }
            if (!isCancelled()) {
                responseVo.reader = mReader;
            }

        } catch (CustomException p_e) {
            if (p_e.getErrorCode() != ExceptionConstant.ERROR_CODE_UNAUTHORIZE) {
                isAuthorized = true;
                CustomLogHandler.printError(p_e);
                if (!isCancelled()) {
                    BaseVo commonVo = new BaseVo();
                    commonVo.setCustomException(p_e);
                    responseVo.baseVo = commonVo;
                }
            } else {
                isAuthorized = false;
            }
        }
        return responseVo;
    }



    @Override
    protected void onPreExecute() {
        mIsTaskRunning = true;
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ResponseVo pResult) {
        mIsTaskRunning = false;
        super.onPostExecute(pResult);
        // user is authorized
        if (pResult.reader!=null)
        {
            mListener.onSuccess(pResult.reader,mRequestCode);
        }else {

            if (isAuthorized)
                mListener.onFail(pResult.baseVo, mRequestCode);
            else {
                // Un-Authorized user. Logout
                //  showAlert(mContext);
            }
        }
    }

    private String attachedGetParams(List<NameValuePair> pPostData) {
        StringBuilder setMethodParams = new StringBuilder();
        if (pPostData != null && pPostData.size() > 0) {
            setMethodParams.append("?");
            int counter = 0;
            for (NameValuePair valuePair : pPostData) {
                counter++;
                setMethodParams.append(valuePair.getName() + "=" + valuePair.getValue());
                if (counter < pPostData.size())
                    setMethodParams.append("&");
            }
        }
        return setMethodParams.toString();
    }

    public boolean isTaskRunning() {
        return mIsTaskRunning;
    }

    public void setIsTaskRunning(boolean pIsTaskRunning) {
        this.mIsTaskRunning = pIsTaskRunning;
    }
}
