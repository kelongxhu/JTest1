package com.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private HttpClientUtil() {
    }


    public static String sendPostRequest(final String reqURL,
            final String reqBody) throws ParseException, IOException {
        String reseContent = "fail";
        final HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);
        final HttpPost httpPost = new HttpPost(reqURL);
        try {

            final StringEntity s = new StringEntity(reqBody, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            final HttpResponse response = httpClient.execute(httpPost);
            final HttpEntity entity = response.getEntity();
            if (null != entity) {
                reseContent = EntityUtils.toString(entity, ContentType
                        .getOrDefault(entity).getCharset());
                EntityUtils.consume(entity);
            }
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return reseContent;
    }


    public static String sendPostSSLRequest(final String reqURL,
            final Map<String, Object> params, final String encodeCharset)
            throws Exception {

        String responseContent = "";
        final HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                2000);
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                    final String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                    final String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        final X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
            @Override
            public void verify(final String host, final SSLSocket ssl)
                    throws IOException {
            }

            @Override
            public void verify(final String host, final X509Certificate cert)
                    throws SSLException {
            }

            @Override
            public void verify(final String host, final String[] cns,
                    final String[] subjectAlts)
                    throws SSLException {
            }

            @Override
            public boolean verify(final String arg0, final SSLSession arg1) {
                return true;
            }
        };
        try {
            final SSLContext sslContext = SSLContext
                    .getInstance(SSLSocketFactory.TLS);
            sslContext.init(null, new TrustManager[] {trustManager}, null);
            final SSLSocketFactory socketFactory = new SSLSocketFactory(
                    sslContext,
                    hostnameVerifier);
            httpClient.getConnectionManager().getSchemeRegistry()
                    .register(new Scheme("https", 443, socketFactory));
            final HttpPost httpPost = new HttpPost(reqURL);

            if (null != params) {
                final List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for (final Map.Entry<String, Object> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(String.valueOf(entry
                            .getKey()), String.valueOf(entry.getValue())));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formParams,
                        encodeCharset));
            }

            final HttpResponse response = httpClient.execute(httpPost);
            final HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, ContentType
                        .getOrDefault(entity).getCharset());
                EntityUtils.consume(entity);
            }
        } catch (final Exception e) {
            throw e;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseContent;
    }

    /**
     * HTTP_GET_SSL
     * 
     * @author binli
     * @date 2014年8月21日 下午3:22:37
     * @param reqURL
     * @param params
     * @param encodeCharset
     * @return
     * @throws Exception
     * @return
     * @version v1.0
     */
    public static String sendGetSSLRequest(final String reqURL)
            throws Exception {

        String responseContent = "";
        final HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                2000);
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                    final String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                    final String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        final X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
            @Override
            public void verify(final String host, final SSLSocket ssl)
                    throws IOException {
            }

            @Override
            public void verify(final String host, final X509Certificate cert)
                    throws SSLException {
            }

            @Override
            public void verify(final String host, final String[] cns,
                    final String[] subjectAlts)
                    throws SSLException {
            }

            @Override
            public boolean verify(final String arg0, final SSLSession arg1) {
                return true;
            }
        };
        try {
            final SSLContext sslContext = SSLContext
                    .getInstance(SSLSocketFactory.TLS);
            sslContext.init(null, new TrustManager[] {trustManager}, null);
            final SSLSocketFactory socketFactory = new SSLSocketFactory(
                    sslContext,
                    hostnameVerifier);
            httpClient.getConnectionManager().getSchemeRegistry()
                    .register(new Scheme("https", 443, socketFactory));
            final HttpGet httpGet = new HttpGet(reqURL);

            final HttpResponse response = httpClient.execute(httpGet);
            final HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, ContentType
                        .getOrDefault(entity).getCharset());
                EntityUtils.consume(entity);
            }
        } catch (final Exception e) {
            throw e;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseContent;
    }


    public static void main(String[] args)throws Exception{
        HttpClientUtil.sendGetSSLRequest("https://localhost:8450");
    }
}
