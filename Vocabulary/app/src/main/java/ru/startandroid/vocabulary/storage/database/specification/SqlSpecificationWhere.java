package ru.startandroid.vocabulary.storage.database.specification;

public interface SqlSpecificationWhere {
    String getWhereClause();
    String[] getWhereArgs();
}
