package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUtil {
	private static DBUtil mInstance;
	private Context mContext;
	private SQLHelper mSQLHelp;
	private SQLiteDatabase mSQLiteDatabase;

	private DBUtil(Context context) {
		mContext = context;
		mSQLHelp = new SQLHelper(context);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
	}
	/**
	 * 初始化数据库操作DBUtil类
	 */
	public static DBUtil getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBUtil(context);
		}
		return mInstance;
	}
	/**
	 * 关闭数据库
	 */
	public void close() {
		mSQLHelp.close();
		mSQLHelp = null;
		mSQLiteDatabase.close();
		mSQLiteDatabase = null;
		mInstance = null;
	}

	/**
	 * 添加数据
	 */
	public void insertData(ContentValues values) {
		mSQLiteDatabase.insert(SQLHelper.TABLE_CHANNEL, null, values);
	}

	public void insertCollection(ContentValues values){
		long i = mSQLiteDatabase.insert(SQLHelper.COLLECTION_TABLE_NAME,null,values);
	}

	/**
	 * 更新数据
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public void updateData(ContentValues values, String whereClause,
                           String[] whereArgs) {
		mSQLiteDatabase.update(SQLHelper.TABLE_CHANNEL, values, whereClause,
				whereArgs);
	}

	/**
	 * 删除数据
	 * 
	 * @param whereClause
	 * @param whereArgs
	 */
	public void deleteData(String whereClause, String[] whereArgs) {
		mSQLiteDatabase
				.delete(SQLHelper.TABLE_CHANNEL, whereClause, whereArgs);
	}
	public void deleteCollection(String number, String title) {
		int i = mSQLiteDatabase.delete(SQLHelper.COLLECTION_TABLE_NAME,"phoneNumber = ? and title = ?",new String[]{number,title});
	}

	/**
	 * 查询数据
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor selectData(String[] columns, String selection,
                             String[] selectionArgs, String groupBy, String having,
                             String orderBy) {
		Cursor cursor = mSQLiteDatabase.query(SQLHelper.TABLE_CHANNEL,columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}

	public List<HashMap<String,String>> selectCollection(String colum,String info) {
		Cursor cursor = mSQLiteDatabase.query(SQLHelper.COLLECTION_TABLE_NAME,null,colum+" = ?",new String[]{info},null,null,null);
		List<HashMap<String,String>> hashMapList=new ArrayList<>();
		HashMap<String,String> hashMap;
		if (cursor.moveToFirst()){
			for (int i = 0; i<cursor.getCount(); i++){
				cursor.moveToPosition(i);
				hashMap=new HashMap<>();
				hashMap.put("phoneNumber",cursor.getString(1));
				hashMap.put("author",cursor.getString(2));
				hashMap.put("time",cursor.getString(3));
				hashMap.put("clickNumber",cursor.getString(4));
				hashMap.put("title",cursor.getString(5));
				hashMap.put("content",cursor.getString(6));
				hashMap.put("pictureURL",cursor.getString(7));
				hashMapList.add(hashMap);
			}
		}
		return hashMapList;
	}

	public int selectCollectionFuhe(String number,String count) {
		Cursor cursor = mSQLiteDatabase.query(SQLHelper.COLLECTION_TABLE_NAME,null,"phoneNumber = ? and title = ?",new String[]{number,count},null,null,null);
		return cursor.getCount();
	}

	public List<HashMap<String,String>> selectCollectionFuHe(String number,String count) {
		Cursor cursor = mSQLiteDatabase.query(SQLHelper.COLLECTION_TABLE_NAME,null,"phoneNumber = ? and title = ?",new String[]{number,count},null,null,null);
		List<HashMap<String,String>> hashMapList=new ArrayList<>();
		HashMap<String,String> hashMap;
		if (cursor.moveToFirst()){
			Log.e("TAG", "数据长度："+cursor.getCount());
			for (int i = 0; i < cursor.getCount(); i++){
				cursor.moveToPosition(i);
				hashMap=new HashMap<>();
				hashMap.put("phoneNumber",cursor.getString(1));
				hashMap.put("author",cursor.getString(2));
				hashMap.put("time",cursor.getString(3));
				hashMap.put("clickNumber",cursor.getString(4));
				hashMap.put("title",cursor.getString(5));
				hashMap.put("content",cursor.getString(6));
				hashMap.put("pictureURL",cursor.getString(7));
				hashMapList.add(hashMap);
			}
		}
		return hashMapList;
	}
}