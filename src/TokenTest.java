import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


public class TokenTest {
		
	private final String localhost = "http://127.0.0.1:8000/";
	private final String webserver = "http://128.197.103.77/";
	
	private JSONObject token = null;
	
	public TokenTest(){
		
	}
	
	
	// HTTP GET request
	public void sendGet() throws Exception {
		
		String api_getProjects = "api/projects/";
		
		String url = webserver + api_getProjects;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Authorization", "JWT " + getTokenValue());

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in;
		if (responseCode >= 400)
		{
			in = new BufferedReader(
		        new InputStreamReader(con.getErrorStream()));
		}
		else
		{
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	// HTTP POST request
	public void getToken() throws Exception {

		String url = webserver + "get-token/";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();		
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		String urlParameters = "";
		
		// Send post request
		con.setDoOutput(true);
		con.setDoInput(true);
		
		JSONObject credentials = new JSONObject();
		credentials.put("username", "josh");
		credentials.put("password", "josh");
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(credentials.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		
		System.out.println(con.getResponseMessage().toString());
		System.out.println(con.getHeaderFields().toString());
		System.out.println(con.getContentLength());

		BufferedReader in;
		if (responseCode >= 400)
		{
			in = new BufferedReader(
		        new InputStreamReader(con.getErrorStream()));
		}
		else
		{
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		token = new JSONObject(response.toString());

	}
	
	public String getTokenValue()
	{
		String response;
		try {
			response = token.get("token").toString();
		} catch (JSONException e) {
			response = null;
			e.printStackTrace();
		} 
		return response;
	}
	

	public static void main(String[] args) {
		TokenTest test = new TokenTest();

		try {
			test.getToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(test.getTokenValue());
		
		try {
			test.sendGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
