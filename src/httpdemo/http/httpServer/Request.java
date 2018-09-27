package httpdemo.http.httpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

public class Request {

    //请求信息
    private String info;
    //请求方法
    private String method;
    //请求url
    private String url;
    //请求参数
    private String queryStr;
    //存储请求参数Map
    private Map<String,List<String>> hashMap;

    private InputStream inputStream;

    public String getUrl() {
        return url;
    }

    public Request(Socket socket) throws IOException {
        try {
            hashMap=new HashMap<String,List<String>>();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[1024 * 1024];
        int len = inputStream.read(bytes);
        info= new String(bytes, 0, len);
        info=decode(info,"UTF-8");

        System.out.println(info);

        //分解请求协议
        parseRequestInfo();
        //分解请求参数字段
        ParameterValues();

    }
    public Request(InputStream inputStream) throws IOException {
        hashMap=new HashMap<String,List<String>>();
        this.inputStream=inputStream;
        byte[] bytes = new byte[1024 * 1024];
        int len = inputStream.read(bytes);
        info = new String(bytes, 0, len);
        info=decode(info,"UTF-8");
        System.out.println(info);

        //分解请求协议
        parseRequestInfo();
        //分解请求参数字段
        ParameterValues();

    }


    //参数分解
    public void ParameterValues(){
        if (this.queryStr!=null) {
            String[] keyValues = this.queryStr.split("&");
            for (String queryString : keyValues) {
                String[] parameter = queryString.split("=");
                parameter = Arrays.copyOf(parameter, 2);
                String key = parameter[0].trim();

                String values = decode(parameter[1], "UTF-8").trim();

                if (!hashMap.containsKey(key)) {
                    hashMap.put(key, new ArrayList<String>());
                }
                hashMap.get(key).add(values);
            }
        }
    }

    //参数中文编码乱码
    public String decode(String value,String enc){
        try {
            return URLDecoder.decode(value,enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //请求正文分割。请求头；请求行；请求正文
    public void parseRequestInfo(){
        method=info.substring(0,this.info.indexOf("/")).toLowerCase().trim();
        System.out.println(method);

        int indexstart=this.info.indexOf("/")+1;
        int indexend=this.info.indexOf("HTTP/");
        url=info.substring(indexstart,indexend).trim();
        System.out.println(url);

        int index=url.indexOf("?");
        if (index>=0){
            String[] urlArray=url.split("\\?");
            url=urlArray[0].trim();
            queryStr=urlArray[1];
        }

        if(method.equals("post")){
            String qStr =info.substring(info.lastIndexOf("\r\n")).trim();
            if (null==queryStr){
                queryStr=qStr;
            }else {
                queryStr+="&"+queryStr;
            }
        }

        System.out.println(url);
        System.out.println(queryStr);
    }
}
