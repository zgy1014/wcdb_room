package com.wcdb.tool.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wcdb.tool.constant.StringConstant;
import com.tencent.wcdb.database.SQLiteCipherSpec;
import com.tencent.wcdb.room.db.WCDBOpenHelperFactory;

import java.io.File;


public class AppDataUtils  {

    private static boolean flag = false;

    public static AppDatabase initRooms(Context mContext, boolean flags) {
        flag = flags;
        AppDatabase db =  initRoom(mContext);
        if(db.isOpen()) {
            db.getOpenHelper().close();
        }
        db= initRoom(mContext);
        return db;

    }


    /**
     *
     */
    public static AppDatabase initRoom(Context mContext) {
        SQLiteCipherSpec cipherSpec = new SQLiteCipherSpec()
                .setPageSize(4096)
                .setKDFIteration(64000);

        WCDBOpenHelperFactory factory = new WCDBOpenHelperFactory()
//                  .passphrase("passphrase".getBytes())  // passphrase to the database, remove this line for plain-text
                .cipherSpec(cipherSpec)
                // cipher to use, remove for default settings
                // enable WAL mode, remove if not needed
                .asyncCheckpointEnabled(true);        // enable asynchronous checkpoint, remove if not needed


//        Migration MIGRATION_1_2 = new Migration(1, 2) {
//            @Override
//            public void migrate(SupportSQLiteDatabase database) {
////                    为旧表添加新的字段
////                    database.execSQL("ALTER TABLE User "
////                            + " ADD COLUMN book_id TEXT");
////                创建新的数据表
//                database.execSQL("CREATE TABLE IF NOT EXISTS `CalenderInfo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subId` TEXT, `eventId` TEXT, `flagId` TEXT, `startTime` TEXT)");
//            }
//        };
//        Migration MIGRATION_1_2 = new Migration(1, 2) {
//            @Override
//            public void migrate(SupportSQLiteDatabase database) {
////                    为旧表添加新的字段，添加int类型的字段时候，要不为空，给 默认值
//                    database.execSQL("ALTER TABLE SmallTable "
//                            + " ADD COLUMN delayCount INTEGER NOT NULL DEFAULT 0");
//
//            }
//        };

        File dir =  new File(StringConstant.STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
//        File file = new File(dir, StringConstant.DbBase);
        AppDatabase db = null;
        if (flag) {
            db = Room.databaseBuilder(mContext, AppDatabase.class,  StringConstant.DbBase)
                    .createFromFile(new File(dir, StringConstant.DbBase))
                  //  .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .openHelperFactory(factory)   // specify WCDBOpenHelperFactory when opening database
                    .build();

        }else {
            db = Room.databaseBuilder(mContext, AppDatabase.class,  StringConstant.DbBase)
                 //   .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .openHelperFactory(factory)   // specify WCDBOpenHelperFactory when opening database
                    .build();
        }

        return db;



    }



}
