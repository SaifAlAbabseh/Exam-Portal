package examportal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class GeoInfo {
	private HashMap<String, String> geo;
	private String ip;
	private String timezone;
	private String date_time;
	private String time_24;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime_24() {
		return time_24;
	}

	public void setTime_24(String time_24) {
		this.time_24 = time_24;
	}

	public String getIp() {
		return ip;
	}

	public HashMap<String, String> getGeo() {
		return geo;
	}

	public void setGeo(HashMap<String, String> geo) {
		this.geo = geo;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
}

public class GeoMain {
	private final String apiKey;
	private HttpRequest request;
	private HttpClient client;
	private HttpResponse<String> result;
	private GeoInfo parent;
	
	public GeoMain() throws Exception{
		apiKey = getAPIKey();
		
		if(apiKey == null) {
			System.exit(0);
		}
		
		request = HttpRequest.newBuilder().uri(URI.create("https://api.ipgeolocation.io/getip")).build();
		client = HttpClient.newHttpClient();
		result = client.send(request, BodyHandlers.ofString());

		GeoInfo gi1 = getNewGeoInfoObject(result);

		request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.ipgeolocation.io/timezone?ip=" + gi1.getIp() + "&apiKey=" + apiKey))
				.build();
		client = HttpClient.newHttpClient();
		result = client.send(request, BodyHandlers.ofString());

		parent = getNewGeoInfoObject(result);
	}
	
	public int[] getTime(){ // returns time {hour, minute, second} as an array of integers
		String[] time = parent.getTime_24().split(":");
		return new int[]{Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2])};
	}
	
	public int[] getDate(){ // returns date {year, month, day} as an array of integers
		String[] date = parent.getDate().split("-");
		return new int[]{Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])};
	}
	
	private GeoInfo getNewGeoInfoObject(HttpResponse<String> result) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();

		Gson gson = builder.create();
		GeoInfo geoInfo = gson.fromJson(result.body(), GeoInfo.class);
		
		return geoInfo;
	}
	
	private String getAPIKey() {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();
			String query = "SELECT time_api_key FROM auth_info";
			ResultSet result = stmt.executeQuery(query);
			if(result.next()) {
				return result.getString(1);
			}
			conn.close();
			stmt.close();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}
