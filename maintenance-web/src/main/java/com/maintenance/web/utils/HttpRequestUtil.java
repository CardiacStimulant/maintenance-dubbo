package com.maintenance.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestUtil {
    /**
     * 判断编码方式
     *
     * @param str
     * @return
     */
    private static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static String Encoding(String str, String newencode) {
        String oldencode = getEncoding(str);
        if (oldencode.equals(newencode)) {

            return str;
        }
        try {
            return new String(str.getBytes(oldencode), newencode);
        } catch (Exception exception) {
            return str;
        }
    }

    /**
     * 带文件上传的post, file,form
     *
     * @param path
     * @param params
     * @param file
     */
    public static String postFile(String path, HashMap<String, String> params, File file) throws IOException {
        // path为传送地址
        URL url = new URL(path);

        // 创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);// 允许对外发送请求参数
        conn.setUseCaches(false);// 不进行缓存
        conn.setConnectTimeout(5 * 1000);
        // 传送方式
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=--testsssssss");
        // conn.setRequestProperty("Content-Type",
        // "application/x-www-form-urlencoded,*/*");
        conn.setRequestProperty("Connection", "Keep-Alive");
        if (params != null && !params.isEmpty()) {

            for (Map.Entry<String, String> entry : params.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        // 输出
        OutputStream outStream = conn.getOutputStream();
        if (file != null) {
            // data为文件流信息
            InputStream is = new FileInputStream(file);

            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = is.read(bytes)) != -1) {
                outStream.write(bytes, 0, len);
            }
            is.close();
        }
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200) {
            InputStream input = conn.getInputStream();
            StringBuffer sb1 = new StringBuffer();
            int ss;
            while ((ss = input.read()) != -1) {
                sb1.append((char) ss);

            }
            return sb1.toString();
        } else {
            return conn.getResponseCode() + "";
        }
    }

    /**
     * POST 请求 Form表单
     *
     * @param RequestURL
     * @param params
     * @return
     */
    public static String post(String RequestURL, StringBuffer params) {
        String result = null;
        try {
            URL url = new URL(RequestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000 * 1000);
            conn.setConnectTimeout(30000 * 1000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setDoOutput(true);// 是否输入参数
            conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            byte[] bypes = params.toString().getBytes();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(bypes);// 输入参数
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            // {

            InputStream input = conn.getInputStream();
            StringBuffer sb1 = new StringBuffer();

            int ss;
            while ((ss = input.read()) != -1) {
                sb1.append((char) ss);

            }
            result = sb1.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return result;

    }

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String post(String url, Map<String, Object> parameters) {
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {
            // 编码请求参数  
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            URLEncoder.encode((String) parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            URLEncoder.encode((String) parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象  
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式  
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数  
            out.write(params);
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String getData(String path) {

        String ret = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn.getResponseCode() == 200) {

                InputStream inStream = conn.getInputStream();
                byte[] data1 = HttpRequestUtil.readStream(inStream);

                if (data1 != null) {
                    ret = new String(data1, "utf-8");
                }
            }

            conn.disconnect();

        } catch (SocketTimeoutException e) {
            conn.disconnect();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            conn.disconnect();
        }

        return ret;
    }

    /**
     * POST 方式传JSON
     *
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    public static String postJSONData(String path, String data) throws Exception {
        String ret = "";
        HttpURLConnection conn = null;
        OutputStream output = null;
        InputStream inStream = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60 * 1000);//连接超时时间
            conn.setReadTimeout(60 * 1000);//读取超时时间
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));

            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();

            output = conn.getOutputStream();
            output.write(data.getBytes());// 输入参数
            output.flush();
            if (conn.getResponseCode() == 200) {

                inStream = conn.getInputStream();
                byte[] data1 = HttpRequestUtil.readStream(inStream);

                if (data1 != null) {
                    ret = new String(data1, "utf-8");
                }
            } else {
                inStream = conn.getErrorStream();
                byte[] data1 = HttpRequestUtil.readStream(inStream);

                if (data1 != null) {
                    ret = new String(data1, "utf-8");
                }

            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new Exception("获取数据超时！");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("调用APP接口异常：接口=" + path);
        } finally {
            close(inStream);
            close(output);
            close(conn);
        }
        return ret;

    }

    private static byte[] readStream(InputStream inStream) throws IOException, SocketTimeoutException {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
        }
    }

    public static void close(URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    /**
     * 获取Sping web相关属性
     */
    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
        }
        return session;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }
}
