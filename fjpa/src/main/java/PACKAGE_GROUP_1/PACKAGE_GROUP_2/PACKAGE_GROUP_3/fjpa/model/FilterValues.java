package {{PACKAGE_GROUP}}.fjpa.model;

import java.util.List;
import java.util.Objects;

public class FilterValues {
    private String query;
    private List<Object> values;

    public FilterValues(String query, List<Object> values) {
        this.query = query;
        this.values = values;
    }

    public FilterValues() {}

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterValues that = (FilterValues) o;
        return Objects.equals(query, that.query) && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, values);
    }

    @Override
    public String toString() {
        return "FilterValues{" +
                "query='" + query + '\'' +
                ", values=" + values +
                '}';
    }
}
