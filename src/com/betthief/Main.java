
package com.betthief;

import com.betthief.adapter.TwelvebetCom;
import com.betthief.dal.Http;
import com.betthief.domain.Coeficent;
import com.betthief.domain.Game;
import com.betthief.util.Output;
import com.betthief.util.TextIO;
import com.betthief.util.UI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Main {

    private static final int REPEAT_CYCLE_TIME_MS = 5 * 1000;
    public static final boolean IS_DEBUG = true;
    private static Thread thread;

    public static void main(String[] args) {

        System.out.println("Use 'S' to stop");

        thread = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {

                    String htmlResponseBody = Http.request(TwelvebetCom.SOURCE);

                    ArrayList<Game> liveGames = TwelvebetCom
                            .parse(htmlResponseBody);

                    double avgP1Win = UI.displayAverage(liveGames);

                    if (!IS_DEBUG)
                        Output.startBrowser(avgP1Win);

                    try {
                        System.out.println("REFRESH: "
                                + Calendar.getInstance().getTime().toString());
                        Thread.sleep(REPEAT_CYCLE_TIME_MS);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        });

        thread.start();

        if (TextIO.getAnyChar() == 's' || TextIO.getAnyChar() == 'S') {
            System.out.println("app stopped");
            thread.stop();
        }

    }

}
