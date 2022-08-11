# ​

最近项目有需要用到wcdb数据库，并且保证和IOS互通数据，在网上找很多相关资料，最后还是靠自己一点点摸索成功，现在做个总结。

一、在gradle 里加上 WCDB 相关的 room 组件
def room_version = "2.3.0"

    // wcdb数据库和room数据库组合使用
    implementation 'com.tencent.wcdb:wcdb-android:1.0.8'
    implementation 'com.tencent.wcdb:room:1.0.8'
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"

二、在项目需要用到的界面中初始化wcdb数据库，我是写了一个工具类，直接调用即可，先具体来看看
首先打开 RoomDatabase 时，指定 WCDBOpenHelperFactory 作为 openFactory

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
其次创建数据库

    //dbName可以使用单独的名字或者绝对路径
        AppDatabase   db = Room.databaseBuilder(mContext, AppDatabase.class,  "wcdb.db")
                    //创建本地数据库文件，如果需要和后台数据库绑定，需要和IOS互通就需要，否则只是自己本地操作，不需要
                    //如果需要用到互通保存到本地，记得先加存储和查询权限
                   // .createFromFile(new File(dir, "wcdb.db"))
                    //数据库版本更新
                  //  .addMigrations(MIGRATION_1_2)
                    ///允许主线程查询
                    .allowMainThreadQueries()
                    //数据库的模式，可以根据自己的需要设置，TRUNCATE模式只创建2个数据库，而且如果需要用到和IOS交互，上传数据库时，用TRUNCATE模式
                    .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .openHelperFactory(factory)   // 使用WCDB打开Room
                    .build();
三、ROOM数据库的使用
1.创建实体类

这里可以根据自己的业务需求，是定义id为自增的主键还是自己定义其他的自增主键。因为我要和IOS互通，所以我们都自己定义了相同的自增主键。

这里需要特别注意的是，因为考虑到和ios互通，所以在创建数据库的时候要和ios沟通好，要他们保证数据库的非空字段，不然等到后期把在ios手机上创建出来的数据库从服务器下载下来后，会报错。具体什么错误忘记了，嘿嘿嘿，这样错误研究了N久，这里这边注明一下！

如果不需要考虑互通，上诉可以忽略！

@Entity
public class FlagTable {

//    @PrimaryKey(autoGenerate = true)
//    public int id;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String flagId;

    public String title;

    public String subTitle;

    public int color;

    public String completeDay;

    public boolean isComplete ;

    public long createTime;


}
2.创建数据库Dao

这里是数据库的增删查改CRUD都有，可以根据自己的业务逻辑自己写。


@Dao
public interface FlagInfoDao {

    @Query("SELECT * FROM FlagTable")
    List<FlagTable> getAll();

    @Query("SELECT * FROM FlagTable WHERE flagId = :flagId")
    FlagTable getById(String flagId);

    @Query("SELECT * FROM FlagTable WHERE isComplete = :isComplete order by createTime asc")
    List<FlagTable> getAll(boolean isComplete);

    @Insert
    void insert(FlagTable... flagInfo);

    @Delete
    void delete(FlagTable flagInfo);

    @Query("DELETE FROM FlagTable WHERE flagId = :flagId")
    void deleteFlagById(String flagId);

    @Query("DELETE FROM FlagTable")
    void deleteAll();

    @Update
    void update(FlagTable flagInfo);
}
3.创建DataBase数据库

@Database(entities = {FlagTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FlagInfoDao flagInfoDao();


}
四、数据库升级（使用此方法记得修改数据库版本号）
这个要说明一下，当开发中使用了Google的Room框架的话，当你在之后的版本中新增了表或者改动了某些表结构的话，你就需要对数据库的版本号进行相应的更新，现在整理两种更新方式

1.这种方式会清空数据库中的数据，所以要使用这种方式之前一定要慎重考虑。fallbackToDestructiveMigration会将所有表全部丢弃。



AppDatabase db = null;
//dbName可以使用单独的名字或者绝对路径
db = Room.databaseBuilder(mContext, AppDatabase.class,  "wcdb.db")
//添加下面这一行
.fallbackToDestructiveMigration()
.build();
2. SQLite的ALTER TABLE命令非常局限，只支持重命名表以及添加新的字段。

//添加新的字段

        Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
           public void migrate(SupportSQLiteDatabase database) {
//                    为旧表添加新的字段，添加int类型的字段时候，要不为空，给 默认值
database.execSQL("ALTER TABLE FlagTable "
+ " ADD COLUMN delayCount INTEGER NOT NULL DEFAULT 0");

           }
       };
//创建新的表


        Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
//                    为旧表添加新的字段
database.execSQL("ALTER TABLE User "
+ " ADD COLUMN book_id TEXT");
//                创建新的数据表
database.execSQL("CREATE TABLE IF NOT EXISTS `CalenderInfo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subId` TEXT, `eventId` TEXT, `flagId` TEXT, `startTime` TEXT)");
}
};
在创建数据库的时候添加addMigrations

AppDatabase   db = Room.databaseBuilder(mContext, AppDatabase.class,  "wcdb.db")

                    //数据库版本更新
                    .addMigrations(MIGRATION_1_2)
               
                    .build();
五、查看数据库数据
1.获取数据库文件 ，并需要上传给后台，可以调用此方法，获取数据库具体地址

String  flag = context.getDatabasePath(“wcdb.db”)
2.查看数据库数据

手机连接studio，创建数据库并操作数据库后，在data目录下会出现两个文件。如图：



下载保存到桌面，并拖入SQLiteStudio数据库打开，双击数据库名打开后，能很详细的看到数据库变，表结构字段以及数据信息。如图：



六、最近抽时间自己写了个小demo，希望等帮助到需要的小伙伴。

https://github.com/zgy1014/wcdb_room#wcbd_room

本文算是自己在项目中使用WCDB过程中一些使用心得和积累，WCDB在使用这一块其实还有更多高级的用法，这里并没有提到，后面有时间了再做详述。也欢迎小伙伴们遇到问题一起解决。

​