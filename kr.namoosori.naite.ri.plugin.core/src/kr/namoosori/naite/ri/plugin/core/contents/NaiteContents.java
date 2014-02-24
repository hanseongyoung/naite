package kr.namoosori.naite.ri.plugin.core.contents;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kr.namoosori.naite.ri.plugin.core.CoreConstants;
import kr.namoosori.naite.ri.plugin.core.exception.NaiteException;
import kr.namoosori.naite.ri.plugin.core.util.NaiteFileUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class NaiteContents {
	//
	static final String REGEX_FILE_NAME = ".*(filename).*=(.*)";
	static final String USER_AGENT_TYPE = "Mozilla/5.0";
	
	public void download(String localDownloadPath, String serverPath, String serverFileName) throws NaiteException {
		//
		String encodedFileName = encode(serverFileName, true);
		byte[] contents = getHttpContentsArray(CoreConstants.TEACHER_SERVER_URL + serverPath + encodedFileName);
		NaiteFileUtils.saveFile(localDownloadPath, contents);
	}
	
	// 주의 : 사용후 반드시 InputStream을 닫을 것.
	public InputStream getInputStream(String serverPath, String serverFileName) throws NaiteException {
		//
		String encodedFileName = encode(serverFileName, true);
		return getHttpContentsInputStream(CoreConstants.TEACHER_SERVER_URL + serverPath + encodedFileName);
	}
	
	public String getContentsString(String uri) throws NaiteException {
		//
		byte[] contentsArray = getHttpContentsArray(CoreConstants.TEACHER_SERVER_URL + uri);
		String contentsString = new String(contentsArray);
		System.out.println(contentsString);
		return contentsString;
	}
	
	public void doPost(String uri, Map<String, String> params) throws NaiteException {
		//
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost(CoreConstants.TEACHER_SERVER_URL + uri);
		request.setHeader(HttpHeaders.USER_AGENT, USER_AGENT_TYPE);
		if (params != null && !params.isEmpty()) {
			try {
				request.setEntity(new UrlEncodedFormEntity(this.createNameValuePairs(params), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new NaiteException("지원하지 않는 인코딩 타입입니다. charset : " + "UTF-8", e);
			}
		}
		
		HttpEntity entity = null;
		StatusLine statusLine = null;
		try {
			HttpResponse response = httpClient.execute(request);
			statusLine = response.getStatusLine();
			entity = response.getEntity();
			if (entity != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("doPost 성공");
				return;
			}
		} catch (ClientProtocolException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		} catch (IOException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		}
		
		String errorFormat = getErrorFormatIfNoneContents(statusLine.getStatusCode());
		throw new NaiteException(String.format(errorFormat, request.getURI(), statusLine.getStatusCode(), statusLine.getReasonPhrase()));
	}
	
	public void doMultipartPost(String uri, Map<String, String> params, Map<String, File> fileParams) throws NaiteException {
		//
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost(CoreConstants.TEACHER_SERVER_URL + uri);
		request.setHeader(HttpHeaders.USER_AGENT, USER_AGENT_TYPE);
		
		MultipartEntity multipartEntity = new MultipartEntity();
		if (params != null && !params.isEmpty()) {
			try {
				for (Entry<String, String> entry : params.entrySet()) {
					if (entry.getValue() != null) {
						multipartEntity.addPart(entry.getKey(), 
								new StringBody(entry.getValue(), Charset.forName("UTF-8")));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new NaiteException("지원하지 않는 인코딩 타입입니다. charset : " + "UTF-8", e);
			}
		}
		
		if (fileParams != null && !fileParams.isEmpty()) {
			for (Entry<String, File> entry : fileParams.entrySet()) {
				multipartEntity.addPart(entry.getKey(), new FileBody(entry.getValue()));
			}
		}
		
		request.setEntity(multipartEntity);
		
		HttpEntity entity = null;
		StatusLine statusLine = null;
		try {
			HttpResponse response = httpClient.execute(request);
			statusLine = response.getStatusLine();
			entity = response.getEntity();
			if (entity != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("doMultipartPost 성공");
				return;
			}
		} catch (ClientProtocolException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		} catch (IOException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		}
		
		String errorFormat = getErrorFormatIfNoneContents(statusLine.getStatusCode());
		throw new NaiteException(String.format(errorFormat, request.getURI(), statusLine.getStatusCode(), statusLine.getReasonPhrase()));
	}
	
	protected List<NameValuePair> createNameValuePairs(Map<String, String> params) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(params.size());
		for (Entry<String, String> entry : params.entrySet()) {
			parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return parameters;
	}
	
	public static void main(String[] args) {
		NaiteContents nc = new NaiteContents();
		try {
			String str = nc.getContentsString("test.txt");
			System.out.println("str:"+str);
		} catch (NaiteException e) {
			e.printStackTrace();
		}
	}
	
	// 주의 : 사용후 반드시 InputStream을 닫을것.
	private InputStream getHttpContentsInputStream(String url) throws NaiteException {
		//
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		HttpEntity entity = null;
		StatusLine statusLine = null;
		try {
			HttpResponse response = httpClient.execute(request);
			statusLine = response.getStatusLine();
			entity = response.getEntity();
			if (entity != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return entity.getContent();
			}
		} catch (ClientProtocolException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		} catch (IOException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		}
		
		String errorFormat = getErrorFormatIfNoneContents(statusLine.getStatusCode());
		throw new NaiteException(String.format(errorFormat, request.getURI(), statusLine.getStatusCode(), statusLine.getReasonPhrase()));
	}
	
	private byte[] getHttpContentsArray(String url) throws NaiteException {
		//
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		
		HttpEntity entity = null;
		StatusLine statusLine = null;
		try {
			HttpResponse response = httpClient.execute(request);
			statusLine = response.getStatusLine();
			entity = response.getEntity();
			if (entity != null && statusLine.getStatusCode() == HttpStatus.SC_OK) {
				byte[] byteContents = EntityUtils.toByteArray(entity);
				return byteContents;
			}
		} catch (ClientProtocolException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		} catch (IOException e) {
			throw new NaiteException("HTTP 통신 요청중 오류가 발생했습니다 URL:" + request.getURI(), e);
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String errorFormat = getErrorFormatIfNoneContents(statusLine.getStatusCode());
		throw new NaiteException(String.format(errorFormat, request.getURI(), statusLine.getStatusCode(), statusLine.getReasonPhrase()));
	}
	
	private String getErrorFormatIfNoneContents(int statusCode) {
		//
		String errorFormat;
		if (statusCode >= HttpStatus.SC_BAD_REQUEST) {
			errorFormat = "나이테 사이트 접속 오류가 발생했습니다.:[URL=%1$s][StatusCode=%2$d]%3$s";
		} else {
			errorFormat = "나이테 사이트에서 해당내용을 제대로 가져오지 못했습니다.:[URL=%1$s][StatusCode=%2$d]%3$s";
		}
		return errorFormat;
	}
	
	private String encode(String str, boolean encodeOnlyBlank) throws NaiteException {
		if (encodeOnlyBlank) {
			return str.replace(" ", "%20");
		}
		
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new NaiteException("URL 인코드 오류\n", e);
		}
	}
}
