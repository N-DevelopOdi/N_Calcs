package ru.n_develop.n_calcs.Module;


import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by dim90 on 02.04.2017.
 */

public class ExportStatics extends Thread
{

	InputStream is = null;
	String response = null;
	Boolean result = false;
	String success = null;
	String line = null;

	String JsonExport;

	public void run() {
		// создаем лист для отправки запросов
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		// лист с запросом
		nameValuePairs.add(new BasicNameValuePair("JsonExport", JsonExport));

		//  подключаемся к php запросу и отправляем в него id
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://n-develop.16mb.com/test.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("pass 1", "connection success ");
		} catch (Exception e) {
			Log.e("Fail 1", e.toString());
		}

		// получаем ответ от php запроса в формате json
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			response = sb.toString();
			Log.e("pass 2", "connection success" + response);
		} catch (Exception e) {
			Log.e("Fail 2", e.toString());
		}

		// обрабатываем полученный json
		try {
			JSONObject json_data = new JSONObject(response);
			success = (json_data.getString("success"));
			if (success.equals("true"))
			{
				result = true;
			}
			Log.e("pass 3", Boolean.toString(result));
		} catch (Exception e) {
			Log.e("Fail 3", e.toString());
		}
	}

	// принемаем id при запуске потока
	public void start(String jsonExport) {
		JsonExport = jsonExport;

		this.start();
	}

	// отправляес полученный результат
	public Boolean result ()
	{
		return result;
	}
}
