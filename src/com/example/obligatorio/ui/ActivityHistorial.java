package com.example.obligatorio.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;
import com.example.obligatorio.sistema.Util;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class ActivityHistorial extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial);

		ArrayList<String> historial = new ArrayList<String>();
		//historial.add("03/05/2012-ESTAblecimiento $415");
		//historial.add("23/08/2012-$100");
		//historial.add("06/10/2012-$386");
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	//	Calendar cal = Calendar.getInstance();
		
		 final List<ListaResultado> histo = Sistema.getInstance().getHistorial();
		for(ListaResultado lres : histo){
			historial.add(lres.getFecha()+ "   $" + lres.getTotal());
		}
		
		ListView lstHistorial = (ListView) findViewById(R.id.listHistorial);

		lstHistorial.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, historial));
		//simple_list_item_multiple_choice
		
		
		lstHistorial.setOnItemClickListener(new OnItemClickListener() {
//					Intent abrir = new Intent(ActivityHistorial.this, ActivityListaActual.class);//getApplicationContext
//						startActivity(abrir);
			public void onItemClick(AdapterView<?> arg0, View rowView,
					int index, long arg3) {

				mostrarPedidoPrecios(histo.get(index));
			}
		});
		

	}
	public void mostrarPedidoPrecios(final ListaResultado lres){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//final Intent abrir = new Intent(this, ActivityMap.class);
		String[] mensaje= new String[lres.getProductosPrecios().size()+1]; 
		int i = 0;
		for(ListaResultado.ProductoCantidadPrecio pcp : lres.getProductosPrecios()){
			mensaje[i]= pcp.getProdCantidad().getCantidad()+" "+pcp.getProdCantidad().getProducto().GetNombre()+" $"+pcp.getPrecioProducto();
			i++;
		}
		mensaje[i]="Total: "+lres.getTotal();
		
		builder.setItems(mensaje,null)
		       .setTitle(lres.getEst().getNombre());
//		builder.setPositiveButton("Ver en mapa", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//            	
//            	int[] latLon = new int[2];
//            	latLon[0] = Util.getIntDirFormDouble(lres.getEst().getDireccion().getLatitud());
//            	latLon[1] = Util.getIntDirFormDouble(lres.getEst().getDireccion().getLongitud());
//            	
//    			abrir.putExtra("latLong", latLon);
//    			startActivity(abrir);
//
//            }
//        });
		
		AlertDialog dialog = builder.create();
		dialog.show(); 
	
	}
	

}
