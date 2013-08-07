package cw.html.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

/**
 * Functionality to retrieve documents from the web.
 *
 */
public class WebDownloader {
	private static int CONNECTION_TIMEOUT = 5000; //5秒
	
	/**
	 * Download the document represented by the given URL.
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static byte[] download(URL url) throws IOException {
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);  
		
		HttpMethod method = new GetMethod(url.toString());
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, CONNECTION_TIMEOUT);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());  
		
		byte[] responseBody;
		
		try {
			int statusCode = client.executeMethod( method );			

			if (statusCode != HttpStatus.SC_OK) {  
				throw new IOException("Unexpected HTTP Status: " + statusCode);
			}
			
			responseBody = method.getResponseBody();
			
		}catch (HttpException e) {  
            // 發生致命的異常，可能是協議不對或者返回的內容有問題  
            System.out.println("Please check your provided http address!");  
            e.printStackTrace();
            throw e;
        } catch (IOException e) {  
            // 發生網絡異常  
            e.printStackTrace();  
            throw e;
        } finally {  
            /* 6 .釋放連接 */  
        	method.releaseConnection();  
        }  
		
		return responseBody;
	}
	
	public static byte[] download(String url) throws IOException {
		if(url!=null){
			if(!StringUtils.startsWith(url, "http://")){
				url = "http://" + url;
			}
			
			return download( new URL(url) );
		}
		
		return null;
		
	}
	
	public static InputStream downloadAsInputStream(String url) throws IOException {
		return new ByteArrayInputStream( download(url) );
	}
}
