package com.example.obligatorio.ui;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.obligatorio.adapters.ResultadoAdaptador;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;
import com.example.obligatorio.sistema.Util;

public class ActivityResultado extends Activity {

	private ProgressDialog dialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultado);
		final List<ListaResultado> data = Sistema.getInstance().getListaResultados();

		final ResultadoAdaptador adaptador = new ResultadoAdaptador(this, data);

		final ListView list = (ListView) findViewById(R.id.listaResultados);

		list.setAdapter(adaptador);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View rowView,
					int index, long arg3) {

				mostrarPedidoPrecios(data.get(index));
			}
		});

		
		
	}
	public void mostrarPedidoPrecios(final ListaResultado lres){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final Intent abrir = new Intent(this, ActivityMap.class);
		String[] mensaje= new String[lres.getProductosPrecios().size()+1]; 
		int i = 0;
		for(ListaResultado.ProductoCantidadPrecio pcp : lres.getProductosPrecios()){
			mensaje[i]= pcp.getProdCantidad().getCantidad()+" "+pcp.getProdCantidad().getProducto().GetNombre()+" $"+pcp.getPrecioProducto();
			i++;
		}
		mensaje[i]="Total: "+lres.getTotal();
		
		builder.setItems(mensaje,null)
		       .setTitle("Lista Pedido");
		builder.setPositiveButton("Ver en mapa", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
            	int[] latLon = new int[2];
            	latLon[0] = Util.getIntDirFormDouble(lres.getEst().getDireccion().getLatitud());
            	latLon[1] = Util.getIntDirFormDouble(lres.getEst().getDireccion().getLongitud());
            	
    			abrir.putExtra("latLong", latLon);
    			startActivity(abrir);

            }
        });
		
		AlertDialog dialog = builder.create();
		dialog.show(); 
	
	}

}
