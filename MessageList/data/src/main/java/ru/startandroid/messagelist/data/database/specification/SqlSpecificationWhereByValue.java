package ru.startandroid.messagelist.data.database.specification;

public class SqlSpecificationWhereByValue implements SqlSpecificationWhere {

    private final String columnName;
    private final String value;

    public SqlSpecificationWhereByValue(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    @Override
    public String getWhereClause() {
        return String.format("%1$s = ?",
                columnName);
    }


    @Override
    public String[] getWhereArgs() {
        return new String[]{value};
    }


}
