
package com.betthief.adapter;

import com.betthief.Main;
import com.betthief.dal.Http;
import com.betthief.domain.Coeficent;
import com.betthief.domain.Game;
import com.betthief.util.UAString;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TwelvebetCom {

    public final static String URL = "http://eu.12bet.com/";
    public final static String SOURCE = URL
            + "EuroSite/Match_data.aspx?Scope=Sport&Id=0&Sport=0&Market=l&RT=W&CT=12%2F01%2F2012+10%3A25%3A45&Game=0&OddsType=1";

    public static ArrayList<Game> parse(String htmlResponse) {

        String[] rawRows = htmlResponse.replace("'", "").replace("[", "")
                .replace("]", "").split(";");

        ArrayList<Game> list = new ArrayList<Game>();

        Game game = null;

        for (int i = 0; i < rawRows.length; i++) {

            String[] elements = rawRows[i].split(",");

            if (elements.length >= 30)
                if (!elements[6].trim().equals("")) {
                    // is header
                    // N[0]=['002986625','2986625','0020121201000003','1','3','*ENGLISH
                    // PREMIER LEAGUE','Arsenal','Swansea
                    // City','201212011059','2754950',1,'0','Live
                    // 79\'',0,'','True',2,'0',0,0,'0','0','0','3','1',1,'25292556','0.0','1.320','3.500',''];

                    // 6==league name
                    // 7==player1
                    // 8==player2
                    // 13==time

                    game = new Game(elements[5], elements[6], elements[7],
                            elements[12]);

                } else {
                    // is coeficent

                    // if 25 == 5
                    // N[4]=['','','','1','','','','','','',,'','',0,'','True',2,'0',0,0,'0','0','0','3','1',5,'25292552','3.80','1.45','10.29'];
                    // 27 28 29

                    if (elements[25].trim().equals("5")) {
                        game.setCoeficent(new Coeficent(
                                new Double(elements[27]), new Double(
                                        elements[28]), new Double(elements[29])));

                        list.add(game);

                    }
                }

            // all[i] = elements;

        }

        System.out.println("returned list size: " + list.size());

        return list;
    }

    public static List<String> getCookies() {

        HttpGet get = new HttpGet(URL);
        get.setHeader(UAString.UA_HEADER, UAString.FIREFOX.toString());

        try {
            HttpResponse response = new DefaultHttpClient().execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                Header[] headers = response.getAllHeaders();

                List<String> result = new ArrayList<String>();

                for (Header header : headers) {
                    if (header.getName().equals(Http.HTTP_HEADER_SET_COOKIE)) {
                        result.add(header.getValue().split(";")[0]);

                        if (Main.IS_DEBUG)
                            System.out.println(Http.HTTP_HEADER_SET_COOKIE
                                    + ":" + header.getValue().split(";")[0]);

                    }
                }

                return result;

            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ArrayList<String>();

    }

}
