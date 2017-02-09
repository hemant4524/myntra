package com.ob.httplibrary.http;

import android.content.Context;
import android.util.Log;

import com.andoblib.constant.Constants;
import com.andoblib.customexception.CustomException;
import com.andoblib.customexception.ExceptionConstant;
import com.andoblib.http.HTTPConnection;
import com.andoblib.http.HTTPConstant;
import com.andoblib.log.CustomLogHandler;
import com.andoblib.log.LogConstant;
import com.ob.httplibrary.R;
import com.ob.httplibrary.vo.FilesToUpload;
import com.ob.httplibrary.vo.NameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * This is the form http connection class used to post the form data on the server.
 */
public class FormHttpConnection {
    /**
     * Default charset used, if not provided
     */
    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * Line separator
     */
    private static final String CRLF = "\r\n"; // Line separator required by multipart/form-data.
    private static final String TAG = FormHttpConnection.class.getSimpleName();

    // ====================Post Methods
    public InputStreamReader getInputStream(final Context pContext, final String pUrl, final List<NameValuePair> pPostData) throws CustomException {
        return getInputStream(pContext, pUrl, pPostData, null);
    }


    public InputStreamReader getInputStream(final Context pContext, final String pUrl, final List<NameValuePair> pPostData, final List<FilesToUpload> filesToUpload) throws CustomException {
        int responseCode;
        Log.v(TAG, "debug:-"+LogConstant.DEBUG);
        CustomLogHandler.printDebug(TAG, "URL  ==  " + pUrl);
        // Throw exception if there is no internet connection.
        if (!HTTPConnection.isNetConnected(pContext)) {
            throw new CustomException(Constants.ERR_NO_INTERNET_CONNECTION, ExceptionConstant.ERROR_CODE_NO_INTERNET_CONNECT);
        }

        HttpURLConnection conn;
        // boundary
        String boundary = Long.toHexString(System.currentTimeMillis());
        // Content type for multipart/form-data with a pre-defined boundary
        String contentTypeFormData = HTTPConstant.CONTENT_TYPE_MULTIPART + "; boundary=" + boundary;

        boolean hasFile = false;
        try {
            URL url = new URL(pUrl);
            conn = (HttpURLConnection) url.openConnection();
            String mMethod;
            conn.setRequestProperty("Content-Type", HTTPConstant.CONTENT_TYPE_URLENCODED);
            // For POST method
            if (pPostData != null || filesToUpload != null) {
                mMethod = "POST";
                conn.setChunkedStreamingMode(0);
                conn.setDoOutput(true);
                // multipart/form-data
                if (filesToUpload != null) {
                    conn.setRequestProperty("Content-Type", contentTypeFormData);
                    conn.setRequestProperty("Connection", "keep-alive");
                    hasFile = true;
                }
            }
            // For GET method
            else {
                mMethod = "GET";
                conn.setDoOutput(false);
            }
            conn.setRequestProperty("charset", DEFAULT_CHARSET);
            conn.setRequestMethod(mMethod);
            conn.setDoInput(true);

            conn.setConnectTimeout(HTTPConstant.CONNECTION_TIME_OUT);
            conn.setReadTimeout(HTTPConstant.READ_TIME_OUT);

            // POST
            if (pPostData != null || filesToUpload != null) {
                OutputStream output = conn.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, DEFAULT_CHARSET), true);

                //noinspection TryFinallyCanBeTryWithResources
                try {
                    // POST parameters
                    if (pPostData != null) {
                        addParametersToConnection(writer, pPostData, boundary, hasFile);
                    }
                    // POST Files
                    if (filesToUpload != null) {
                        addFilesToConnection(writer, output, filesToUpload, boundary);
                        // multipart/form-data end
                        writer.append(CRLF).flush();
                        writer.append("--").append(boundary).append("--").append(CRLF).flush();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    output.close();
                    writer.close();
                }
            }

            responseCode = conn.getResponseCode();
            InputStream is;

            if (responseCode != 200) {
                is = conn.getErrorStream();
            } else {
                is = conn.getInputStream();
            }
            //            Log.d(TAG, "Response code ==  " + responseCode);
            // Just Print the response.
            //   ResponseParser.getStringResponse(new InputStreamReader(is));
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                return new InputStreamReader(is);
            }
            // Access token was not valid or expired
            else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new CustomException(pContext.getString(R.string.err_status_code, String.valueOf(responseCode)), ExceptionConstant.ERROR_CODE_UNAUTHORIZE);
            }
            // If status is not ok, then throw the exception.
            else {
                //TODO: Error handling
                if (responseCode == HttpURLConnection.HTTP_NOT_FOUND)
                    throw new CustomException(pContext.getString(R.string.err_status_code, String.valueOf(responseCode)), ExceptionConstant.ERROR_CODE_CUSTOM);
                else
                    return new InputStreamReader(is);
//                throw new CustomException(pContext.getString(R.string.err_status_code, String.valueOf(responseCode)), ExceptionConstant.ERROR_CODE_CUSTOM);
            }
        } catch (SocketTimeoutException p_e) {
            CustomLogHandler.printError(p_e);
            throw new CustomException(Constants.ERR_CONNECTION_TIMEOUT, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
        } catch (ProtocolException p_e) {
            CustomLogHandler.printError(p_e);
            throw new CustomException(Constants.ERR_SOMETHING_WENT_WRONG, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
        } catch (FileNotFoundException p_e) {
            CustomLogHandler.printError(p_e);
            throw new CustomException(pContext.getString(R.string.err_file_no_available), p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
        } catch (IOException p_e) {
            CustomLogHandler.printError(p_e);
            throw new CustomException(Constants.ERR_SOMETHING_WENT_WRONG, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
        } catch (CustomException p_e) {
            CustomLogHandler.printError(p_e);
            throw p_e;
        } catch (Throwable p_e) {
            CustomLogHandler.printError(p_e);
            throw new CustomException(Constants.ERR_SOMETHING_WENT_WRONG, p_e, ExceptionConstant.ERROR_CODE_CUSTOM);
        }
    }

    public InputStreamReader getInputStream(final Context pContext, final String pUrl) throws CustomException {
        return getInputStream(pContext, pUrl, null);
    }

    /**
     * Method to add Post parameter to the URLConnection
     *
     * @param writer    - PrintWriter to add parameter to
     * @param pPostData - List of data to post
     * @param hasFile   variable to determine whether to use multipart/form-data or application/x-www-form-urlencoded
     * @throws IOException - If parameter cannot be encoded using UTF-8 encoding or
     *                     there's some exception while writing to the OutputStream of
     *                     the UrlConnection
     */
    private void addParametersToConnection(PrintWriter writer, List<NameValuePair> pPostData, String boundary, boolean hasFile) throws IOException {
        // Multipart form data
        if (hasFile) {
            for (NameValuePair nVP : pPostData) {
                //                addFormField(writer, nVP.getName(), nVP.getValue());
                // Send normal param.
                writer.append("--").append(boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"").append(nVP.shouldEncode() ? URLEncoder.encode(nVP.getName(), DEFAULT_CHARSET) : nVP.getName()).append("\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=").append(DEFAULT_CHARSET).append(CRLF);
                writer.append(CRLF).append(nVP.shouldEncode() ? URLEncoder.encode(nVP.getValue(), DEFAULT_CHARSET) : nVP.getValue()).append(CRLF).flush();
            }
        }
        //application/x-www-form-urlencoded
        else {
            StringBuilder request = new StringBuilder();
            boolean isFirst = true;
            for (NameValuePair nVP : pPostData) {
                CustomLogHandler.printVerbose(TAG, "Key----" + (nVP.shouldEncode() ? URLEncoder.encode(nVP.getName(), DEFAULT_CHARSET) : nVP.getName()));
                CustomLogHandler.printVerbose(TAG, "Value----" + (nVP.getValue() == null ? "" : (nVP.shouldEncode() ? URLEncoder.encode(nVP.getValue(), DEFAULT_CHARSET) : nVP.getValue())));

                if (isFirst)
                    isFirst = false;
                else
                    request.append("&");
                request.append(nVP.shouldEncode() ? URLEncoder.encode(nVP.getName(), DEFAULT_CHARSET) : nVP.getName());
                request.append("=");
                request.append(nVP.getValue() == null ? "" : (nVP.shouldEncode() ? URLEncoder.encode(nVP.getValue(), DEFAULT_CHARSET) : nVP.getValue()));
            }


            // Log.v(TAG,"Request:--"+request.toString());

           /* if (request.toString().contains("fax_proof")) {
                String json = "{\"order_type\":\"sample\",\"fax_proof\":\"null\",\"product_slug\":\"6-natural-finish-flat-wood-ruler-1\",\"imprint\":\"null\",\"special_instruction\":\"comments\",\"color\":{\"id:216\":{\"quantity\":[\"1\"]}},\"culture_product_id\":1003,\"_format\":\"json\",\"product_id\":1003,\"paper_proof\":\"null\",\"product_proof\":\"null\",\"color_attr_id\":\"10\",\"shipping_method\":{\"qunatity_detail\":{\"id:216\":{\"quantity\":[\"1\"]}},\"shipping_detail\":[{\"color_quantity\":{\"id:216\":{\"actual_quantity\":[\"1\"],\"quantity\":[\"1\"]}},\"shipping_from\":\"shipping_book\",\"shipping_detail\":{\"on_hand_date\":\"12\\/12\\/2015\",\"shipper_account\":\"123\",\"shipping_charge\":\"\",\"shipping_method\":\"\",\"shipping_carrier\":\"fedex\"},\"selected_address_id\":\"3\"}],\"shipping_type\":\"standard\"},\"_locale\":\"en_us\",\"max_selectable_color\":\"0\",\"access_token\":\"MGEzZDk5YTM0MWE3MDU4MjlkYWZhOGViN2Q3ODM4ZTUxZWM2NTFiNmY0NmZkMjkwMjcyOGE1NDMxNmI2MzVkNw\"}";
                writer.append(json).flush();
            }else*/
                writer.append(request.toString()).flush();
        }
    }

    /**
     * Method to add Files to the URLConnection
     *
     * @param writer        PrintWriter to add parameter to
     * @param filesToUpload List of files to upload
     * @param boundary      boundary of the multipart/form-data connection
     * @throws IOException If parameter cannot be encoded using UTF-8 encoding or
     *                     there's some exception while writing to the OutputStream of
     *                     the UrlConnection
     */
    private void addFilesToConnection(PrintWriter writer, OutputStream output, List<FilesToUpload> filesToUpload, String boundary) throws IOException {
        for (FilesToUpload nVP : filesToUpload) {
            if (nVP.getFile() == null && nVP.getFilePath() == null)
                continue;
            //            addFilePart(writer, output, nVP);
            File f = nVP.getFile() == null ? new File(nVP.getFilePath()) : nVP.getFile();
            // Send binary file.
            writer.append("--").append(boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"").append(nVP.getUploadName()).append("\"; filename=\"").append(f.getName()).append("\"").append(CRLF);
            writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(f.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            copyFileToOutputStream(f.getAbsolutePath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
        }
    }

    /**
     * Method to copy file data to the output stream
     *
     * @param path   File path
     * @param output - output stream
     * @throws IOException
     */
    private void copyFileToOutputStream(String path, OutputStream output) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        inputStream.close();
    }
}
