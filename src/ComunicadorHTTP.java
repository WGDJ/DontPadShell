import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class ComunicadorHTTP {

	private static final String USER_AGENT = "Mozilla/5.0";

	private static HttpURLConnection getHttpUrlConnection(String url)
			throws IOException {
		if (url.startsWith("https")) {
			URL obj = new URL(url);
			return (HttpsURLConnection) obj.openConnection();
		} else if (url.startsWith("http")) {
			URL obj = new URL(url);
			return (HttpURLConnection) obj.openConnection();
		}
		return null;
	}

	/**
	 * Pega o conteúdo através da API JSON do DontPad (Não pega a quebra de linha)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String sendGetFomJson(String url) throws Exception {
	
		url += ".body.json?lastUpdate=0";

		HttpURLConnection con = getHttpUrlConnection(url);

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		//int responseCode = con.getResponseCode();

		JSONObject json = Util.getJsonFromString(Util.getString(con.getInputStream()));
		
		return json.get("body").toString();

	}
	
	public static void sendPost(String url, Map<String, String> parametros)
			throws Exception {

		HttpURLConnection con = getHttpUrlConnection(url);

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		StringBuilder urlParameters = new StringBuilder();
		for (Entry<String, String> entry : parametros.entrySet()) {
			urlParameters.append(entry.getKey());
			urlParameters.append("=");
			urlParameters.append(entry.getValue());
			urlParameters.append("&");
		}

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters.toString().substring(0,
				urlParameters.toString().length() - 1));
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

	}

	

}
