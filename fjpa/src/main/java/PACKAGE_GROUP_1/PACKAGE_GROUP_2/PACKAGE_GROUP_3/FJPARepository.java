package {{PACKAGE_GROUP}};

import {{PACKAGE_GROUP}}.model.ReflectAttribute;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FJPARepository <T>{
    protected final ReflectEntity<T> reflectEntity;
    protected final QueryGenerator<T> queryGenerator;
    protected final StatementWrapper statementWrapper;
    protected String SELECT_ALL_QUERY;

    protected ResultSetMapper<T> resultSetMapper;

    @SuppressWarnings("unchecked")
    public FJPARepository(Connection connection) {
        this.reflectEntity = (ReflectEntity<T>) new ReflectEntity<>(this.getClazz());
        this.statementWrapper = new StatementWrapper(connection);
        this.queryGenerator = new QueryGenerator<>(reflectEntity);
        this.resultSetMapper = new ResultSetMapper<>(reflectEntity);
        this.SELECT_ALL_QUERY = queryGenerator.selectAll();
    }

    // to get Class<T>
    private Class<?> getClazz(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    public List<T> findAll() throws SQLException {
        return statementWrapper.select(SELECT_ALL_QUERY,null, this.resultSetMapper::mapResultSetToInstance);
    }

    public List<T> findAll(String suffix, List<Object> values) throws SQLException {
        String query = queryGenerator.configure(String.format("%s %s", SELECT_ALL_QUERY, suffix));
        return statementWrapper.select(query,values, this.resultSetMapper::mapResultSetToInstance);
    }

    public List<T> findByField(String fieldName, Object fieldValue) throws SQLException {
        return findByField(fieldName, fieldValue, List.of());
    }

    public List<T> findByField(String fieldName, Object fieldValue, List<Class<?>> excludes) throws SQLException {
        return findByField(fieldName, fieldValue, excludes, "");
    }

    public List<T> findByField(String fieldName, Object fieldValue, List<Class<?>> excludes, String suffix) throws SQLException {
        String condition = String.format("%s = ?", fieldName);
        return findByCondition(condition, List.of(fieldValue), suffix, excludes);
    }

    public T findById(Object id) throws SQLException {
        return findById(id, List.of());
    }

    public T findById(Object id, List<Class<?>> excludes) throws SQLException {
        List<T> lists = findByField("@id", id, excludes);
        return lists.isEmpty() ? null : lists.get(0);
    }

    public List<T> findByCondition(String condition, List<Object> values, String suffix, List<Class<?>> excludes) throws SQLException {
        String query = String.format("%s WHERE %s %s", SELECT_ALL_QUERY, condition, suffix);
        return this.statementWrapper.select(
                this.queryGenerator.configure(query),
                values,
                resultSet -> this.resultSetMapper.mapResultSetToInstance(resultSet, excludes, false)
        );
    }

    public List<T> findByCondition(String condition, List<Object> values, String suffix) throws SQLException {
        return findByCondition(condition, values, suffix, List.of());
    }

    public List<T> findByCondition(String condition, List<Object> values) throws SQLException {
        return findByCondition(condition, values, "");
    }

    public T saveOrUpdate(T toSave) throws SQLException {
        final Object idValue = ReflectEntity.invokeGetters(toSave, reflectEntity.getIdAttribute());
        if(idValue == null)
            return null;

        final boolean isCreate = findById(idValue) == null;

        String query;
        if(isCreate){
            query = QueryTemplate.insert(
                    this.joinAttributesNames(","),
                    "? " + ",?".repeat(reflectEntity.getAttributes().size() - 1)
            );
        }else{
            query = QueryTemplate.updateByCondition(
                joinAttributesNamesWithoutId(" = ? , ") + " = ?",
                reflectEntity.getIdAttribute().getSqlColumnName() + " = ?"
            );
        }

        List<Object> values;
        if(isCreate){
            values = reflectEntity.getAttributes()
                    .stream()
                    .map(attribute -> ReflectEntity.invokeGetters(toSave, attribute))
                    .collect(Collectors.toList());
        }else{
            values = reflectEntity.getAttributes()
                    .stream()
                    .filter(attribute -> !attribute.isId())
                    .map(attribute -> ReflectEntity.invokeGetters(toSave, attribute))
                    .collect(Collectors.toList());
            values.add(ReflectEntity.invokeGetters(toSave, reflectEntity.getIdAttribute()));
        }

        ResultSet resultSet = statementWrapper.update(
            queryGenerator.configure(query, true),
            values
        );

        if(!resultSet.next())
            return null;
        return this.resultSetMapper.mapResultSetToInstance(resultSet, true);
    }

    public List<T> saveOrUpdateAll(List<T> toSaves) throws SQLException {
        List<T> result = new ArrayList<>();
        for(T toSave: toSaves){
            result.add(saveOrUpdate(toSave));
        }
        return result;
    }

    public void deleteAll() throws SQLException {
        String query = queryGenerator.configure(QueryTemplate.deleteAll());
        PreparedStatement statement = statementWrapper.prepared(query, List.of());
        statement.executeUpdate();
    }

    public void deleteById(Object id) throws SQLException {
        String query = queryGenerator.configure(QueryTemplate.deleteById());
        PreparedStatement statement = statementWrapper.prepared(query, List.of(id));
        statement.executeUpdate();
    }

    public String joinAttributesNames(String limiter) {
        return reflectEntity.getAttributes()
                .stream()
                .map(ReflectAttribute::getOriginalColumnName)
                .collect(Collectors.joining(limiter));
    }

    public String joinAttributesNamesWithoutId(String limiter){
        return reflectEntity.getAttributes()
                .stream()
                .filter(attribute-> !attribute.isId())
                .map(ReflectAttribute::getOriginalColumnName)
                .collect(Collectors.joining(limiter));
    }

    public ReflectEntity<T> getReflectEntity() {
        return reflectEntity;
    }

    public QueryGenerator<T> getQueryGenerator() {
        return queryGenerator;
    }

    public StatementWrapper getStatementWrapper() {
        return statementWrapper;
    }

    public String getSELECT_ALL_QUERY() {
        return SELECT_ALL_QUERY;
    }

    public ResultSetMapper<T> getResultSetMapper() {
        return resultSetMapper;
    }

    public void setResultSetMapper(ResultSetMapper<T> resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
    }

    public void setSELECT_ALL_QUERY(String SELECT_ALL_QUERY) {
        this.SELECT_ALL_QUERY = SELECT_ALL_QUERY;
    }
}
