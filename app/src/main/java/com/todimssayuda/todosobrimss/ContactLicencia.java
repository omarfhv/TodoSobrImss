package com.todimssayuda.todosobrimss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 30/08/2018.
 */
public class ContactLicencia {

    public long idlicencia;
    public String fechainiciolicencia;
    public String fechafinallicencia;
    public String rbtnlicencia;
    public String motivolicencia;
    public String idimagen;

    public long getId() {
        return idlicencia;
    }

    public String toString() {
        return fechainiciolicencia + " " + rbtnlicencia;
    }

    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] ColumnasLicencia = {Database.LICENCIA_ID,
            Database.LICENCIA_FECHAINICIO,
            Database.LICENCIA_FECHAFINAL,
            Database.LICENCIA_RBTNS,
            Database.LICENCIA_MOTIVO};


    public ContactLicencia(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

    }

    public ContactLicencia createLicencia(String fechainiciolicencia, String fechafinallicencia, String rbtnlicencia, String motivolicencia) {
        ContentValues values = new ContentValues();
        values.put(Database.LICENCIA_FECHAINICIO, fechainiciolicencia);
        values.put(Database.LICENCIA_FECHAFINAL, fechafinallicencia);
        values.put(Database.LICENCIA_RBTNS, rbtnlicencia);
        values.put(Database.LICENCIA_MOTIVO, motivolicencia);
        long insertId = database.insert(Database.TABLE_LICENCIA, null,
                values);
        Cursor cursor = database.query(Database.TABLE_LICENCIA,
                ColumnasLicencia, Database.LICENCIA_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ContactLicencia newContact = cursorToContact(cursor);
        cursor.close();

        return newContact;
    }

    public void updateLicencia(long idlicencia, String fechainiciolicencia, String fechafinallicencia, String rbtnlicencia, String motivolicencia) {
        ContentValues values = new ContentValues();
        values.put(Database.LICENCIA_FECHAINICIO, fechainiciolicencia);
        values.put(Database.LICENCIA_FECHAFINAL, fechafinallicencia);
        values.put(Database.LICENCIA_RBTNS, rbtnlicencia);
        values.put(Database.LICENCIA_MOTIVO, motivolicencia);
        String where = "licenciaid=?";
        String[] whereargs = new String[]{String.valueOf(idlicencia)};
        long insertId = database.update(Database.TABLE_LICENCIA,
                values, where, whereargs);
        Cursor cursor = database.query(Database.TABLE_LICENCIA,
                ColumnasLicencia, Database.LICENCIA_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteContactLicencia(long idlicencia) {
        System.out.println("Contact deleted with id: " + idlicencia);
        database.delete(Database.TABLE_LICENCIA, Database.LICENCIA_ID
                + " = " + idlicencia, null);
    }

    public List<ContactLicencia> getAll() {
        List<ContactLicencia> comments = new ArrayList<ContactLicencia>();

        Cursor cursor = database.query(Database.TABLE_LICENCIA,
                ColumnasLicencia, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           ContactLicencia contact = cursorToContact(cursor);
            comments.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private ContactLicencia cursorToContact(Cursor cursor) {
        ContactLicencia c = new ContactLicencia(null);
        c.idlicencia = cursor.getLong(0);
        c.fechainiciolicencia = cursor.getString(1);
        c.fechafinallicencia = cursor.getString(2);
        c.rbtnlicencia = cursor.getString(3);
        c.motivolicencia = cursor.getString(4);
        return c;
    }


}
