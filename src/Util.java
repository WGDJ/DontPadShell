import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Util {

	public static JSONObject getJsonFromString(String json) {
		try {
			return new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document newDocumentFromInputStream(InputStream in) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			ret = builder.parse(new InputSource(in));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String executarComando(String commando, DontPadShell dontPadShell) throws Exception {

		Process proc = null;
		
		try {
			proc = Runtime.getRuntime().exec(commando);
		} catch (IOException e) {
			dontPadShell.getDontPadPrinter().put("Comando incorreto" + DontPadPrinter.lineSeparatorBreakLine + dontPadShell.getPromptString());
		}
		
		return getString(proc.getInputStream());
		
	}
	
	public static String getString(InputStream in) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		in.close();

		return sb.toString();

	}

}
