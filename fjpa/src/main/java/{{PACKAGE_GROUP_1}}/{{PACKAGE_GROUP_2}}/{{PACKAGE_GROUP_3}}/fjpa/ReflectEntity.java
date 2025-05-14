package {{PACKAGE_GROUP}}.fjpa;

import {{PACKAGE_GROUP}}.fjpa.annotation.*;
import {{PACKAGE_GROUP}}.fjpa.model.ReflectAttribute;
import {{PACKAGE_GROUP}}.fjpa.utils.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Used to get metadata about the generic class
 * @param <T>
 */
public class ReflectEntity <T>{
    private final Class<T> clazz;
    private final String sqlTableName;
    private final String originalTableName;
    private final List<ReflectAttribute> attributes;
    private final ReflectAttribute idAttribute;

    public ReflectEntity(Class<T> clazz) {
        this.clazz = clazz;
        this. originalTableName = ReflectEntity.retrieveTableName(clazz);
        this.sqlTableName = Utils.toSQLName(originalTableName);
        this.attributes = retrieveAttributes(clazz);
        this.idAttribute = attributes.stream()
                .filter(ReflectAttribute::isId)
                .findFirst().orElseThrow(()->new RuntimeException("@Id is required for one field"));
    }

    public T createInstance(Map<ReflectAttribute, Object> argsValues) {
        Constructor<T> noArgsConstructor = Arrays.stream((Constructor<T>[]) clazz.getDeclaredConstructors())
                .filter(el -> el.getParameterCount() == 0)
                .findFirst().orElseThrow(()-> new RuntimeException("Entity must have noArgsConstructor"));
        try{
            T instance = noArgsConstructor.newInstance();
            return invokeSetters(instance, argsValues);
        }catch(InvocationTargetException | InstantiationException | IllegalAccessException error){
            throw new RuntimeException("Instantiation error for " + getOriginalTableName());
        }
    }

    public static  <T> Object invokeGetters(T instance, ReflectAttribute attribute) {
        try {
            Object object = attribute.getGetter().invoke(instance);
            if(attribute.getValueGetter() != null){
                Method setterMethod = Arrays.stream(object.getClass().getMethods())
                        .filter(method -> method.getName().equals(attribute.getValueGetter()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Value getter no found for field: " + attribute.getValueGetter()));
                return setterMethod.invoke(object);
            }
            return object;

        } catch (IllegalAccessException | InvocationTargetException error) {
            throw new RuntimeException(error);
        }
    }

    public T invokeSetters(T instance, Map<ReflectAttribute, Object> argsValues) {
        argsValues.forEach((attribute, value) -> {
            try {
                attribute.getSetter().invoke(instance, value);
            } catch (IllegalAccessException | InvocationTargetException error) {
                throw new RuntimeException(error);
            }
        });
        return instance;
    }

    public static String retrieveTableName(Class<?> clazz){
        if(!clazz.isAnnotationPresent(Entity.class)){
            throw new RuntimeException("Class must be annotated with @Entity to be used with fjpa");
        }
        Entity entity = clazz.getAnnotation(Entity.class);
        return entity.tableName().isEmpty() ? clazz.getSimpleName().toLowerCase() : entity.tableName();
    }

    public List<ReflectAttribute> retrieveAttributes(Class<?> clazz){
        List<ReflectAttribute> attributes = new ArrayList<>();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class)).toList();

        fields.forEach(field -> {
            Column clAnnotation = field.getAnnotation(Column.class);
            String setterMethodName = String.format("set%s", Utils.toCamelCase(field.getName()));
            String getterMethodName = String.format("get%s", Utils.toCamelCase(field.getName()));

            Method setterMethod = Arrays.stream(clazz.getMethods())
                    .filter(method -> method.getName().equals(setterMethodName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Setter method not found for field: " + field.getName()));

            Method getterMethod = Arrays.stream(clazz.getMethods())
                    .filter(method -> method.getName().equals(getterMethodName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Getter method not found for field: " + field.getName()));

            final boolean isRelation = field.isAnnotationPresent(Relation.class);
            Relation rRef = null;

            if(isRelation){
                rRef = field.getAnnotation(Relation.class);
            }

            ValueGetter valueGetter = null;
            if(field.isAnnotationPresent(ValueGetter.class)) {
                valueGetter = field.getAnnotation(ValueGetter.class);
            }

            String originalColumnName = clAnnotation.columnName().isEmpty() ? field.getName().toLowerCase() : clAnnotation.columnName();
            ReflectAttribute attribute =  new ReflectAttribute();
            attribute.setRelation(isRelation);
            attribute.setOriginalColumnName(originalColumnName);
            attribute.setSqlColumnName(Utils.toSQLName(originalColumnName));
            attribute.setOriginalTableName(originalTableName);
            attribute.setSqlTableName(sqlTableName);
            attribute.setValueGetter(valueGetter != null ? valueGetter.field() : null);
            attribute.setRequired(clAnnotation.required());
            attribute.setSetter(setterMethod);
            attribute.setGetter(getterMethod);
            attribute.setClazz(field.getType());
            attribute.setFieldName(field.getName());
            attribute.setRefColumnName(isRelation ? rRef.refColumnName() : "");
            attribute.setId(field.isAnnotationPresent(Id.class));

            attributes.add(attribute);
        });

        return attributes;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public String getSqlTableName() {
        return sqlTableName;
    }

    public String getOriginalTableName() {
        return originalTableName;
    }

    public List<ReflectAttribute> getAttributes() {
        return attributes;
    }

    public ReflectAttribute getIdAttribute() {
        return idAttribute;
    }
}
