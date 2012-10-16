package com.example.obligatorio.ui;

import java.util.ArrayList;


import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;

public class ActivityListaActual extends Activity {

	//private ArrayList<Producto> productosSeleccionados;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);

		ArrayList<Producto> productos = new ArrayList<Producto>();
		productos.add(new Producto("A", "Marca", "Especificacion", 2));
		productos.add(new Producto("B", "Marca", "Especificacion", 1));
		productos.add(new Producto("C", "Marca", "Especificacion", 5));
		productos.add(new Producto("D", "Marca", "Especificacion", 3));

		ProductosAdaptadorCantidad adaptador = new ProductosAdaptadorCantidad(
				this, productos);

		ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

		lstOpciones.setAdapter(adaptador);
	}

}