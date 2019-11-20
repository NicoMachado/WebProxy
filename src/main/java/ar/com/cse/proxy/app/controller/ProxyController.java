package ar.com.cse.proxy.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

@Controller
public class ProxyController {
	private static final String      CONTENT_ENCODING_GZIP = "gzip";

	//On a GET request, it should make a get request to <url>
	@GetMapping(value="/proxy/**")
	@ResponseBody
	public String index(HttpServletRequest request) throws IOException {

		//..Getting URL in parameters
		String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		
		String proxyUrl = new AntPathMatcher().extractPathWithinPattern(pattern, 
		        request.getServletPath());
		
		proxyUrl = proxyUrl.replace("http:/", "http://");
		proxyUrl = proxyUrl.replace("https:/", "https://");
		
		System.out.println(proxyUrl);
		
		//..doing a GET request.
		URL url;

		url = new URL(proxyUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		System.out.println("Inboud Request Headers:");
	    Enumeration<String> names = request.getHeaderNames();
	    while (names.hasMoreElements()) {
	      String name = (String) names.nextElement();
          if (!name.toLowerCase().equals(new String("cookie"))) {
    	      Enumeration<String> values = request.getHeaders(name); // support multiple values
    	      if (values != null) {
    	        while (values.hasMoreElements()) {
    	          String value = (String) values.nextElement();
    	          con.setRequestProperty(name, value);
    	  			System.out.println("\tName: " +  name + "\tValue: " + value);
    	          
    	        }
    	      }
          }
	    }


	    if (request.getHeader("Accept") == null)
	    	con.setRequestProperty("Accept", "*/*");				//Force Accept header to */*
	    else 
	    	con.setRequestProperty("Accept", request.getHeader("Accept")); 

	    con.setRequestProperty("User-Agent", request.getHeader("User-Agent")); 			//By pass User-Agent
	    
		con.setRequestMethod("GET");

		System.out.println("Connecting to : " + proxyUrl);
		
		//Content Encoding
		String contentEncoding = con.getContentEncoding();
		
		BufferedReader in = null;
		
		if (CONTENT_ENCODING_GZIP.equalsIgnoreCase(contentEncoding)) {
			in = new BufferedReader(
					new InputStreamReader(new GZIPInputStream(con.getInputStream())));
        } else {
        	in = new BufferedReader(
    				new InputStreamReader(con.getInputStream()));
        }
		
		
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine).append(System.getProperty("line.separator"));
		    System.out.println(inputLine);
		}
		
		in.close();
		con.disconnect();		

		//Respond with return from URL
		return content.toString();
		
	}
	

	//On a POST request, it should make a post request to <url>, passing through any form data
	@RequestMapping(value="/proxy/**", method=RequestMethod.POST)
	@ResponseBody
	public String doPOST(HttpServletRequest request) throws IOException {

		String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		
		String proxyUrl = new AntPathMatcher().extractPathWithinPattern(pattern, 
		        request.getServletPath());
		
		proxyUrl = proxyUrl.replace("http:/", "http://");
		proxyUrl = proxyUrl.replace("https:/", "https://");
		
		System.out.println(proxyUrl);

		//Retrieve form data ( -d asf=blah )
		// and prepare POST data.
		StringBuilder postData = new StringBuilder();
		Map<String, String[]> parameterMap = request.getParameterMap();
		for(Map.Entry<String,String[]> values : parameterMap.entrySet()) {
			System.out.println(values.getKey());
			String[] value = values.getValue();
			System.out.println(value[0]);
			
			if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(values.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(value[0]), "UTF-8"));
			
		}
		
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		
		//..doing a POST request.
		URL url;

		url = new URL(proxyUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("User-Agent", request.getHeader("User-Agent")); //By pass User-Agent
		con.setRequestProperty("Accept", "*/*");			//Force Accept header to */* 
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		con.setDoOutput(true);
        con.getOutputStream().write(postDataBytes);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine).append(System.getProperty("line.separator"));
		}
		in.close();
		con.disconnect();		

		//Return Response from URL
		return content.toString();
		
	}
	
	@RequestMapping("/")
	@ResponseBody
	public String root() {
		System.out.println("sin URL");
		return "missing url parameter!!!";
	}

}
