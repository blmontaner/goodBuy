package com.example.obligatorio.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;
import com.example.obligatorio.sistema.Util;
import com.example.obligatorio.ui.ActivityMap;
import com.example.obligatorio.ui.Principal;
import com.example.obligatorio.ui.R;
import com.google.android.maps.OverlayItem;

public class BalloonOverlayView extends FrameLayout {

	private LinearLayout layout;
	private TextView nombreEst;
	private TextView direccion;
	private ListaResultado listRes;

	public BalloonOverlayView(Context context, int balloonBottomOffset) {
		super(context);
		setPadding(10, 0, 10, balloonBottomOffset);
		layout = new LinearLayout(context);
		layout.setVisibility(VISIBLE);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay, layout);
		nombreEst = (TextView) v.findViewById(R.id.balloon_item_title);
		direccion = (TextView) v.findViewById(R.id.balloon_item_snippet);

		

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.NO_GRAVITY;

		addView(layout, params);
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Intent abrir = new Intent(getContext(), Principal.class);
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				final ListaResultado lres =(ListaResultado)((LinearLayout )v).getTag();
				String[] mensaje= new String[lres.getProductosPrecios().size()+1]; 
				int i = 0;
				for(ListaResultado.ProductoCantidadPrecio pcp : lres.getProductosPrecios()){
					mensaje[i]= pcp.getProdCantidad().getCantidad()+" "+pcp.getProdCantidad().getProducto().GetNombre()+" $"+pcp.getPrecioProducto();
					i++;
				}
				mensaje[i]="Total: "+lres.getTotal();
				
				builder.setItems(mensaje,null)
				       .setTitle("Lista Pedido");
				builder.setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            	Sistema.getInstance().setListaResActual(lres);
		            	getContext().startActivity(abrir);

		            }
		        });
				
				AlertDialog dialog = builder.create();
				dialog.show(); 
			}
			
			});
		// TODO Auto-generated constructor stub
	}

	public void setData(OverlayItem item) {

		layout.setVisibility(VISIBLE);
		nombreEst.setVisibility(VISIBLE);
		nombreEst.setText(item.getTitle());
		direccion.setVisibility(VISIBLE);
		direccion.setText(item.getSnippet());

	}
	public void setData(ListaResultado item) {

		layout.setVisibility(VISIBLE);
		nombreEst.setVisibility(VISIBLE);
		nombreEst.setText(item.getEst().getNombre());
		layout.setTag(item);
		direccion.setVisibility(VISIBLE);
		direccion.setText(item.getEst().getDireccion().getCalle());
		listRes = item;

	}
	

}
