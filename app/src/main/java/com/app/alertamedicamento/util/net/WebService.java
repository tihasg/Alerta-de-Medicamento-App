package com.app.alertamedicamento.util.net;

import android.util.Base64;
import android.util.Log;

import com.app.alertamedicamento.util.exception.SysException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Utilitarios para WEB.
 */

public class WebService {

    public static String URL_SERVER = "localhost";
    public static String PORT_SERVER = "8080";
    public static String AUTH_USER = "root";
    public static String AUTH_PWD = "toor";
    public static boolean AUTH_SERVER = false;
    private ProxyConfig proxy = null;

    public WebService() {
    }

    public static String getUrlServer() {
        return String.format("http://%s:%s", URL_SERVER, PORT_SERVER);
    }

    public static boolean valResp(String result) {
        result = (result == null) ? "" : result.trim();
        return !result.equals("");
    }

    public static boolean setUrlServer(String _url) {
        URL_SERVER = _url;
        return true;
    }

    public static boolean setPortServer(String _portServer) {
        PORT_SERVER = _portServer;
        return true;
    }

    public static boolean setAuthServer(boolean _authServer) {
        AUTH_SERVER = _authServer;
        return true;
    }

    public static boolean setAuthPwd(String _authPwd) {
        AUTH_PWD = _authPwd;
        return true;
    }

    public static boolean setAuthUser(String _authUser) {
        AUTH_USER = _authUser;
        return true;
    }

    public void setProxyConfig(ProxyConfig proxyConfig) {
        proxy = proxyConfig;
    }

    public InputStream obterConteudoArquivo(String u) {
        u = u.replace(" ", "%20");
        URL url;
        try {
            url = new URL(u);
            URLConnection conn = null;
            if (proxy != null)
                conn = url.openConnection(proxy.getProxy());
            else
                conn = url.openConnection();

            return new DataInputStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            throw new SysException(e);
        } catch (IOException e) {
            throw new SysException(e);
        }
    }

