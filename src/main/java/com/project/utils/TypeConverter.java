package com.project.utils;



public class TypeConverter {
    public TypeConverter(){}
    public static String convertToString(Object value) {
        if(value==null){
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        } else {
            return String.valueOf(value);
        }
    }

    public static Integer convertToInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null; 
            }
        } else {
            return null;
        }
    }

    public static Boolean convertToBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            String strValue = ((String) value).toLowerCase().trim();
            if (strValue.equals("true")) {
                return true;
            } else if (strValue.equals("false")) {
                return false;
            } else {
                return null; 
            }
        } else {
            return null;
        }
    }
    public static Float convertToFloat(Object value){
        if (value instanceof Float){
            return (Float) value;
        }else if(value instanceof String){
            try {
                return Float.parseFloat((String) value);
            } catch (Exception e) {
                return null;
            }
        }else{
            return null;
        }
    }
    public static Long convertToLong(Object value){
        if (value instanceof Long){
            return (Long) value;
        }else if(value instanceof String){
            try {
                return Long.parseLong((String) value);
            } catch (Exception e) {
                return null;
            }
        }else{
            return null;
        }
    }
    public static Number convertToNumber(String value, Class<? extends Number> targetClass) {
        if (targetClass == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetClass == Float.class) {
            return Float.parseFloat(value);
        } else if (targetClass == Double.class) {
            return Double.parseDouble(value);
        } else if (targetClass == Long.class) {
            return Long.parseLong(value);
        } else {
            throw new IllegalArgumentException("Unsupported number type: " + targetClass);
        }
    }
}
