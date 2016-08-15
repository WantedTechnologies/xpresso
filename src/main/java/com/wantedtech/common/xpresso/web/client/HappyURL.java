package com.wantedtech.common.xpresso.web.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HappyURL implements Closeable {
	
	String URL;
	URL website;
	URLConnection connection;
	BufferedReader reader;
	
	public HappyURL(String URL) throws IOException {
		this.URL = URL;
        this.website = new URL(this.URL);
        connection = website.openConnection();
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}
	
    public String readText() throws IOException {
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = reader.readLine()) != null) 
            response.append(inputLine);

        return response.toString();
    }

	@Override
	public void close() throws IOException {
		reader.close();
		
	}
}