    /**
     * Obter o conteudo de um site.
     *
     * @param u URL do Site.
     * @return String contendo todo o conteudo do site em HTML.
     * @deprecated
     */
    public String obterConteudoSite(String u) {
        URL url;
        try {
            url = new URL(u);
            HttpURLConnection conn = null;
            if (proxy != null)
                conn = (HttpURLConnection) url.openConnection(proxy.getProxy());
            else
                conn = (HttpURLConnection) url.openConnection();

            //conn.setDoOutput(true);
            conn.setAllowUserInteraction(false);

            //Auth!
            setAuth(conn);

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), Charset.forName("UTF-8")));
            String line;
            StringBuilder resultado = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                resultado.append(line);
                resultado.append("\n");
            }
            rd.close();
            return resultado.toString();
        } catch (MalformedURLException e) {
            throw new SysException("Nao foi possivel obter contato com o site "
                    + u, e);
        } catch (IOException e) {
            throw new SysException("Nao foi possivel obter contato com o site "
                    + u, e);
        }
    }

    /**
     * Obter o conteudo de um site.
     *
     * @param u URL do Site.
     * @return String contendo todo o conteudo do site em HTML.
     */
    public String obterConteudoSite(String u, String characterSet) {
        URL url;
        try {
            url = new URL(u);

            HttpURLConnection conn = null;

            if (proxy != null)
                conn = (HttpURLConnection) url.openConnection(proxy.getProxy());
            else
                conn = (HttpURLConnection) url.openConnection();

            setAuth(conn);
            conn.setInstanceFollowRedirects(false);
            //conn.setDoOutput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), Charset.forName(characterSet)));
            String line;
            StringBuilder resultado = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                resultado.append(line);
                resultado.append("\n");
            }
            rd.close();
            return resultado.toString();

        } catch (MalformedURLException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        } catch (IOException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        }
    }

    public void setAuth(HttpURLConnection conn) {
        if (AUTH_SERVER) {
            // Auth!
            String authStr = AUTH_USER + ":" + AUTH_PWD;
            // encode data on your side using BASE64
            byte[] bytesEncoded = Base64.encode(authStr.getBytes(), Base64.DEFAULT);
            String authEncoded = new String(bytesEncoded);
            conn.setRequestProperty("Authorization", "Basic " + authEncoded);
        }
    }

    public String obterConteudoSite(String u, String encode, Map<String, String> parametros) {
        URL url;
        try {
            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode(parametros.get(chave), encode));
                    strParams.append("&");
                }
            }
            url = new URL(u);
            URLConnection conn = null;
            if (proxy != null)
                conn = url.openConnection(proxy.getProxy());
            else
                conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(strParams.toString());
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), Charset.forName(encode)));
            String line;
            StringBuilder resultado = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                resultado.append(line);
            }
            wr.close();
            rd.close();
            return resultado.toString();
        } catch (MalformedURLException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        } catch (IOException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        }
    }

    /**
     * Obter o conteudo de um site usando o metodo POST.
     *
     * @param u          URL do Site.
     * @param parametros Parametros que serao usandos na requisicao ao site.
     * @return String contendo todo o conteudo do site em HTML.
     */
    public String obterConteudoSite(String u,
                                    Map<String, String> parametros) {
        return obterConteudoSite(u, parametros, null);
    }

    public String obterConteudoSite(String u, String encode, Map<String, String> parametros, Map<String, String> headers) {
        URL url;
        try {
            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode(parametros.get(chave), "UTF-8"));
                    strParams.append("&");
                }
            }
            url = new URL(u);
            URLConnection conn = null;

            if (proxy != null)
                conn = url.openConnection(proxy.getProxy());
            else
                conn = url.openConnection();

            if (headers != null) {
                for (String header : headers.keySet()) {
                    conn.setRequestProperty(header, headers.get(header));
                }
            }
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(strParams.toString());
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), Charset.forName(encode)));
            String line;
            StringBuilder resultado = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                resultado.append(line);
            }
            wr.close();
            rd.close();
            return resultado.toString();
        } catch (MalformedURLException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        } catch (IOException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        }
    }

    public String obterConteudoSite(String u, Map<String, String> parametros, Map<String, String> headers) {
        URL url;
        try {
            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode(parametros.get(chave), "UTF-8"));
                    strParams.append("&");
                }
            }
            url = new URL(u);
            URLConnection conn = null;

            if (proxy != null)
                conn = url.openConnection(proxy.getProxy());
            else
                conn = url.openConnection();

            if (headers != null) {
                for (String header : headers.keySet()) {
                    conn.setRequestProperty(header, headers.get(header));
                }
            }
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(strParams.toString());
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), Charset.forName("UTF-8")));
            String line;
            StringBuilder resultado = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                resultado.append(line);
            }
            wr.close();
            rd.close();
            return resultado.toString();
        } catch (MalformedURLException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        } catch (IOException e) {
            throw new SysException("Nao foi possivel obter contato com o site " + u, e);
        }
    }

    public void download(String appURL, String appName) {
        try {
            URL url = new URL(appURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            File file = new File(appName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();

        } catch (MalformedURLException e) {
            Log.e("WebService", e.getMessage());
        } catch (ProtocolException e) {
            Log.e("WebService", e.getMessage());
        } catch (FileNotFoundException e) {
            Log.e("WebService", e.getMessage());
        } catch (IOException e) {
            Log.e("WebService", e.getMessage());
        }
    }

    public String excuteGet(String targetURL, Map<String, String> parametros) {
        URL url;
        HttpURLConnection connection = null;
        try {
            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    if (!strParams.toString().equals("")) {
                        strParams.append("&");
                    }

                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode(parametros.get(chave), "UTF-8"));
                }
            }

            url = new URL(targetURL + "?" + strParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);

            // Auth!
            setAuth(connection);

            int responseCode = connection.getResponseCode(); //can call this instead of con.connect()
            if (responseCode >= 400 && responseCode <= 499) {
                throw new SysException("Bad authentication status: " + responseCode); //provide a more meaningful exception message
            }

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String excutePost(String targetURL, Map<String, String> parametros) {
        URL url;
        HttpURLConnection connection = null;
        try {
            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    if (!strParams.toString().equals("")) {
                        strParams.append("&");
                    }

                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode((parametros.get(chave) == null ? "" : parametros.get(chave)), "UTF-8"));
                }
            }

            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty(
                    "Content-Length",
                    "" + Integer.toString(strParams.toString().getBytes().length));
            connection.setRequestProperty("Content-Language", "pt-BR");

            connection.setUseCaches(false);
            //connection.setDoInput(true);
            //connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);

            // Auth!
            setAuth(connection);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(strParams.toString());
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String webPost(String url, Map<String, String> parametros, String encode) {
        try {

            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode((parametros.get(chave) == null ? "" : parametros.get(chave)), encode));
                    strParams.append("&");
                }
            }

            return excutePost(url + "?" + strParams, parametros);

        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
        }

        return "";
    }

    public String webGet(String url, Map<String, String> parametros,
                         String encode) {
        try {

            StringBuilder strParams = new StringBuilder();
            if (parametros != null) {
                for (String chave : parametros.keySet()) {
                    strParams.append(URLEncoder.encode(chave, "UTF-8"));
                    strParams.append("=");
                    strParams.append(URLEncoder.encode((parametros.get(chave) == null ? "" : parametros.get(chave)), encode));
                    strParams.append("&");
                }
            }

            return obterConteudoSite(url + "?" + strParams, encode);

        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
        }

        return "";
    }

    public boolean testURL(String strUrl) {

        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            setAuth(urlConn);

            urlConn.connect();

            return HttpURLConnection.HTTP_OK == urlConn.getResponseCode();
        } catch (Exception e) {
            Log.e("WebService", "Error creating HTTP connection : " + strUrl);
            return false;
        }
    }

}
