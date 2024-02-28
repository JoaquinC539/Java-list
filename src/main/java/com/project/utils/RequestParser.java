package com.project.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class RequestParser {

    public RequestParser() {
    }

    public static LinkedHashMap<String, String> parseHeaders(HttpServletRequest request) {
        LinkedHashMap<String, String> headersMap = new LinkedHashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersMap.put(headerName, headerValue);
        }
        return headersMap;
    }

    public static LinkedHashMap<String, String> parseCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        LinkedHashMap<String, String> cookiesMap = new LinkedHashMap<String, String>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                cookiesMap.put(name, value);
            }
        }
        return cookiesMap;
    }

    
    public static LinkedHashMap<String, Object> parseBody(HttpServletRequest request) throws IOException {
        LinkedHashMap<String, Object> bodyMap = new LinkedHashMap<>();
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            String jsonBody = extractRequestBody(request);
            bodyMap = parseJsonToMap(jsonBody);
        } else if (contentType != null && contentType.contains("application/x-www-form-urlencoded")) {
            String formBody = extractRequestBody(request);
            bodyMap = parseFormURLEncodedMap(formBody);
        }

        return bodyMap;

    }

    private static String extractRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }

    private static LinkedHashMap<String, Object> parseJsonToMap(String jsonBody) throws IOException {
        if (jsonBody.toCharArray()[0] != '{') {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("error", "Incompatible header and body request");
            return map;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonBody, new TypeReference<LinkedHashMap<String, Object>>() {
        });
    }

    private static LinkedHashMap<String, Object> parseFormURLEncodedMap(String formBody) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        if (formBody.toCharArray()[0] == '{') {
            map.put("error", "Incompatible header and body request");
            return map;
        }
        // String[] fields=formBody.split("&");
        // for (String field : fields){
        // String[] valkey=field.split("=");
        // String value=valkey[1].replace("%20", " ");
        // map.put(valkey[0], value);
        // }
        Arrays.stream(formBody.split("&"))
                .map(pair -> pair.split("="))
                .filter(pair -> pair.length == 2)
                .forEach(pair -> map.put(pair[0], pair[1].replace("%20", " ")));
        return map;
    }

    public static LinkedHashMap<String, String> parseQueryParams(HttpServletRequest request) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        String queryString = request.getQueryString();
        return map;
    }
}
