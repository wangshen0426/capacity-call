package util;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientPost {
	public static void SubmitPost(String url, List<byte[]> fileArr,
			String[] fileName, String originalServerIp) {

		HttpClient httpclient = new DefaultHttpClient();

		try {
			HttpPost httppost = new HttpPost(url);

			MultipartEntity reqEntity = new MultipartEntity();

			int length = fileName.length;
			JSONArray ja = new JSONArray();
			for (int i = 0; i < length; i++) {
				JSONObject jo = new JSONObject();
				jo.put("fileName", fileName[i]);
				jo.put("fileByte", fileArr.get(i));
				ja.add(jo);
			}

			reqEntity.addPart("JSON", new StringBody(ja.toString()));
			reqEntity.addPart("originalServerIp", new StringBody(
					originalServerIp));
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {

				System.out.println("服务器正常响应.....");

				HttpEntity resEntity = response.getEntity();

				System.out.println(EntityUtils.toString(resEntity));

				System.out.println(resEntity.getContent());

				EntityUtils.consume(resEntity);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {

			}
		}
	}

}
