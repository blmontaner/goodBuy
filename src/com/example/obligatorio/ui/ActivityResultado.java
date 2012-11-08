package com.example.obligatorio.ui;

import java.util.List;

import com.example.obligatorio.adapters.ResultadoAdaptador;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

public class ActivityResultado extends Activity {

	private ProgressDialog dialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultado);
		System.out.println("RESUTLADOOOOOOOOO");
		// Find the list view component in your layout.

//		dialog = new ProgressDialog(this);
//		dialog.setMessage("Se estan bucando los datos...");
//		dialog.setTitle("Procesando");
//		dialog.setCancelable(false);
//		// dialog.show();
//
//		dialog.setProgress(0);
//		dialog.setMax(100);
//		dialog.show(); // Mostramos el diálogo antes de comenzar
//
//		for (int i = 0; i < 250; i++) {
//			// Simulamos cierto retraso
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//			}
//
//			int p = Math.round(i / 250f);
//			dialog.setProgress(p);
//		}
//		dialog.dismiss();

		// obtain data
		List<ListaResultado> data = Sistema.getInstance().getListaResultados();
		System.out.println("RESUTLADOOOOOOOOO");

		// use a list adapter to render your list item in your list view!!
		// The item is rendered using the list_item.xml layout.
		// Here you can have any layout, with image and text as in your pic.

		final ResultadoAdaptador adaptador = new ResultadoAdaptador(this, data);

		ListView list = (ListView) findViewById(R.id.listaResultados);

		list.setAdapter(adaptador);

		// list.setOnItemClickListener(new OnItemClickListener() {
		//
		// public void onItemClick(AdapterView<?> arg0, View rowView,
		// int index, long arg3) {
		// }
		//
		// });
	}

}
