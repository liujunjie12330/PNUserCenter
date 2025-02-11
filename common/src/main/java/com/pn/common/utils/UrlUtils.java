package com.pn.common.utils;

/**
 * @author: javadadi
 * @Time: 17:56
 * @ClassName: UrlUtils
 */

public class UrlUtils {

    public static String coverToUrlByIp(String head, String host, String port, String... params) {
        StringBuilder url = new StringBuilder();
        url.append(head).append("://").append(host).append(":").append(port);
        for (String param : params) {
            url.append("/").append(param);
        }
        return url.toString();
    }

    public static String coverToUrlByNameSpace(String head,String namespace, String... params) {
        StringBuilder url = new StringBuilder();
        url.append(head).append("://").append(namespace);
        for (String param : params) {
            url.append("/").append(param);
        }
        return url.toString();
    }
}
