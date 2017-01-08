package ru.startandroid.messagelist.storage.database.specification;

public interface SqlSpecificationWhere {
    String getWhereClause();
    String[] getWhereArgs();
}
