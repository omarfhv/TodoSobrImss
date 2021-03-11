package com.todimssayuda.todosobrimss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 27/08/2018.
 */

public class ContactSustis {


    public long idsustis;
    public String fechasustis;
    public String nombresustis;
    public String phonesustis;
    public String rbtnsustis;
    public String chbx;


    public long getId() {
        return idsustis;

    }

    // Será utilizado por ArrayAdapter en ListView
    //    @Override
    public String toString() {
        return fechasustis + " " + rbtnsustis;
    }

    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] ColumnasSustis = {Database.SUSTIS_ID,
            Database.SUSTIS_FECHAS,
            Database.SUSTI_NOMBRES,
            Database.SUSTI_TELEFONOS,
            Database.SUSTI_RBTNS,
            Database.SUSTI_CHBX};


    public ContactSustis(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

    }

    public ContactSustis createSustis(String fechasustis, String nombresustis, String phonesustis, String rbtnsustis, String chbx) {
        ContentValues values = new ContentValues();
        values.put(Database.SUSTIS_FECHAS, fechasustis);
        values.put(Database.SUSTI_NOMBRES, nombresustis);
        values.put(Database.SUSTI_TELEFONOS, phonesustis);
        values.put(Database.SUSTI_RBTNS, rbtnsustis);
        values.put(Database.SUSTI_CHBX, chbx);
        long insertId = database.insert(Database.TABLE_SUSTIS, null,
                values);
        Cursor cursor = database.query(Database.TABLE_SUSTIS,
                ColumnasSustis, Database.SUSTIS_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ContactSustis newContact = cursorToContact(cursor);
        cursor.close();

        return newContact;
    }

    public void updateSustis(long idsustis, String fechasustis, String nombresustis, String phonesustis, String rbtnsustis, String chbx) {
        ContentValues values = new ContentValues();
        values.put(Database.SUSTIS_FECHAS, fechasustis);
        values.put(Database.SUSTI_NOMBRES, nombresustis);
        values.put(Database.SUSTI_TELEFONOS, phonesustis);
        values.put(Database.SUSTI_RBTNS, rbtnsustis);
        values.put(Database.SUSTI_CHBX, chbx);
        String where = "sustiid=?";
        String[] whereargs = new String[]{String.valueOf(idsustis)};
        long insertId = database.update(Database.TABLE_SUSTIS,
                values, where, whereargs);
        Cursor cursor = database.query(Database.TABLE_SUSTIS,
                ColumnasSustis, Database.SUSTIS_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteContactSustis(long idsustis) {
        System.out.println("Contact deleted with id: " + idsustis);
        database.delete(Database.TABLE_SUSTIS, Database.SUSTIS_ID
                + " = " + idsustis, null);
    }

    public List<ContactSustis> getAll() {
        List<ContactSustis> comments = new ArrayList<ContactSustis>();

        Cursor cursor = database.query(Database.TABLE_SUSTIS,
                ColumnasSustis, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ContactSustis contact = cursorToContact(cursor);
            comments.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private ContactSustis cursorToContact(Cursor cursor) {
        ContactSustis c = new ContactSustis(null);
        c.idsustis = cursor.getLong(0);
        c.fechasustis = cursor.getString(1);
        c.nombresustis = cursor.getString(2);
        c.phonesustis = cursor.getString(3);
        c.rbtnsustis = cursor.getString(4);
        c.chbx = cursor.getString(5);
        return c;
    }


}