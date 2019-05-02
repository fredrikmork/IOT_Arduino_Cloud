package no.hvl.dat110.aciotdevice.client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RestClient {

	public RestClient() {
		//  Auto-generated constructor stub

	}

	private static String logpath = "/accessdevice/log";
	private static int port = 8080;
	private static String host = "localhost";

	public void doPostAccessEntry(String message) {

		// implement a HTTP POST on the service to post the message
		Gson gson = new Gson();

		String json = gson.toJson(new AccessMessage(message));

		try (Socket s = new Socket(host, port)) {

			// construct the GET request
			String httppostrequest = "POST " + logpath + " HTTP/1.1\r\n" +
					"Host: " + host + "\r\n" +
					"Content-type: application/json\r\n" +
					"Content-length: " + json.length() + "\r\n" +
					"Connection: close\r\n" +
					"\r\n" +
					json +
					"\r\n";

			// sent the HTTP request
			OutputStream output = s.getOutputStream();

			PrintWriter pw = new PrintWriter(output, false);

			pw.print(httppostrequest);
			pw.flush();

			// read the HTTP response
			InputStream in = s.getInputStream();

			Scanner scan = new Scanner(in);
			StringBuilder jsonresponse = new StringBuilder();
			boolean header = true;

			while (scan.hasNext()) {

				String nextline = scan.nextLine();

				if (header) {
					System.out.println(nextline);
				} else {
					jsonresponse.append(nextline);
				}

				// simplified approach to identifying start of body: the empty line
				if (nextline.isEmpty()) {
					header = false;
				}

			}

			System.out.println("BODY:");
			System.out.println(jsonresponse.toString());

			scan.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {
		// implement a HTTP GET on the service to get current access code
		AccessCode code = null;

		try (Socket s = new Socket(host, port)) {
			Gson gson = new Gson();
			// construct the GET request
			String httpgetrequest = "GET " + codepath + " HTTP/1.1\r\n" + "Accept: application/json\r\n"
					+ "Host: localhost" + "\r\n" + "Connection: close\r\n" + "\r\n";

			// sent the HTTP request
			OutputStream output = s.getOutputStream();

			PrintWriter pw = new PrintWriter(output, false);

			pw.print(httpgetrequest);
			pw.flush();

			// read the HTTP response
			InputStream in = s.getInputStream();

			Scanner scan = new Scanner(in);
			StringBuilder jsonresponse = new StringBuilder();
			boolean header = true;

			while (scan.hasNext()) {

				String nextline = scan.nextLine();

				if (header) {
					System.out.println(nextline);
				} else {
					jsonresponse.append(nextline);
				}

				// simplified approach to identifying start of body: the empty line
				if (nextline.isEmpty()) {
					header = false;
				}

			}

			System.out.println("BODY:");
			System.out.println(jsonresponse.toString());
			code = gson.fromJson(jsonresponse.toString(), AccessCode.class);
			scan.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

		return code;
	}
}
