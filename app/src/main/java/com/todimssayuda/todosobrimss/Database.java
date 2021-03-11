package com.todimssayuda.todosobrimss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    public static final String TABLE_PASES = "pases";
    public static final String PASE_ID = "paseid";
    public static final String PASE_FECHAS = "pasefecha";
    public static final String PASE_RBTNS = "paserbtn";
    public static final String PASE_HORAS = "pasehora";
    public static final String PASE_MOTIVOS = "pasemotivo";
    public static final String PASE_IDIMAGEN = "idimagen";


    public static final String TABLE_SUSTIS = "sustis";
    public static final String SUSTIS_ID = "sustiid";
    public static final String SUSTIS_FECHAS = "sustisfecha";
    public static final String SUSTI_RBTNS = "sustibtn";
    public static final String SUSTI_NOMBRES = "sustinombre";
    public static final String SUSTI_TELEFONOS = "sustitel";
    public static final String SUSTI_CHBX = "chbxpago";


    public static final String TABLE_LICENCIA = "licencia";
    public static final String LICENCIA_ID = "licenciaid";
    public static final String LICENCIA_FECHAINICIO = "licenciafechainicio";
    public static final String LICENCIA_FECHAFINAL = "licenciafechafinal";
    public static final String LICENCIA_RBTNS = "licenciabtn";
    public static final String LICENCIA_MOTIVO = "licenciamotivo";

    public static final String TABLE_OTROS = "otros";
    public static final String OTROS_ID = "otrosid";
    public static final String OTROS_FECHA = "otrosfecha";
    public static final String OTROS_DOCUMENTOS = "otrosdocumentos";
    public static final String OTROS_MOTIVO = "otrosmotivo";

    private static final String DATABASE_NAME = "contacts.db";

    private static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    private static final String Pases = "create table "
            + TABLE_PASES + "(" + PASE_ID
            + " integer primary key autoincrement,"
            + PASE_FECHAS + " text not null,"
            + PASE_RBTNS + " text not null,"
            + PASE_HORAS + " text not null,"
            + PASE_MOTIVOS + " text not null,"
            + PASE_IDIMAGEN + " text not null)";

    private static final String Sustis = "create table "
            + TABLE_SUSTIS + "(" + SUSTIS_ID
            + " integer primary key autoincrement, "
            + SUSTIS_FECHAS + " text not null,"
            + SUSTI_RBTNS + " text not null,"
            + SUSTI_NOMBRES + " text not null,"
            + SUSTI_TELEFONOS + " text not null,"
            + SUSTI_CHBX + " text not null)";

    private static final String Licencia = "create table "
            + TABLE_LICENCIA + "(" + LICENCIA_ID
            + " integer primary key autoincrement, "
            + LICENCIA_FECHAINICIO + " text not null,"
            + LICENCIA_FECHAFINAL + " text not null,"
            + LICENCIA_RBTNS + " text not null,"
            + LICENCIA_MOTIVO + " text not null)";

    private static final String Otros = "create table "
            + TABLE_OTROS + "(" + OTROS_ID
            + " integer primary key autoincrement, "
            + OTROS_FECHA + " text not null,"
            + OTROS_DOCUMENTOS + " text not null,"
            + OTROS_MOTIVO + " text not null)";



    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Sustis);
        database.execSQL(Pases);
        database.execSQL(Licencia);
        database.execSQL(Otros);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(com.todimssayuda.todosobrimss.Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSTIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICENCIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTROS);


        onCreate(db);
    }
}