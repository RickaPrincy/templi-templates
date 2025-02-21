package {{PACKAGE_GROUP}}.fjpa.utils;

public class Utils {
    public static String toCamelCase(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static String toSQLName(String text){
        return "\"" + text + "\"";
    }
}
