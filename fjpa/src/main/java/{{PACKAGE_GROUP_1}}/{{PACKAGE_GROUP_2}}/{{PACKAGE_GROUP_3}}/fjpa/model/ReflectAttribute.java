package {{PACKAGE_GROUP}}.fjpa.model;

import java.lang.reflect.Method;

public class ReflectAttribute {
    private String sqlColumnName;
    private String originalColumnName;
    private String originalTableName;
    private String sqlTableName;
    private String fieldName;
    private String valueGetter;
    private boolean required;
    private boolean isId;
    private Method setter;
    private Method getter;
    private boolean isRelation;
    private String refColumnName;
    private Class<?> clazz;

    public ReflectAttribute(String sqlColumnName, String originalColumnName, String originalTableName, String sqlTableName, String fieldName, String valueGetter, boolean required, boolean isId, Method setter, Method getter, boolean isRelation, String refColumnName, Class<?> clazz) {
        this.sqlColumnName = sqlColumnName;
        this.originalColumnName = originalColumnName;
        this.originalTableName = originalTableName;
        this.sqlTableName = sqlTableName;
        this.fieldName = fieldName;
        this.valueGetter = valueGetter;
        this.required = required;
        this.isId = isId;
        this.setter = setter;
        this.getter = getter;
        this.isRelation = isRelation;
        this.refColumnName = refColumnName;
        this.clazz = clazz;
    }

    public String getSqlColumnName() {
        return sqlColumnName;
    }

    public void setSqlColumnName(String sqlColumnName) {
        this.sqlColumnName = sqlColumnName;
    }

    public String getOriginalColumnName() {
        return originalColumnName;
    }

    public void setOriginalColumnName(String originalColumnName) {
        this.originalColumnName = originalColumnName;
    }

    public String getOriginalTableName() {
        return originalTableName;
    }

    public void setOriginalTableName(String originalTableName) {
        this.originalTableName = originalTableName;
    }

    public String getSqlTableName() {
        return sqlTableName;
    }

    public void setSqlTableName(String sqlTableName) {
        this.sqlTableName = sqlTableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValueGetter() {
        return valueGetter;
    }

    public void setValueGetter(String valueGetter) {
        this.valueGetter = valueGetter;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public boolean isRelation() {
        return isRelation;
    }

    public void setRelation(boolean relation) {
        isRelation = relation;
    }

    public String getRefColumnName() {
        return refColumnName;
    }

    public void setRefColumnName(String refColumnName) {
        this.refColumnName = refColumnName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ReflectAttribute() {}

    @Override
    public String toString() {
        return "ReflectAttribute{" +
                "sqlColumnName='" + sqlColumnName + '\'' +
                ", originalColumnName='" + originalColumnName + '\'' +
                ", originalTableName='" + originalTableName + '\'' +
                ", sqlTableName='" + sqlTableName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", valueGetter='" + valueGetter + '\'' +
                ", required=" + required +
                ", isId=" + isId +
                ", setter=" + setter +
                ", getter=" + getter +
                ", isRelation=" + isRelation +
                ", refColumnName='" + refColumnName + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
