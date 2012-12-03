package com.betthief;

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

	private final static String TWELVEBET = "http://eu.12bet.com/"
			+ "EuroSite/Match_data.aspx?Scope=Sport&Id=0&Sport=0&Market=l&"
			+ "RT=W&CT=12%2F01%2F2012+10%3A25%3A45&Game=0&OddsType=1";
	private static int members;
	private static double avgP1Win;
	private static double avgX;
	private static double avgP2Win;
	private static WebDriver driver;

	// < >
	public static void main(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				while(true){
				String htmlResponseBody = httpRequest();

				ArrayList<Game> liveGames = parseGames(htmlResponseBody);

				displayData(liveGames);

				runBrowser();

				System.out.println("REFRESH: " + Calendar.getInstance().getTime().toString());
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
			}
		}).start();

	}

	private static void runBrowser() {
		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();

		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		if (driver == null) {
			driver = new FirefoxDriver();
			// And now use this to visit Google
			driver.get("http://www.google.com");
		}
		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));
		// Enter something to search for
		element.clear();
		element.sendKeys("" + avgP1Win / members);
	}

	private static double displayData(ArrayList<Game> liveGames) {
		avgP1Win = 0.0;
		avgX = 0.0;
		avgP2Win = 0.0;
		for (Game game : liveGames) {
			System.out.println(game);

			Coeficent coeficent = game.getCoeficent();
			avgP1Win += coeficent.getPlayerOneWin();
			avgX += coeficent.getX();
			avgP2Win += coeficent.getPlayerTwoWin();

		}

		members = liveGames.size();
		System.out
				.println("========================================================");
		System.out.println("AVG P1WIN:" + avgP1Win / members + " X:" + avgX
				/ members + " P2WIN:" + avgP2Win / members);
		return avgP1Win;
	}

	// private static String gzipDecode(String htmlResponse) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	private static ArrayList<Game> parseGames(String htmlResponse) {

		String[] rawRows = htmlResponse.replace("'", "").replace("[", "")
				.replace("]", "").split(";");

		ArrayList<Game> list = new ArrayList<Game>();

		// ArrayList<String[]> rows = new ArrayList<String[]>();

		// String[][] all = new String[rawRows.length] [100];
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

					if (game != null && elements[25].trim().equals("5")) {
						game.setCoeficent(new Coeficent(
								new Double(elements[27]), new Double(
										elements[28]), new Double(elements[29])));

						list.add(game);

					}
				}

			// all[i] = elements;

		}
		System.out.println(":" + list.size());
		// TODO Auto-generated method stub
		return list;
	}

	private static String httpRequest() {

		String dataAsString = "";

		try {
			HttpGet get = new HttpGet(TWELVEBET);
			get.setHeader("User-Agent", "Mozilla Firefox");
			get.setHeader("Cookie",
					"ASP.NET_SessionId=vsrl0vavug5lfh45dqugm5f3");

			HttpResponse response = new DefaultHttpClient().execute(get);
			StatusLine status = response.getStatusLine();

			if (status.getStatusCode() != 200)
				throw new IOException("Invalid response from server: "
						+ status.toString());

			dataAsString = IOUtils.toString(response.getEntity().getContent(),
					"UTF-8");

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.print("DEBUG: " + dataAsString.length());
		// TODO Auto-generated method stub
		return dataAsString;
	}

}
