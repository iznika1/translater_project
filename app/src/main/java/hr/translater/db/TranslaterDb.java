package hr.translater.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Igor on 28.1.2017..
 */


@Database(name = TranslaterDb.NAME, version = TranslaterDb.VERSION)
public class TranslaterDb {
    public static final String NAME = "TranslaterDb";

    public static final int VERSION = 1;
}