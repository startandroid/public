package ru.startandroid.messagelist.data.database.specification;

public interface SqlSpecificationWhere {
    String getWhereClause();
    String[] getWhereArgs();
}
