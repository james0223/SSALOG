package com.ssalog.dto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class webhook {
	public int postNotify(String _URL, String message, String botName) throws Exception{
		int result = 400;
		
		try {
			HttpURLConnection connection = getConnection(new URL(_URL));
			StringBuffer sb = new StringBuffer();
			sb.append("\"username\": \"" + botName + "\"}");
			connection.getOutputStream().write(sb.toString().getBytes("UTF-8"));
			connection.getInputStream();
			
			int statusCode = connection.getResponseCode();
        	result = statusCode;
        	if ( statusCode != 200 ) {
        		printHeaderFileds(connection);
        	}
	        connection.disconnect();
		} catch (Exception e) {
			throw e;
		}
		return result; 
	}
	private HttpURLConnection getConnection(URL url) throws IOException
	{
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Content-Type","application/json" );
		connection.addRequestProperty("Accept", "application/json");
		return connection;
	}
	private void printHeaderFileds(HttpURLConnection connection) {
		Map<String,List<String>> headerFields = connection.getHeaderFields();
		Set<String> keys = headerFields.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			String key = it.next();
			List<String> values = headerFields.get(key);
			StringBuffer sb = new StringBuffer();
			sb.append( key ).append( ":" );
			Iterator<String> it2 = values.iterator();
			while( it2.hasNext() ) sb.append( " " ).append( it2.next() );
			System.out.println(sb);
		}
	}
}
