package com.example.obligatorio.ui;

import java.util.List;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.sistema.Sistema;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.R.integer;
import android.app.Activity;

public class ActivityListaActual extends Activity {

	// private ArrayList<Producto> productosSeleccionados;
	private ProductosAdaptadorCantidad adaptador;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);

		adaptador = new ProductosAdaptadorCantidad(this, Sistema.getInstance()
				.getListaPedActual().getProductos());

		ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

		lstOpciones.setAdapter(adaptador);

	}

	public void Suma(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(1);
		int suma = Integer.parseInt(valor.getText().toString()) + 1;
		valor.setText(suma + "");

		ProductoCantidad pro = (ProductoCantidad) valor.getTag();
		pro.setCantidad(suma);

		// http://androidforbeginners.blogspot.com/2010/03/clicking-buttons-in-listview-row.html

	}

	public void Resta(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(1);
		int resta = Integer.parseInt(valor.getText().toString());
		if (resta > 0) {
			resta--;
		}
		valor.setText(resta + "");

		ProductoCantidad pro = (ProductoCantidad) valor.getTag();

		if (resta == 0) {
			
			pro.getProducto().setEnListaActual(false);
			int pos = Sistema.getInstance().getListaPedActual()
					.eliminarProducto(pro.getProducto());
			System.out.println(pos + "   posssss");
			Integer borreEL = Sistema.getInstance().getItemsChecked().remove(pos);
			System.out.println(borreEL + "   borreEL");
			
			Toast.makeText(this, "Quitado", Toast.LENGTH_SHORT).show();
			adaptador.notifyDataSetChanged();
			
		} else {
			pro.setCantidad(resta);
		}
	}

}