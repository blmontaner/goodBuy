package com.example.obligatorio.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.servicio.ListaResultado.ProductoCantidadPrecio;
import com.example.obligatorio.sistema.Sistema;

public class ActivityListaActualHistorial extends Activity {
	// private ArrayList<Producto> productosSeleccionados;
	private ProductosAdaptadorCantidad adaptador;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);

		int cantidadElementos = Sistema.getInstance().getHistorial().size();
		if (cantidadElementos != 0) {
			List<ProductoCantidadPrecio> productosCant = Sistema.getInstance()
					.getHistorial().get(cantidadElementos - 1)
					.getProductosPrecios();
			List<ProductoCantidad> productos = new ArrayList<ProductoCantidad>();
			for (ProductoCantidadPrecio pros : productosCant) {
				productos.add(pros.getProdCantidad());
			}

			adaptador = new ProductosAdaptadorCantidad(this, productos,
					R.layout.activity_actual_historial_items);

			ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

			lstOpciones.setAdapter(adaptador);
		}
	}
}
