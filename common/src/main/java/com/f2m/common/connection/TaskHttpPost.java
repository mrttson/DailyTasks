package com.f2m.common.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sev_user on 10/17/2016.
 */
public class TaskHttpPost extends AsyncTask<Void, Void, Void> {

    private final String TAG = CommonUltil.buildTag(TaskHttpPost.class);

    private String urlString;
    private ParamObject[] mParams;
    private IOnTextResultCallback mCallback;
    private int mFlag;
    private HttpPost mHttpPost;

    private URL mURL;
    private HttpURLConnection conn;
    private int mResponseCode;
    private InputStream is;

    private String mTextResult = null;

    private boolean isHasFile = false;

    public enum PARAM_TYPE {
        STRING,
        FILE
    }

    public TaskHttpPost(String url, ParamObject[] params, IOnTextResultCallback callback, int flag) {
        Log.d(TAG, "TaskHttpPost with URL: " + url);
        urlString = url;
        mParams = params;
        mCallback = callback;
        mFlag = flag; // This variable using to determine what request is handling
        for (ParamObject param :
                mParams) {
            if (param.getParamType() == PARAM_TYPE.FILE) {
                isHasFile = true;
                break;
            }
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            mHttpPost = new HttpPost(urlString);
            mHttpPost.addHeaderField(ConnectionsManager.PROPERTY_USER_AGENT, ConnectionsManager.USER_AGENT_MOZILLA_50);
            for (ParamObject param :
                    mParams) {
                if (param.getParamType() == PARAM_TYPE.STRING) {
                    mHttpPost.addFormField(param.getKey(), param.getValue());
                } else {
                    File f = new File(param.getValue());
                    if (f.exists()) {
                        mHttpPost.addFilePart(param.getKey(), f);
                    }
                }
            }
            mTextResult = mHttpPost.finish();
        } catch (Exception e) {
            Log.d(TAG, e.getClass().toString() + " : " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mCallback.onResult(mTextResult, mFlag);
    }

    private class ParamObject {

        private PARAM_TYPE mTypes;
        private String mKey;
        private String mValue;

        public ParamObject(PARAM_TYPE type, String key, String value) {
            mTypes = type;
            mKey = key;
            mValue = value;
        }

        public PARAM_TYPE getParamType() {
            return mTypes;
        }

        public String getKey() {
            return mKey;
        }

        public String getValue() {
            return mValue;
        }

    }

    public class HttpPost {
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        private StringBuilder sb = new StringBuilder();
        private BufferedReader reader;
        private String line;


        public HttpPost(String requestURL) throws IOException {
            this(requestURL, ConnectionsManager.CHARSET_UTF8);
        }

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         * @param requestURL
         * @param charset
         * @throws IOException
         */
        public HttpPost(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";

            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", ConnectionsManager.USER_AGENT_MOZILLA_50);
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         * @param name field name
         * @param value field value
         */
        public HttpPost addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
            return this;
        }

        /**
         * Adds a upload file section to the request
         * @param fieldName name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         * @param name - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public String finish() throws IOException {

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }

            return sb.toString();
        }
    }
}
