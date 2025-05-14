package {{PACKAGE_GROUP}}.fjpa;

import {{PACKAGE_GROUP}}.fjpa.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class QueryGenerator<T> {
    private final ReflectEntity<T> reflectEntity;
    private static List<String> usedRelations = new ArrayList<>();
    private static List<InnerRelation> innerRelations = new ArrayList<>();

    public QueryGenerator(ReflectEntity<T> reflectEntity) {
        this.reflectEntity = reflectEntity;
    }

    public String getSqlFormat(ReflectAttribute attribute, boolean isUpdate){
        return isUpdate ? attribute.getSqlColumnName() : String.format("%s.%s", attribute.getSqlTableName(), attribute.getSqlColumnName());
    }

    public String configure(String query){
        return configure(query, false);
    }

    public String configure(String query, boolean isUpdate){
        query = query
                    .replace("@entity.", "@")
                    .replace("@entity", reflectEntity.getSqlTableName())
                    .replace("@id", getSqlFormat(reflectEntity.getIdAttribute(), isUpdate));

        for(ReflectAttribute attribute: reflectEntity.getAttributes()) {
            if (!query.contains("@")) {
                break;
            }
            query = query.replace(
                String.format("@%s", attribute.getFieldName()),
                getSqlFormat(attribute, isUpdate)
            );
        }

        return query;
    }

    public String getFields(){
        return reflectEntity
                .getAttributes()
                .stream().filter(attribute -> (
                    !usedRelations.contains(getRelationName(attribute)) &&
                    !usedRelations.contains(getReverseRelationName(attribute))
                )).map(attribute -> {
                    StringBuilder simpleFields = new StringBuilder();

                    simpleFields.append(String.format(
                        "%s.%s as \"%s.%s\"",
                        attribute.getSqlTableName(),
                        attribute.getSqlColumnName(),
                        attribute.getOriginalTableName(),
                        attribute.getOriginalColumnName()
                    ));

                    if(attribute.isRelation()){
                        final int lastIndex = innerRelations.size();
                        ChildGenerator childGenerator = getAttributeQueryGenerator(attribute);
                        simpleFields.append(" , ");
                        simpleFields.append(childGenerator.getQueryGenerator().getFields());

                        innerRelations.add(lastIndex > 0 ? lastIndex - 1 : 0,
                            new InnerRelation (
                                attribute,
                                childGenerator.getReflectEntity().getOriginalTableName(),
                                childGenerator.getReflectEntity().getSqlTableName()
                            )
                        );
                    }
                    return simpleFields.toString();
                }).collect(Collectors.joining(" , "));
    }

    public String selectAll(){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(getFields());
        queryBuilder.append(" FROM ");
        queryBuilder.append(reflectEntity.getSqlTableName());

        innerRelations.forEach(innerRelation -> {
            String query = String.format(
                " INNER JOIN %s ON %s.\"%s\" = %s.%s",
                innerRelation.getSqlDestination(),
                innerRelation.getSqlDestination(),
                innerRelation.getAttribute().getRefColumnName(),
                innerRelation.getAttribute().getSqlTableName(),
                innerRelation.getAttribute().getSqlColumnName()
            );
            queryBuilder.append(query);
        });
        usedRelations = new ArrayList<>();
        innerRelations = new ArrayList<>();
        return queryBuilder.toString();
    }

    public ChildGenerator getAttributeQueryGenerator(ReflectAttribute attribute){
        ReflectEntity<?> childEntity = new ReflectEntity<>(attribute.getClazz());
        usedRelations.add(getRelationName(attribute));
        usedRelations.add(getReverseRelationName(attribute));

        QueryGenerator<?> childGenerator = new QueryGenerator<>(childEntity);
        return new ChildGenerator(childGenerator, childEntity);
    }

    public static String getRelationName(ReflectAttribute attribute){
        return attribute.getOriginalTableName() + attribute.getFieldName();
    }

    public static String getReverseRelationName(ReflectAttribute attribute){
        return attribute.getFieldName() + attribute.getOriginalTableName();
    }

    public static String orderSuffix(String orderBy, Order order){
        return String.format(" ORDER BY @%s %s", orderBy, order.toString());
    }

    public static FilterValues queryCondition(Map<String, Object> queries){
        StringBuilder queryBuilder = new StringBuilder();
        List<Object> values = new ArrayList<>();

        boolean isFirstQuery = true;

        for(Map.Entry<String, Object> query : queries.entrySet()){
            if(!Objects.nonNull(query.getValue())){
                continue;
            }
            values.add(query.getValue());

            if(!isFirstQuery){
                queryBuilder.append(" AND ");
            }
            isFirstQuery = false;
            queryBuilder.append(String.format(" @%s = ? ", query.getKey()));
        }

        return new FilterValues(queryBuilder.toString(), values);
    }
}
