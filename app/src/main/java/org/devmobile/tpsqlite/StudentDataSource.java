package org.devmobile.tpsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource {
    private SQLiteDatabase db;
    private MySqLiteHelper dbHelper;
    private String[] lesColums = MySqLiteHelper.getTableName();

    public StudentDataSource(Context context){
        dbHelper = new MySqLiteHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }



    public Student creerAdherant (String nom, String tel){
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLUMN_NOM,nom);
        values.put(MySqLiteHelper.COLUMN_TEL,tel);

        long idAdherantInsert = db.insert(MySqLiteHelper.TABLE_STUDENT, null, values);
        Cursor cursor = db.query(MySqLiteHelper.TABLE_STUDENT, lesColums, MySqLiteHelper.COLUMN_ID + " = " +
                idAdherantInsert, null, null, null,null );
        cursor.moveToFirst();
        Student student = cursortoAdherant(cursor);
        cursor.close();
        return student;
    }

    public void updateAdherant (Student student){
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLUMN_NOM, student.getNom());
        values.put(MySqLiteHelper.COLUMN_TEL, student.getTel());

        db.update(MySqLiteHelper.TABLE_STUDENT, values,
                MySqLiteHelper.COLUMN_ID + " = ?",
                new  String[]{String.valueOf(student.getId())}

        );

    }

    public void deleteAdherant(Student student){
        long id = student.getId();
        db.delete(MySqLiteHelper.TABLE_STUDENT,
                MySqLiteHelper.COLUMN_ID + " = " + id , null
        );
    }
    public List<Student> getAllAdherants(){
        List<Student> students = new ArrayList<Student>();

        Cursor cursor = db.query(MySqLiteHelper.TABLE_STUDENT,
                lesColums, null, null, null, null, null
        );
        if (!cursor.moveToNext()) {
            return new ArrayList<>();
        }
        do {
            students.add(cursortoAdherant(cursor));
        }while (cursor.moveToNext());
        cursor.close();
        return students;
    }

    private Student cursortoAdherant(Cursor cursor) {
        final int idIndex = cursor.getColumnIndex(MySqLiteHelper.COLUMN_ID);
        final int nameIndex = cursor.getColumnIndex(MySqLiteHelper.COLUMN_NOM);
        final int telIndex = cursor.getColumnIndex(MySqLiteHelper.COLUMN_TEL);

        Student student = new Student();
        student.setId(cursor.getLong(idIndex));
        student.setNom(cursor.getString(nameIndex));
        student.setTel(cursor.getString(telIndex));

        return student;
    }
}

