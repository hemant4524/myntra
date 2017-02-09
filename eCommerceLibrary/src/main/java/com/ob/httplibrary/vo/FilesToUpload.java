package com.ob.httplibrary.vo;

import java.io.File;

public class FilesToUpload {

    private File mFile;
    private String mFilePath;
    private String mUploadName;

    public FilesToUpload() {
    }

    public FilesToUpload(String pFilePath, String pUploadName) {
        this.mFilePath = pFilePath;
        this.mUploadName = pUploadName;
    }

    public FilesToUpload(File pFile, String pUploadName) {
        this.mFile = pFile;
        this.mUploadName = pUploadName;
    }

    /**
     * @return the mFile
     */
    public final File getFile() {
        return mFile;
    }

    /**
     * @param mFile the mFile to set
     */
    public final void setFile(File mFile) {
        this.mFile = mFile;
    }

    /**
     * @return the mFilePath
     */
    public final String getFilePath() {
        return mFilePath;
    }

    /**
     * @param mFilePath the mFilePath to set
     */
    public final void setFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }

    /**
     * @return the mUploadName
     */
    public final String getUploadName() {
        return mUploadName;
    }

    /**
     * @param mUploadName the mUploadName to set
     */
    public final void setUploadName(String mUploadName) {
        this.mUploadName = mUploadName;
    }

}
