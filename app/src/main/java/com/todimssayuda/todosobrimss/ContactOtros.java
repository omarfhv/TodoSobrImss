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

public class ContactOtros {
    public long idotros;
    public String fechaotros;
    public String documentootros;
    public String motivootros;



    public long getId() {
        return idotros;

    }

    // Será utilizado por ArrayAdapter en ListView
    //    @Override
    public String toString() {
        return fechaotros + " " + documentootros;
    }

    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] ColumnasOtros = {Database.OTROS_ID,
            Database.OTROS_FECHA,
            Database.OTROS_DOCUMENTOS,
            Database.OTROS_MOTIVO};


    public ContactOtros(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

    }

    public ContactOtros createOtros(String fechaotros, String documentootros, String motivootros) {
        ContentValues values = new ContentValues();
        values.put(Database.OTROS_FECHA, fechaotros);
        values.put(Database.OTROS_DOCUMENTOS, documentootros);
        values.put(Database.OTROS_MOTIVO,motivootros);
        long insertId = database.insert(Database.TABLE_OTROS, null,
                values);
        Cursor cursor = database.query(Database.TABLE_OTROS,
                ColumnasOtros, Database.OTROS_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ContactOtros newContact = cursorToContact(cursor);
        cursor.close();

        return newContact;
    }

    public void updateOtros(long idotros, String fechaotros, String documentootros, String motivootros) {
        ContentValues values = new ContentValues();
        values.put(Database.OTROS_FECHA, fechaotros);
        values.put(Database.OTROS_DOCUMENTOS, documentootros);
        values.put(Database.OTROS_MOTIVO,motivootros);

        String where = "otrosid=?";
        String[] whereargs = new String[]{String.valueOf(idotros)};
        long insertId = database.update(Database.TABLE_OTROS,
                values, where, whereargs);
        Cursor cursor = database.query(Database.TABLE_OTROS,
                ColumnasOtros, Database.OTROS_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteContactOtros(long idotros) {
        System.out.println("Contact deleted with id: " + idotros);
        database.delete(Database.TABLE_OTROS, Database.OTROS_ID
                + " = " + idotros, null);
    }

    public List<ContactOtros> getAll() {
        List<ContactOtros> comments = new ArrayList<ContactOtros>();

        Cursor cursor = database.query(Database.TABLE_OTROS,
                ColumnasOtros, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           ContactOtros contact = cursorToContact(cursor);
            comments.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private ContactOtros cursorToContact(Cursor cursor) {
        ContactOtros c = new ContactOtros(null);
        c.idotros = cursor.getLong(0);
        c.fechaotros = cursor.getString(1);
        c.documentootros = cursor.getString(2);
        c.motivootros = cursor.getString(3);

        return c;
    }


}
