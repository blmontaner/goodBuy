package com.example.obligatorio.ui;

import java.util.List;

import com.example.obligatorio.adapters.ResultadoAdaptador;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityResultado extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_resultado);
	    
	 // Find the list view component in your layout.
	    
	    // obtain data
	    List<ListaResultado> data = Sistema.getInstance().getListaResultados();
	    System.out.println("RESUTLADOOOOOOOOO");
	    for(ListaResultado lr : data){
	    	System.out.println(lr.toString());
	    }

	    // use a list adapter to render your list item in your list view!!
	    // The item is rendered using the list_item.xml layout.
	    // Here you can have any layout, with image and text as in your pic.
	   	    
	    
	    final ResultadoAdaptador adaptador = new ResultadoAdaptador(this,
	    		data);

	    ListView list = (ListView) findViewById(R.id.listaResultados);


	    list.setAdapter(adaptador);

//	    list.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View rowView,
//					int index, long arg3) {
//			}
//	
//	    });
	}

}
