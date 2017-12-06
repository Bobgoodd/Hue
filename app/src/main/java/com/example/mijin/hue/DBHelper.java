package com.example.mijin.hue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mijin.hue.ProjectTab.Tab3.WorkViewItem;

import java.util.ArrayList;

/**
 * Created by mijin on 2017-11-27.
 */
public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE TODO (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, checked BOOLEAN, progress INTEGER);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String item, boolean checked, int progress) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO TODO VALUES(null, '" + item + "', '" + checked + "', '"+progress+"');");
        db.close();
    }

    public void update(String item, int _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE TODO SET item=" + item + " WHERE _id='" + _id + "';");
        db.close();
    }

    public void update(boolean checked, int _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE TODO SET checked=" + checked + " WHERE _id='" + _id + "';");
        db.close();
    }

    public void update(int progress, int _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE TODO SET progress=" + progress + " WHERE _id='" + _id + "';");
        db.close();
    }

    public void delete(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM TODO WHERE _id='" + _id + "';");
        db.close();
    }

    public ArrayList<WorkViewItem> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<WorkViewItem> workViewItems = new ArrayList<WorkViewItem>();
        WorkViewItem workViewItem;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM TODO", null);
        while (cursor.moveToNext()) {
            //workViewItem = new WorkViewItem(cursor.getInt(0),cursor.getString(1),Boolean.parseBoolean(cursor.getString(2)),cursor.getInt(3));
            //workViewItems.add(workViewItem);
        }

        return workViewItems;
    }
}

