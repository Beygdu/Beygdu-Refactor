package is.example.aj.beygdu.Skrambi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arnar on 2/11/2016.
 */
public class RequestHandler {

    private HttpURLConnection conn = null;

    private String targetUrl;
    private String urlParam;
    private String contentType;
    private String contentLang;
    private boolean useCaches;
    private boolean doInput;
    private boolean doOutput;

    private String contentLength;

    public RequestHandler(String targetUrl, String urlParam,
                          String contentType, String contentLang,
                          boolean useCaches, boolean doInput, boolean doOutput) {
        this.targetUrl = targetUrl;
        this.urlParam = urlParam;
        this.contentType = contentType;
        this.contentLang = contentLang;
        this.useCaches = useCaches;
        this.doInput = doInput;
        this.doOutput = doOutput;
        this.contentLength = Integer.toString(urlParam.getBytes().length);

    }

    public String sendRequest() {

        String response;
        StringBuffer result;

        try {
            URL url = new URL(targetUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Content-Length", contentLength);
            conn.setRequestProperty("Content-Language", contentLang);
            conn.setUseCaches(useCaches);
            conn.setDoInput(doInput);
            conn.setDoOutput(doOutput);

            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(urlParam);
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuffer();
            while( (response = bufferedReader.readLine()) != null ) {
                result.append(response);
                //result.append('\r');
            }
            bufferedReader.close();

            return  result.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
        finally {
            if(conn != null) conn.disconnect();
        }
    }
}
