package com.example.obligatorio.adapters;

import java.util.ArrayList;


import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.ui.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductosAdaptadorCantidad extends ArrayAdapter<Producto> {

	private Activity context;
	private ArrayList<Producto> productos;

	public ProductosAdaptadorCantidad(Activity context, ArrayList<Producto> pros) {
		super(context, R.layout.activity_actual_items, pros);
		this.context = context;
		this.productos = pros;
		//commentario prueba
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
			item = inflater.inflate(R.layout.activity_actual_items,
					null);

			holder = new ProductoCantidadViewHolder();
			holder.nombreTextView = (TextView) item
					.findViewById(R.id.tvNombre);//preg sobre que tengan los mismos nombres , lbl y tv
			holder.marcaTextView = (TextView) item.findViewById(R.id.tvMarca);
			holder.especificacionTextView = (TextView) item
					.findViewById(R.id.tvEspecificacion);
			holder.cantidadTextView = (TextView) item
					.findViewById(R.id.tvCantidad);

			item.setTag(holder);
		} else {
			holder = (ProductoCantidadViewHolder) item.getTag();

		}
		holder.nombreTextView.setText(productos.get(position).GetNombre());
		holder.marcaTextView.setText(productos.get(position).GetMarca());
		holder.especificacionTextView.setText(productos.get(position)
				.GetEspecificacion());
		holder.cantidadTextView.setText(productos.get(position).getCantidad() + "");
		return item;
	}

}