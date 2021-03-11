package com.todimssayuda.todosobrimss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evilnapsis on 3/29/16.
 */
public class Contact {
    public long id;
    public String fecha;
    public String radibuttonnid;
    public String horas;
    public String motivo;
    public String idimagen;


    public long getId() {
        return id;

    }


    // Será utilizado por ArrayAdapter en ListView
    //    @Override
    public String toString() {
        return fecha + " " + radibuttonnid;
    }


    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] columnasPase = {Database.PASE_ID,
            Database.PASE_FECHAS,
            Database.PASE_RBTNS,
            Database.PASE_HORAS,
            Database.PASE_MOTIVOS,
            Database.PASE_IDIMAGEN};

    public Contact(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

    }


    public com.todimssayuda.todosobrimss.Contact createPase(String fecha, String radibuttonnid, String horas, String motivo, String idimagen) {
        ContentValues values = new ContentValues();
        values.put(Database.PASE_FECHAS, fecha);
        values.put(Database.PASE_RBTNS, radibuttonnid);
        values.put(Database.PASE_HORAS, horas);
        values.put(Database.PASE_MOTIVOS, motivo);
        values.put(Database.PASE_IDIMAGEN, idimagen);
        long insertId = database.insert(Database.TABLE_PASES, null,
                values);
        Cursor cursor = database.query(Database.TABLE_PASES,
                columnasPase, Database.PASE_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        com.todimssayuda.todosobrimss.Contact newContact = cursorToContact(cursor);
        cursor.close();

        return newContact;
    }

    public void updatePase(long id, String name, String radibuttonnid, String horas, String motivo, String idimagen) {
        ContentValues values = new ContentValues();
        values.put(Database.PASE_FECHAS, name);
        values.put(Database.PASE_RBTNS, radibuttonnid);
        values.put(Database.PASE_HORAS, horas);
        values.put(Database.PASE_MOTIVOS, motivo);
        values.put(Database.PASE_IDIMAGEN, idimagen);
        String where = "paseid=?";
        String[] whereargs = new String[]{String.valueOf(id)};
        long insertId = database.update(Database.TABLE_PASES, values, where, whereargs);
        Cursor cursor = database.query(Database.TABLE_PASES,
                columnasPase, Database.PASE_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteContact(long id) {
        System.out.println("Contact deleted with id: " + id);
        database.delete(Database.TABLE_PASES, Database.PASE_ID
                + " = " + id, null);

    }

    public List<Contact> getAll() {
        List<Contact> comments = new ArrayList<Contact>();

        Cursor cursor = database.query(Database.TABLE_PASES,
                columnasPase, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            com.todimssayuda.todosobrimss.Contact contact = cursorToContact(cursor);
            comments.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private com.todimssayuda.todosobrimss.Contact cursorToContact(Cursor cursor) {
        com.todimssayuda.todosobrimss.Contact c = new com.todimssayuda.todosobrimss.Contact(null);
        c.id = cursor.getLong(0);
        c.fecha = cursor.getString(1);
        c.radibuttonnid = cursor.getString(2);
        c.horas = cursor.getString(3);
        c.motivo = cursor.getString(4);
        c.idimagen = cursor.getString(5);
        return c;
    }

}