package ru.startandroid.vocabulary.data.sentence.storage;


import ru.startandroid.vocabulary.data.sentence.Sentence;
import ru.startandroid.vocabulary.storage.database.ExercisesTable;
import ru.startandroid.vocabulary.storage.database.SentencesTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;

public class SqlSpecificationRawEnabledSentences implements SqlSpecificationRaw {
    @Override
    public String getQuery() {
        return String.format(
                "select * from %1$s inner join %2$s on %3$s = %4$s.%5$s where %6$s = ?",
                //from
                SentencesTable.TABLE_NAME,

                //inner join
                ExercisesTable.TABLE_NAME,
                // on
                SentencesTable.EXERCISE_ID,
                ExercisesTable.TABLE_NAME,
                ExercisesTable.ID,

                // where
                ExercisesTable.ENABLED
        );
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[]{String.valueOf(1)};
    }
}
