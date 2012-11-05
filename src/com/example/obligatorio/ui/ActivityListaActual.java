package com.example.obligatorio.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.sistema.Sistema;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.R.integer;
import android.app.Activity;
import android.graphics.Color;

public class ActivityListaActual extends Activity {

	// private ArrayList<Producto> productosSeleccionados;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);

		List<Producto> productos = Sistema.getInstance().getListaPedActual()
				.getProds();
		// productos.add(new Producto("A", "Marca", "Especificacion", 2));
		// productos.add(new Producto("B", "Marca", "Especificacion", 1));
		// productos.add(new Producto("C", "Marca", "Especificacion", 5));
		// productos.add(new Producto("D", "Marca", "Especificacion", 3));

		ProductosAdaptadorCantidad adaptador = new ProductosAdaptadorCantidad(
				this, productos);

		ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

		lstOpciones.setAdapter(adaptador);
		
		//////lstOpcioneslstOpcioneslstOpcioneslstOpcioneslstOpcioneslstOpciones
	}

	public void Suma(View v) {
		LinearLayout vwParentRow = (LinearLayout)v.getParent();
		TextView valor = (TextView)vwParentRow.getChildAt(2);
		int suma = Integer.parseInt(valor.getText().toString()) + 1;
		valor.setText(suma + "");
		//http://androidforbeginners.blogspot.com/2010/03/clicking-buttons-in-listview-row.html
		
		
        
        //TextView child = (TextView)vwParentRow.getChildAt(2);
       // int c = Color.BLUE;
        
       // vwParentRow.setBackgroundColor(c); 
       // vwParentRow.refreshDrawableState(); 
	}

	public void Resta(View v) {
		LinearLayout vwParentRow = (LinearLayout)v.getParent();
		TextView valor = (TextView)vwParentRow.getChildAt(2);
		int resta = Integer.parseInt(valor.getText().toString());
		if (resta > 0) {
			resta--;
		}
		valor.setText(resta + "");
	}

}