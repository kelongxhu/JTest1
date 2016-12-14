package com.util.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HttpPackageUtil {

    private static String[] secretParas = new String[] {"password"};

    public static String assembleHttpData(HttpServletRequest request) {
        StringBuilder data = new StringBuilder();
        data.append("-----------Http-Headers-------------");
        newLine(data);
        data.append("requestURI:" + request.getRequestURI());
        newLine(data);
        assembleHeader(request, data);
        data.append("-----------Http-Parameters-----------");
        newLine(data);
        assembleParameter(request, data);
        return data.toString();
    }

    public static void assembleHeader(HttpServletRequest request, StringBuilder data) {
        Enumeration<String> nameEnum = request.getHeaderNames();
        String headerName = null;
        String value = null;
        while (nameEnum.hasMoreElements()) {
            headerName = nameEnum.nextElement();
            value = request.getHeader(headerName);
            data.append(headerName);
            data.append(":");
            data.append(value);
            newLine(data);
        }
    }

    public static void assembleParameter(HttpServletRequest request, StringBuilder data) {
        Enumeration<String> nameEnum = request.getParameterNames();
        String paraName = null;
        String[] values = null;
        while (nameEnum.hasMoreElements()) {
            paraName = nameEnum.nextElement();
            values = request.getParameterValues(paraName);
            for (String value : values) {
                data.append(paraName);
                data.append(":");
                if (isSecretPara(paraName)) {
                    data.append("******");
                } else {
                    data.append(value);
                }
                newLine(data);
            }
        }
    }

    public static boolean isSecretPara(String paraName) {
        for (String para : secretParas) {
            if (para.equalsIgnoreCase(paraName)) {
                return true;
            }
        }
        return false;
    }

    public static void newLine(StringBuilder data) {
        data.append("\r\n");
    }

}
