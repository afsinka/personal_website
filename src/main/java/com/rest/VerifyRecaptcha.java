package com.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class VerifyRecaptcha {

	public static final String url = "https://www.google.com/recaptcha/api/siteverify";

	private static final Logger logger = LogManager.getLogger(VerifyRecaptcha.class);

	public static boolean verify(String gRecaptchaResponse, String secret) throws IOException {
		if (gRecaptchaResponse == null || (gRecaptchaResponse != null && gRecaptchaResponse.isEmpty())) {
			return false;
		}

		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoOutput(true);

			String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;

			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
			dos.writeBytes(postParams);
			dos.flush();
			dos.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject jsonObject = new JSONObject(response.toString());
			Boolean result = (Boolean) jsonObject.get("success"); // true or false

			return result;

		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}
}
