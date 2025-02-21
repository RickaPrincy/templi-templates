package{{PACKAGE_GROUP}}.fjpa;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatementWrapper {
    final private Connection connection;

    public StatementWrapper(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement prepared(String query, List<Object> values) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        if(values == null)
            return statement;

        List<Object> filteredValues = values.stream().filter(Objects::nonNull).toList();
        for(int i = 1; i <= filteredValues.size(); i++){
            Object current = filteredValues.get(i - 1);
            if(current instanceof Instant){
                statement.setTimestamp(i, Timestamp.from((Instant) current));
            }else if(current.getClass().isEnum()){
                statement.setObject(i, current, Types.OTHER);
            }else{
                statement.setObject(i, current);
            }
        }
        return statement;
    }

    public <T> List<T> select(String query, List<Object> values, Function<ResultSet, T> mapper) throws SQLException {
        return mapResultSet(prepared(query, values).executeQuery(), mapper);
    }

    public ResultSet update(String query, List<Object> values) throws SQLException {
        PreparedStatement statement = prepared(query, values);
        statement.executeUpdate();
        return statement.getGeneratedKeys();
    }

    public <T> List<T> mapResultSet(ResultSet resultSet, Function<ResultSet, T> mapper) throws SQLException {
        List<T> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(mapper.apply(resultSet));
        }
        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
