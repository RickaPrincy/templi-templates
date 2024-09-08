package {{PACKAGE_GROUP}};

public class QueryTemplate {
    public static String selectAll(){
        return "SELECT * FROM @entity";
    }

    //for test
    public static String deleteAll(){
        return "DELETE FROM @entity";
    }

    public static String deleteById(){
        return "DELETE FROM @entity WHERE @id = ?";
    }

    public static String selectById(){
        return "SELECT * FROM @entity WHERE @id = ?";
    }

    public static String selectByCondition(String condition){
        return String.format("SELECT * FROM @entity WHERE %s", condition);
    }

    public static String updateByCondition(String columnValues, String condition){
        return String.format("UPDATE @entity SET %s WHERE %s", columnValues, condition);
    }

    public static String insert(String columns,String values){
        return String.format("INSERT INTO @entity(%s) VALUES ( %s )", columns, values);
    }
}
