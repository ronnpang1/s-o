package httpcon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import argo.jdom.JdomParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONArray;
import static argo.jdom.JsonNodeFactories.*;


import com.jayway.jsonpath.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static argo.jdom.JsonNodeBuilders.*;



public class HttpConnection {
	public static void main(String[] args) throws Exception {
		String Name = null;
		HttpConnection http = new HttpConnection();
		 
		
		
 
		System.out.println("Testing  - Send Http POST request");
		String jsonline= http.sendPost();
		System.out.println(jsonline);
		JsonPath namePath = JsonPath.compile("$.name");
		String longitude = (JsonPath.read(jsonline, "$.resolvedLocations[*].geoname.longitude").toString());
	    String latitude=   (JsonPath.read(jsonline, "$.resolvedLocations[*].geoname.latitude").toString());
		System.out.println(longitude);
		System.out.println(latitude);
	}
	
	public  static String parse(String jsonLine) {
	    JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("data");
	    JsonArray jarray = jobject.getAsJsonArray("translations");
	    jobject = jarray.get(0).getAsJsonObject();
	    String result = jobject.get("translatedText").toString();
	    return result;
	}
	
	// HTTP POST request
	private String sendPost() throws Exception {
 
		String url = "http://192.168.0.104:9090/api/v0/geotag";
		String text="Ukrainian officials set conditions for receiving Russian aid in the conflict-torn east, as a convoy of food and medicine departs from near Moscow";
 
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
 
		// add header
		post.setHeader("Content-Type","text/plain");
		StringEntity text1= new StringEntity (text);
  
		post.setEntity(text1);
 
		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());
 
		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		return result.toString();
 
	}
 
	

}
