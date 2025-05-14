package {{PACKAGE_GROUP}}.fjpa;

import {{PACKAGE_GROUP}}.fjpa.model.ReflectAttribute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetMapper<T>{
    private final ReflectEntity<T> reflectEntity;

    public ResultSetMapper(ReflectEntity<T> reflectEntity) {
        this.reflectEntity = reflectEntity;
    }

    public Object retrieveResultSetValue(ResultSet resultSet, ReflectAttribute attribute, List<Class<?>> excludes, boolean isUpdate) throws SQLException {
        if(attribute.isRelation()){
            if(isUpdate){
                return null;
            }

            ReflectEntity<?> childEntity = new ReflectEntity<>(attribute.getClazz());
            List<Class<?>> newExcludes = new ArrayList<>(excludes);
            newExcludes.add(attribute.getClazz());
            ResultSetMapper<?> childResulSetMapper = new ResultSetMapper<>(childEntity);
            return childResulSetMapper.mapResultSetToInstance(resultSet, newExcludes, false);
        }

        String columnName = attribute.getOriginalColumnName();
        if(!isUpdate){
            columnName = String.format(
                "%s.%s",
                reflectEntity.getOriginalTableName(),
                attribute.getOriginalColumnName()
            );
        }

        switch (attribute.getClazz().getSimpleName()) {
            case "Instant":
                return resultSet.getTimestamp(columnName).toInstant();
            case "LocalDate":
                return resultSet.getDate(columnName).toLocalDate();
            case "BigDecimal":
                return resultSet.getBigDecimal(columnName);
            case "float":
                return resultSet.getFloat(columnName);
            default:
                if (!attribute.getClazz().isEnum()) {
                    return resultSet.getObject(columnName);
                }
                String enumString = resultSet.getString(columnName);
                return Enum.valueOf((Class<Enum>) attribute.getClazz(), enumString);
        }
    }

    public T mapResultSetToInstance(ResultSet resultSet, List<Class<?>> excludes, boolean isUpdate) {
        Map<ReflectAttribute, Object> values = new HashMap<>();
        for(ReflectAttribute attribute: reflectEntity.getAttributes()){
            try {
                if(excludes.contains(attribute.getClazz())){
                    continue;
                }

                values.put(attribute, retrieveResultSetValue(resultSet, attribute, excludes, isUpdate));
            } catch (SQLException error) {
                throw new RuntimeException(error.getMessage());
            }
        }

        return reflectEntity.createInstance(values);
    }

    public T mapResultSetToInstance(ResultSet resultSet) {
        return mapResultSetToInstance(resultSet, List.of(), false);
    }

    public T mapResultSetToInstance(ResultSet resultSet, boolean isUpdate) {
        return mapResultSetToInstance(resultSet, List.of(), isUpdate);
    }
}
