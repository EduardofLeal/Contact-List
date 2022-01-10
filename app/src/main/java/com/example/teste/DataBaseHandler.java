package com.example.teste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "controleAgenda",
                                TABLE_CONTACTS = "contatos",
                                KEY_ID = "_id",
                                KEY_NAME = "nome",
                                KEY_PHONE = "telefone",
                                KEY_ADDRESS = "endereco",
                                KEY_EMAIL = "email",
                                KEY_IMAGEURI = "imagemUri";

    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT, "
                + KEY_PHONE + " TEXT, "
                + KEY_ADDRESS + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_IMAGEURI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public boolean createContact(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contato.get_nome());
        values.put(KEY_PHONE, contato.get_telefone());
        values.put(KEY_ADDRESS, contato.get_endereco());
        values.put(KEY_EMAIL, contato.get_email());
        values.put(KEY_IMAGEURI, contato.get_imagemURI().toString());

        long id = db.insert(TABLE_CONTACTS, null, values);
        contato.set_id(id);
        Log.d("Contato", "Contato" + contato);
        db.close();
        return id > 0;
    }

    public Contato getContact(long id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS,
        KEY_IMAGEURI}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            Contato contato = cursorToContato(cursor);
            db.close();
            cursor.close();
            return contato;
        }
        return null;
    }

    private Contato cursorToContato(Cursor cursor){
        Contato contato = new Contato(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), Uri.parse(cursor.getString(5)));
        contato.set_id(cursor.getLong(0));
        return contato;
    }

    public void deleteContact(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getContactCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public boolean updateContact(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contato.get_nome());
        values.put(KEY_PHONE, contato.get_telefone());
        values.put(KEY_ADDRESS, contato.get_endereco());
        values.put(KEY_EMAIL, contato.get_email());
        values.put(KEY_IMAGEURI, contato.get_imagemURI().toString());

        int rowAffected = db.update(TABLE_CONTACTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(contato.get_id())});
        db.close();

        return rowAffected > 0;
    }

    public List<Contato> getAllContacts(){
        List<Contato> contacts = new ArrayList<Contato>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);

        if(cursor.moveToFirst()){
            do{
                contacts.add((cursorToContato(cursor)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public Cursor getAllContactsCursor(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        return cursor;
    }

    public CursorAdapter getSimpleCursorAdapter(Context context){
        return new MyCursorAdapter(context, getAllContactsCursor());
    }

    public class MyCursorAdapter extends CursorAdapter{
        public MyCursorAdapter(Context context, Cursor cursor){
            super(context, cursor, true);
        }

        @Override

        public View newView(Context context, Cursor cursor, ViewGroup parent){
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.celula, parent, false);

            TextView name = (TextView) view.findViewById(R.id.txtNomeLista);

            Contato currentContact = cursorToContato(cursor);

            name.setText(currentContact.get_nome());
            TextView phone = (TextView) view.findViewById(R.id.txtTelefoneLista);
            phone.setText(currentContact.get_telefone());
            TextView email = (TextView) view.findViewById(R.id.txtEmailLista);
            phone.setText(currentContact.get_email());
            TextView address = (TextView) view.findViewById(R.id.txtEnderecoLista);
            phone.setText(currentContact.get_endereco());
            ImageView ivContactImage = (ImageView) view.findViewById(R.id.imageView);
            ivContactImage.setImageURI(currentContact.get_imagemURI());

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

        }
    }
}

