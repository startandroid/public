package ru.startandroid.messagelist.storage.database.specification;

import java.util.Collection;

import ru.startandroid.messagelist.storage.database.SqlUtils;

public class SqlSpecificationWhereByValues implements SqlSpecificationWhere {

    private final String columnName;
    private final Collection<String> values;

    public SqlSpecificationWhereByValues(String columnName, Collection<String> values) {
        this.columnName = columnName;
        this.values = values;
    }

    @Override
    public String getWhereClause() {
        return SqlUtils.createWhereIn(columnName, values);
    }

    @Override
    public String[] getWhereArgs() {
        if (values == null) {
            return null;
        }
        return values.toArray(new String[values.size()]);
    }
}
