package com.example.obligatorio.base_de_datos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.servicio.ListaResultado.ProductoCantidadPrecio;

public class BaseDeDatos extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 29;

	// Database Name
	private static final String DATABASE_NAME = "GoodBuy";

	// Product table name
	private static final String TABLE_PRODUCTS = "producto";

	// Product Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NOMBRE = "nombre";
	private static final String KEY_MARCA = "marca";
	private static final String KEY_ESPECIFICACION = "especificacion";

	// Tabla direccion
	private static final String TABLE_DIR = "direccion";

	// Columnas direccion
	private static final String KEY_LON = "longitud";
	private static final String KEY_LAT = "latitud";

	// Tabla establecimiento
	private static final String TABLE_ESTABLECIMIENTO = "establecimiento";

	// Columnas establecimiento
	private static final String KEY_DEPARTAMENTO = "departamento";
	private static final String KEY_CIUDAD = "ciudad";
	private static final String KEY_CALLE = "calle";

	// Tabla historial
	private static final String TABLE_HISTORIAL = "historial";

	// Columnas historial
	private static final String KEY_IDESTABLECIMIENTO = "idEst";

	// Tabla ProductoCantidad
	private static final String TABLE_PRODUCTOCANTIDAD = "ProductoCantidad";

	// Columnas ProductoCantidad
	private static final String KEY_CANTIDAD = "Cantidad";
	private static final String KEY_IDPRODUCTO = "idProducto";
	private static final String KEY_PRECIO = "Precio";
	private static final String KEY_PROMEDIO = "promedio";
	private static final String KEY_FECHA = "fechaYHora";

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

		db.execSQL(CREATE_PRODUCTOS_TABLE);

		String CREATE_DIRECCION_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_DIR + " (" + KEY_LON + " DOUBLE ," + KEY_LAT
				+ " DOUBLE)";

		db.execSQL(CREATE_DIRECCION_TABLE);

		String CREATE_ESTABLECIMIENTO_TABLE = "CREATE TABLE if not exists "
				+ TABLE_ESTABLECIMIENTO + " (" + KEY_ID + " LONG PRIMARY KEY,"
				+ KEY_NOMBRE + " TEXT, " + KEY_LON + " DOUBLE ," + KEY_LAT
				+ " DOUBLE ," + KEY_DEPARTAMENTO + " TEXT," + KEY_CIUDAD
				+ " TEXT," + KEY_CALLE + " TEXT)";

		db.execSQL(CREATE_ESTABLECIMIENTO_TABLE);

		String CREATE_PRODUCTOCANTIDAD_TABLE = "CREATE TABLE if not exists "
				+ TABLE_PRODUCTOCANTIDAD + " (" + KEY_IDPRODUCTO + " INTEGER ,"
				+ KEY_PRECIO + " DOUBLE ," + KEY_CANTIDAD + " INTEGER,"
				+ KEY_ID + " INTEGER , " + KEY_PROMEDIO + " INTEGER)";

		db.execSQL(CREATE_PRODUCTOCANTIDAD_TABLE);

		String CREATE_HISTORIAL_TABLE = "CREATE TABLE if not exists "
				+ TABLE_HISTORIAL + " (" + KEY_ID
				+ " INTEGER PRIMARY KEY autoincrement," + KEY_IDESTABLECIMIENTO
				+ " LONG , " + KEY_FECHA + " TEXT)";

		db.execSQL(CREATE_HISTORIAL_TABLE);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTABLECIMIENTO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOCANTIDAD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORIAL);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	// Adding new Direccion
	public void addDireccion(Direccion dir) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from " + TABLE_DIR); // borro la dir vieja.

		ContentValues values = new ContentValues();
		values.put(KEY_LON, dir.getLongitud());
		values.put(KEY_LAT, dir.getLatitud());

		// Inserting Row
		db.insert(TABLE_DIR, null, values);
		db.close(); // Closing database connection
	}

	// Getting Direccion Actual
	public Direccion getDireccionActual() {
		String selectQuery = "SELECT * FROM " + TABLE_DIR;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Direccion dir = new Direccion();
		if (cursor.moveToFirst()) {
			dir.setLongitud(cursor.getDouble(0));
			dir.setLatitud(cursor.getDouble(1));
		}
		cursor.close();
		db.close();
		return dir;
	}

	// hay Direccion Actual
	public Boolean isDireccionActualSeted() {
		String selectQuery = "SELECT * FROM " + TABLE_DIR;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = 0;
		if (cursor.moveToFirst()) {
			count++;
		}
		cursor.close();
		db.close();
		return (count != 0);
	}

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
		// String countQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
		// SQLiteDatabase db = this.getReadableDatabase();
		// Cursor cursor = db.rawQuery(countQuery, null);
		// cursor.close();
		//
		// // return count
		// return cursor.getCount();
		String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				count++;
			}
			;
		}
		cursor.close();
		db.close();
		return count;
	}

	public void deleteAllProducts() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from " + TABLE_PRODUCTS);
	}

	public int getEstablecimientoCount() {
		String selectQuery = "SELECT * FROM " + TABLE_ESTABLECIMIENTO;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = 0;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				count++;
			}
			;
		}
		cursor.close();
		db.close();
		return count;
	}

	public void addEstablecimiento(Establecimiento est) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, est.getId());
		values.put(KEY_NOMBRE, est.getNombre());
		values.put(KEY_LAT, est.getDireccion().getLatitud());
		values.put(KEY_LON, est.getDireccion().getLongitud());
		values.put(KEY_DEPARTAMENTO, est.getDireccion().getDepartamento());
		values.put(KEY_CIUDAD, est.getDireccion().getCiudad());
		values.put(KEY_CALLE, est.getDireccion().getCalle());

		// Inserting Row
		db.insert(TABLE_ESTABLECIMIENTO, null, values);
		db.close(); // Closing database connection
	}

	public List<Establecimiento> getAllEstablecimientos() {
		List<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_ESTABLECIMIENTO;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Establecimiento est = new Establecimiento();
				est.setId(cursor.getLong(0)); // Integer.parseInt(cursor.getString(0)));
				est.setNombre(cursor.getString(1));
				Direccion dir = new Direccion();
				dir.setLatLong(cursor.getDouble(3), cursor.getDouble(2));
				dir.setDepartamento(cursor.getString(4));
				dir.setCiudad(cursor.getString(5));
				dir.setCalle(cursor.getString(6));
				est.setDireccion(dir);
				establecimientos.add(est);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return establecimientos;
	}

	public void addHistorialListaResultado(ListaResultado historial) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_IDESTABLECIMIENTO, historial.getEst().getId());
		values.put(KEY_FECHA, historial.getFecha());
		db.insert(TABLE_HISTORIAL, null, values);

		String selectQuery = "SELECT " + KEY_ID + " FROM " + TABLE_HISTORIAL
				+ " order by " + KEY_ID + " asc";
		Cursor cursor = db.rawQuery(selectQuery, null);
		int idHistorial = 1;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				idHistorial = cursor.getInt(0);
			}
		}
		cursor.close();
		// System.out.println(idHistorial);
		int auxNum;
		for (ProductoCantidadPrecio proCant : historial.getProductosPrecios()) {
			values = new ContentValues();

			values.put(KEY_ID, idHistorial);
			values.put(KEY_IDPRODUCTO, proCant.getProdCantidad().getProducto()
					.getId());
			values.put(KEY_PRECIO, proCant.getPrecioProducto());
			values.put(KEY_CANTIDAD, proCant.getProdCantidad().getCantidad());
			auxNum = proCant.isEsPromedio() ? 1 : 0;
			values.put(KEY_PROMEDIO, auxNum);
			// Inserting Row
			db.insert(TABLE_PRODUCTOCANTIDAD, null, values);
		}

		db.close(); // Closing database connection
	}

	public List<ListaResultado> getAllListaResultado() {
		List<ListaResultado> historiales = new ArrayList<ListaResultado>();
		String selectQuery = "SELECT establecimiento.id , nombre, longitud,latitud, departamento,ciudad, calle, historial.id, fechaYHora "
				+ "FROM historial,establecimiento where historial.idEst == establecimiento.id";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ListaResultado historial = new ListaResultado();

				Establecimiento est = new Establecimiento();
				est.setId(cursor.getLong(0)); // Integer.parseInt(cursor.getString(0)));
				est.setNombre(cursor.getString(1));
				Direccion dir = new Direccion();
				dir.setLatLong(cursor.getDouble(3), cursor.getDouble(2));
				dir.setDepartamento(cursor.getString(4));
				dir.setCiudad(cursor.getString(5));
				dir.setCalle(cursor.getString(6));
				est.setDireccion(dir);
				historial.setEst(est);

				int idHistorial = cursor.getInt(7);
				historial.setFecha(cursor.getString(8));

				List<ProductoCantidadPrecio> productosCantidadPrecio = new ArrayList<ProductoCantidadPrecio>();

				selectQuery = "select * from " + TABLE_PRODUCTOCANTIDAD
						+ " where " + KEY_ID + "==" + idHistorial;
				Cursor cursorProductoCantidad = db.rawQuery(selectQuery, null);

				if (cursorProductoCantidad.moveToFirst()) {
					do {
						ProductoCantidad proCantidad = new ProductoCantidad();
						ProductoCantidadPrecio productosPrecios = new ProductoCantidadPrecio(
								proCantidad,
								cursorProductoCantidad.getDouble(1));
						proCantidad.setCantidad(cursorProductoCantidad
								.getInt(2));
						productosPrecios.setEsPromedio(cursorProductoCantidad
								.getInt(4) == 1);
						
						selectQuery = "select * from " + TABLE_PRODUCTS
								+ " where " + KEY_ID + "=="
								+ cursorProductoCantidad.getInt(0);
						Cursor cursorProducto = db.rawQuery(selectQuery, null);

						Producto pro = null;
						if (cursorProducto.moveToFirst()) {
							do {
								pro = new Producto();
								pro.setId(cursorProducto.getInt(0)); // Integer.parseInt(cursor.getString(0)));
								pro.SetNombre(cursorProducto.getString(1));
								pro.SetMarca(cursorProducto.getString(2));
								pro.SetEspecificacion(cursorProducto
										.getString(3));

							} while (cursorProducto.moveToNext());
						}
						cursorProducto.close();

						proCantidad.setProducto(pro);

						productosCantidadPrecio.add(productosPrecios);

					} while (cursorProductoCantidad.moveToNext());
				}
				cursorProductoCantidad.close();

				historial.setProductosPrecios(productosCantidadPrecio);

				historiales.add(historial);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return historiales;
	}
}
