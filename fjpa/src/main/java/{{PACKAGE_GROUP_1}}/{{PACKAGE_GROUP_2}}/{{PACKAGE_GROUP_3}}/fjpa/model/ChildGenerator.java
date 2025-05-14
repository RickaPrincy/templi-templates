package {{PACKAGE_GROUP}}.fjpa.model;

import {{PACKAGE_GROUP}}.fjpa.QueryGenerator;
import {{PACKAGE_GROUP}}.fjpa.ReflectEntity;

import java.util.Objects;

public class ChildGenerator {
    private QueryGenerator<?> queryGenerator;
    private ReflectEntity<?> reflectEntity;

    public ChildGenerator(QueryGenerator<?> queryGenerator, ReflectEntity<?> reflectEntity) {
        this.queryGenerator = queryGenerator;
        this.reflectEntity = reflectEntity;
    }

    public ChildGenerator() {}

    public QueryGenerator<?> getQueryGenerator() {
        return queryGenerator;
    }

    public void setQueryGenerator(QueryGenerator<?> queryGenerator) {
        this.queryGenerator = queryGenerator;
    }

    public ReflectEntity<?> getReflectEntity() {
        return reflectEntity;
    }

    public void setReflectEntity(ReflectEntity<?> reflectEntity) {
        this.reflectEntity = reflectEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildGenerator that = (ChildGenerator) o;
        return Objects.equals(queryGenerator, that.queryGenerator) && Objects.equals(reflectEntity, that.reflectEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryGenerator, reflectEntity);
    }

    @Override
    public String toString() {
        return "ChildGenerator{" +
                "queryGenerator=" + queryGenerator +
                ", reflectEntity=" + reflectEntity +
                '}';
    }
}
