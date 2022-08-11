package com.wcdb.tool.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wcdb.tool.constant.StringConstant;
import com.tencent.wcdb.database.SQLiteCipherSpec;
import com.tencent.wcdb.room.db.WCDBOpenHelperFactory;

import java.io.File;


public class AppDataUtils  {


    public static AppDatabase initRooms(Context mContext) {
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
        //指定加密方式，使用默认加密可以省略
        SQLiteCipherSpec cipherSpec = new SQLiteCipherSpec()
                .setPageSize(4096)
                .setKDFIteration(64000);

        WCDBOpenHelperFactory factory = new WCDBOpenHelperFactory()
//                  .passphrase("passphrase".getBytes())  // // 指定加密DB密钥，非加密DB去掉此行
                .cipherSpec(cipherSpec)  //指定加密方式，使用默认加密可以省略
                // cipher to use, remove for default settings
                // enable WAL mode, remove if not needed
                //.writeAheadLoggingEnabled(true)   //// 打开WAL以及读写并发，可以省略让Room决定是否要打开
                .asyncCheckpointEnabled(true);        // 开异步Checkpoint优化，不需要可以省略


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

        //创建本地文件存储地址，
//        File dir =  new File(StringConstant.STORAGE_DIR);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        File file = new File(dir, StringConstant.DbBase);
        AppDatabase db = null;
            //dbName可以使用单独的名字或者绝对路径
            db = Room.databaseBuilder(mContext, AppDatabase.class,  StringConstant.DbBase)
                    //创建本地数据库文件，如果需要和后台数据库绑定，需要和IOS互通就需要，否则只是自己本地操作，不需要
                    //如果需要用到互通保存到本地，记得先加存储和查询权限
                   // .createFromFile(new File(dir, StringConstant.DbBase))
                    //数据库版本更新
                  //  .addMigrations(MIGRATION_1_2)
                    ///允许主线程查询
                    .allowMainThreadQueries()
                    //数据库的模式，可以根据自己的需要设置，TRUNCATE模式只创建2个数据库，而且如果需要用到和IOS交互，上传数据库时，用TRUNCATE模式
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .openHelperFactory(factory)   // 使用WCDB打开Room
                    .build();


        return db;



    }



}
