package com.ob.httplibrary.vo;


import com.andoblib.customexception.CustomException;
import com.google.gson.annotations.SerializedName;

/**
 * This is the base response vo used to hold the web service response data.
 */
public class BaseVo  {

    @SerializedName("status_code")
    private int status_code;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    private CustomException customException;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomException getCustomException() {
        return customException;
    }

    public void setCustomException(CustomException customException) {
        this.customException = customException;
    }




    public BaseVo() {
    }



}
