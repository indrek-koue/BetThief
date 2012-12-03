
package com.betthief.dal;

import com.betthief.Main;
import com.betthief.adapter.TwelvebetCom;
import com.betthief.util.UAString;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Http {

    private static final String CHARSET_UTF_8 = "UTF-8";
    public static final String HTTP_HEADER_SET_COOKIE = "Set-Cookie";
    public static final String HTTP_HEADER_COOKIE = "Cookie";

    private static boolean isFirstRequest = true;
    private static List<String> cookies;

    public static String request(String url) {

        if (isFirstRequest) {
            cookies = TwelvebetCom.getCookies();
            isFirstRequest = false;
        }

        HttpGet get = new HttpGet(url);
        get.setHeader(UAString.UA_HEADER, UAString.FIREFOX.toString());

        for (String s : cookies) {
            get.setHeader(HTTP_HEADER_COOKIE, s);
        }

        try {

            HttpResponse response = new DefaultHttpClient().execute(get);
            StatusLine status = response.getStatusLine();

            if (status.getStatusCode() != 200)
                throw new IOException("Invalid response from server: "
                        + status.toString());

            String dataAsString = IOUtils.toString(response.getEntity()
                    .getContent(), CHARSET_UTF_8);

            if (Main.IS_DEBUG)
                System.out.println("response length: " + dataAsString.length()
                        + " @ " + url + " ");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

}
