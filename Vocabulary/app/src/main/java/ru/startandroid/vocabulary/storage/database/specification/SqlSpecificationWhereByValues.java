package ru.startandroid.vocabulary.storage.database.specification;

import java.util.Set;

public class SqlSpecificationWhereByValues implements SqlSpecificationWhere {

    private final String columnName;
    private final String[] values;

    public SqlSpecificationWhereByValues(String columnName, String[] values) {
        this.columnName = columnName;
        this.values = values;
    }

    @Override
    public String getWhereClause() {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (int i = 1; i < values.length; i++) {
            sb.append(", ?");
        }
        return String.format("%1$s IN (%2$s)",
                columnName, sb.toString());
    }


    @Override
    public String[] getWhereArgs() {
        return values;
    }


}
