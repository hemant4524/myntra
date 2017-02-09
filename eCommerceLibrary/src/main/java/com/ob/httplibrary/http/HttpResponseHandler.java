package com.ob.httplibrary.http;

import com.ob.httplibrary.vo.BaseVo;

import java.io.Reader;

/**
 * This class is used to get the response back to the caller class.
 */
public interface HttpResponseHandler {
//    public void onResponse(final BaseVo pResponseData, final CustomException p_e, final int pRequestCode);
    public void onFail(final BaseVo baseVo, final int pRequestCode);

    public void onSuccess(Reader reader, int mRequestCode);

//    public void onSucess()
}
