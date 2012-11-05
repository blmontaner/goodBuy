package com.example.obligatorio.base_de_datos;

import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.dominio.Producto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "GoodBuy";

	// Product table name
	private static final String TABLE_PRODUCTS = "producto";

	// Product Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NOMBRE = "nombre";
	private static final String KEY_MARCA = "marca";
	private static final String KEY_ESPECIFICACION = "especificacion";

	public BaseDeDatos(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PRODUCTOS_TABLE = "CREATE TABLE if not exists "
				+ TABLE_PRODUCTS + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NOMBRE + " TEXT, " + KEY_MARCA + " TEXT,"
				+ KEY_ESPECIFICACION + " TEXT)";
		// TABLE_PRODUCTS ,KEY_ID,KEY_NOMBRE,KEY_MARCA,KEY_ESPECIFICACION);

		// "create table if not exists "
		// + " producto (id integer primary key"// autoincrement, "
		// +
		// " nombre text not null, marca text not null , especificacion text not null);"
		db.execSQL(CREATE_PRODUCTOS_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new PRODUCTO
	public void addProducto(Producto producto) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("id", producto.getId());
		values.put("nombre", producto.GetNombre());
		values.put("marca", producto.GetMarca());
		values.put("especificacion", producto.GetEspecificacion());

		// Inserting Row
		db.insert(TABLE_PRODUCTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	// Contact getContact(int id) {
	// SQLiteDatabase db = this.getReadableDatabase();
	//
	// Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
	// KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
	// new String[] { String.valueOf(id) }, null, null, null, null);
	// if (cursor != null)
	// cursor.moveToFirst();
	//
	// Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
	// cursor.getString(1), cursor.getString(2));
	// // return contact
	// return contact;
	// }

	// Getting All Products
	public List<Producto> getAllProducts() {
		List<Producto> productos = new ArrayList<Producto>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Producto pro = new Producto();
				pro.setId(cursor.getInt(0)); // Integer.parseInt(cursor.getString(0)));
				pro.SetNombre(cursor.getString(1));
				pro.SetMarca(cursor.getString(2));
				pro.SetEspecificacion(cursor.getString(3));
				// Adding Product to list
				productos.add(pro);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return Product list
		return productos;
	}

	// Updating single Product
	public int updateContact(Producto producto) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("id", producto.getId());
		values.put("nombre", producto.GetNombre());
		values.put("marca", producto.GetMarca());
		values.put("especificacion", producto.GetEspecificacion());

		// updating row
		return db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(producto.getId()) });
	}

	// Deleting single Product
	public void deleteProduct(Producto producto) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCTS, KEY_ID + " = ?",
				new String[] { String.valueOf(producto.getId()) });
		db.close();
	}

	// Getting Product Count
	public int getProductCount() {
//		String countQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
		String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count =0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			 while (cursor.moveToNext()){
				 count++;
			 };
		}
		cursor.close();
		db.close();
		return count;
	}
	public void deleteAllProducts(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from " + TABLE_PRODUCTS);
	}

}
