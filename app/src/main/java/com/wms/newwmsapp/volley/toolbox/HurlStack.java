package com.wms.newwmsapp.volley.toolbox;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import com.wms.newwmsapp.volley.AuthFailureError;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.Request.Method;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HurlStack implements HttpStack {

	private static final String HEADER_CONTENT_TYPE = "Content-Type";

	public interface UrlRewriter {
		public String rewriteUrl(String originalUrl);
	}

	private final UrlRewriter mUrlRewriter;
	private final SSLSocketFactory mSslSocketFactory;

	public HurlStack() {
		this(null);
	}

	public HurlStack(UrlRewriter urlRewriter) {
		this(urlRewriter, null);
	}

	public HurlStack(UrlRewriter urlRewriter, SSLSocketFactory sslSocketFactory) {
		mUrlRewriter = urlRewriter;
		mSslSocketFactory = sslSocketFactory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HttpResponse performRequest(Request<?> request,
			Map<String, String> additionalHeaders) throws IOException,
			AuthFailureError {
		String url = request.getUrl();
		HashMap map = new HashMap();
		map.putAll(request.getHeaders());
		map.putAll(additionalHeaders);
		if (this.mUrlRewriter != null) {
			String parsedUrl = this.mUrlRewriter.rewriteUrl(url);
			if (parsedUrl == null) {
				throw new IOException("URL blocked by rewriter: " + url);
			}

			url = parsedUrl;
		}

		URL parsedUrl1 = new URL(url);
		HttpURLConnection connection = this.openConnection(parsedUrl1, request);
		Iterator responseCode = map.keySet().iterator();

		while (responseCode.hasNext()) {
			String protocolVersion = (String) responseCode.next();
			connection.addRequestProperty(protocolVersion,
					(String) map.get(protocolVersion));
		}

		setConnectionParametersForRequest(connection, request);
		ProtocolVersion protocolVersion1 = new ProtocolVersion("HTTP", 1, 1);
		int responseCode1 = connection.getResponseCode();
		if (responseCode1 == -1) {
			throw new IOException(
					"Could not retrieve response code from HttpUrlConnection.");
		} else {
			BasicStatusLine responseStatus = new BasicStatusLine(
					protocolVersion1, connection.getResponseCode(),
					connection.getResponseMessage());
			BasicHttpResponse response = new BasicHttpResponse(responseStatus);
			response.setEntity(entityFromConnection(connection));

			for (Object o : connection.getHeaderFields().entrySet()) {
				Entry header = (Entry) o;
				if (header.getKey() != null) {
					// 如果是cookie则保存为name = Set-Cookie + 序号，保证key不同
					if (header.getKey().equals("Set-Cookie")) {
						List headerList = (List) header.getValue();
						for (int i = 0; i < headerList.size(); i++) {
							BasicHeader h = new BasicHeader(
									(String) header.getKey() + i,
									(String) headerList.get(i));
							response.addHeader(h);
						}
					} else {
						List headerList = (List) header.getValue();
						for (Object aHeaderList : headerList) {
							BasicHeader h = new BasicHeader(
									(String) header.getKey(),
									(String) aHeaderList);
							response.addHeader(h);
						}
					}
				}
			}

			return response;
		}
	}

	private static HttpEntity entityFromConnection(HttpURLConnection connection) {
		BasicHttpEntity entity = new BasicHttpEntity();
		InputStream inputStream;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException ioe) {
			inputStream = connection.getErrorStream();
		}
		entity.setContent(inputStream);
		entity.setContentLength(connection.getContentLength());
		entity.setContentEncoding(connection.getContentEncoding());
		entity.setContentType(connection.getContentType());
		return entity;
	}

	protected HttpURLConnection createConnection(URL url) throws IOException {
		return (HttpURLConnection) url.openConnection();
	}

	private HttpURLConnection openConnection(URL url, Request<?> request)
			throws IOException {
		HttpURLConnection connection = createConnection(url);

		int timeoutMs = request.getTimeoutMs();
		connection.setConnectTimeout(timeoutMs);
		connection.setReadTimeout(timeoutMs);
		connection.setUseCaches(false);
		connection.setDoInput(true);

		if ("https".equals(url.getProtocol()) && mSslSocketFactory != null) {
			((HttpsURLConnection) connection)
					.setSSLSocketFactory(mSslSocketFactory);
		}

		return connection;
	}

	@SuppressWarnings("deprecation")
	static void setConnectionParametersForRequest(HttpURLConnection connection,
			Request<?> request) throws IOException, AuthFailureError {
		switch (request.getMethod()) {
		case Method.DEPRECATED_GET_OR_POST:
			byte[] postBody = request.getPostBody();
			if (postBody != null) {
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.addRequestProperty(HEADER_CONTENT_TYPE,
						request.getPostBodyContentType());
				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				out.write(postBody);
				out.close();
			}
			break;
		case Method.GET:
			connection.setRequestMethod("GET");
			break;
		case Method.DELETE:
			connection.setRequestMethod("DELETE");
			break;
		case Method.POST:
			connection.setRequestMethod("POST");
			addBodyIfExists(connection, request);
			break;
		case Method.PUT:
			connection.setRequestMethod("PUT");
			addBodyIfExists(connection, request);
			break;
		case Method.HEAD:
			connection.setRequestMethod("HEAD");
			break;
		case Method.OPTIONS:
			connection.setRequestMethod("OPTIONS");
			break;
		case Method.TRACE:
			connection.setRequestMethod("TRACE");
			break;
		case Method.PATCH:
			connection.setRequestMethod("PATCH");
			addBodyIfExists(connection, request);
			break;
		default:
			throw new IllegalStateException("Unknown method type.");
		}
	}

	private static void addBodyIfExists(HttpURLConnection connection,
			Request<?> request) throws IOException, AuthFailureError {
		byte[] body = request.getBody();
		if (body != null) {
			connection.setDoOutput(true);
			connection.addRequestProperty(HEADER_CONTENT_TYPE,
					request.getBodyContentType());
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.write(body);
			out.close();
		}
	}
}
