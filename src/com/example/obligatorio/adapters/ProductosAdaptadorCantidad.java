package com.example.obligatorio.adapters;

import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.sistema.Util;
import com.example.obligatorio.ui.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductosAdaptadorCantidad extends ArrayAdapter<ProductoCantidad> {

	private Activity context;
	private List<ProductoCantidad> productos;
	private int layoutInt;
	
//	public ProductosAdaptadorCantidad(Activity context,
//			List<ProductoCantidad> pros) {
//		super(context, R.layout.activity_actual_items, pros);
//		this.context = context;
//		this.productos = pros;
//		this.layoutInt = R.layout.activity_actual_items;
//	}

	public ProductosAdaptadorCantidad(Activity context,
			List<ProductoCantidad> pros, int activityActualItems) {
		super(context, activityActualItems, pros);
		this.context = context;
		this.productos = pros;
		this.layoutInt = activityActualItems;
		// TODO Auto-generated constructor stub
	}

	static class ProductoCantidadViewHolder {
		TextView nombreTextView;
		TextView marcaTextView;
		TextView especificacionTextView;
		TextView cantidadTextView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		ProductoCantidadViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(layoutInt, null);

			holder = new ProductoCantidadViewHolder();
			holder.nombreTextView = (TextView) item.findViewById(R.id.tvNombre);
			// preg sobre que tengan los mismos nombres , lbl y tv
			holder.marcaTextView = (TextView) item.findViewById(R.id.tvMarca);
			holder.especificacionTextView = (TextView) item
					.findViewById(R.id.tvEspecificacion);
			holder.cantidadTextView = (TextView) item
					.findViewById(R.id.tvCantidad);

			item.setTag(holder);
		} else {
			holder = (ProductoCantidadViewHolder) item.getTag();

		}
		holder.nombreTextView.setText(productos.get(position).getProducto()
				.GetNombre());
		holder.marcaTextView.setText(productos.get(position).getProducto()
				.GetMarca());
		holder.especificacionTextView.setText(productos.get(position)
				.getProducto().GetEspecificacion());
		holder.cantidadTextView.setText(productos.get(position).getCantidad()
				+ "");
		holder.cantidadTextView.setTag(productos.get(position));// ////////////////////////////////////////////

		int colorPos = position % Util.getColoresItems().length;
		item.setBackgroundColor(Util.getColoresItems()[colorPos]);
		return item;
	}

}