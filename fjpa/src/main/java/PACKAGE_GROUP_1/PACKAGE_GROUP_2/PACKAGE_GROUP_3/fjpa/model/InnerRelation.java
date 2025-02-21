package {{PACKAGE_GROUP}}.fjpa.model;

import java.util.Objects;

public class InnerRelation {
    private ReflectAttribute attribute;
    private String sqlDestination;
    private String destination;

    public InnerRelation(ReflectAttribute attribute, String sqlDestination, String destination) {
        this.attribute = attribute;
        this.sqlDestination = sqlDestination;
        this.destination = destination;
    }

    public InnerRelation() {}

    public ReflectAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ReflectAttribute attribute) {
        this.attribute = attribute;
    }

    public String getSqlDestination() {
        return sqlDestination;
    }

    public void setSqlDestination(String sqlDestination) {
        this.sqlDestination = sqlDestination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InnerRelation that = (InnerRelation) o;
        return Objects.equals(attribute, that.attribute) && Objects.equals(sqlDestination, that.sqlDestination) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, sqlDestination, destination);
    }

    @Override
    public String toString() {
        return "InnerRelation{" +
                "attribute=" + attribute +
                ", sqlDestination='" + sqlDestination + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
