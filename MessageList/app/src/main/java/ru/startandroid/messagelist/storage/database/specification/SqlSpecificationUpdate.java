package ru.startandroid.messagelist.storage.database.specification;

import android.content.ContentValues;

public class SqlSpecificationUpdate {

    private final ContentValues cv;
    private final SqlSpecificationWhere sqlSpecificationWhere;

    public SqlSpecificationUpdate(ContentValues cv, SqlSpecificationWhere sqlSpecificationWhere) {
        this.cv = cv;
        this.sqlSpecificationWhere = sqlSpecificationWhere;
    }

    public ContentValues getContentValues() {
        return cv;
    }

    public String getWhereClause() {
        return sqlSpecificationWhere.getWhereClause();
    }

    public String[] getWhereArgs() {
        return sqlSpecificationWhere.getWhereArgs();
    }
}
