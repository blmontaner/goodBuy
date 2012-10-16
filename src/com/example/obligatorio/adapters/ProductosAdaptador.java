package com.example.obligatorio.adapters;

import java.util.ArrayList;


import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.ui.R;

import android.app.Activity;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import android.widget.TextView;

public class ProductosAdaptador extends ArrayAdapter<Producto> {

	private Activity context;
	private ArrayList<Producto> productos;
	private ArrayList<Producto> productosOriginales;

	public ProductosAdaptador(Activity context, ArrayList<Producto> pros) {
		super(context, R.layout.activity_productos_items);
		this.context = context;
		this.productos = pros;
		this.productosOriginales = new ArrayList<Producto>(pros);

	}

	static class ProductoViewHolder {
		// guarda los textview para que no los tenga que cargar devuelta, para
		// mejorar eficiencia
		// es un patron viewholder
		TextView nombreTextView;
		TextView marcaTextView;
		TextView especificacionTextView;
		CheckBox enLista;

	}

	// importante getCount y getItem!!!
	//http://justcallmebrian.com/?p=139
	@Override
	public int getCount() {
		return productos.size();
	}

	@Override
	public Producto getItem(int arg0) {
		return productos.get(arg0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;

		ProductoViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.activity_productos_items, null);

			holder = new ProductoViewHolder();
			holder.nombreTextView = (TextView) item
					.findViewById(R.id.LblNombre);
			holder.marcaTextView = (TextView) item.findViewById(R.id.LblMarca);
			holder.especificacionTextView = (TextView) item
					.findViewById(R.id.LblEspecificacion);
			holder.enLista = (CheckBox) item.findViewById(R.id.chkEstado);

			item.setTag(holder);
		} else {
			holder = (ProductoViewHolder) item.getTag();

		}

		holder.nombreTextView.setText(productos.get(position).GetNombre());
		holder.marcaTextView.setText(productos.get(position).GetMarca());
		holder.especificacionTextView.setText(productos.get(position)
				.GetEspecificacion());
		holder.enLista.setChecked(productos.get(position).isEnListaActual());
		return item;
		// http://www.sgoliver.net/blog/?p=1431
	}

	public int getIndiceLetra(String letra) {
		int index = 0;

		for (Producto pro : productosOriginales) {

			if (pro.GetNombre().startsWith(letra)) {
				return index;
			}

			index++;
		}
		return -1;

	}

	public void FiltrarProductos(CharSequence s) {
		productos.clear();
		for (Producto pro : productosOriginales) {
			if (pro.GetNombre().toLowerCase()
					.startsWith(s.toString().toLowerCase())
					|| pro.GetMarca().toLowerCase()
							.startsWith(s.toString().toLowerCase())) {
				productos.add(pro);
			}

		}
		notifyDataSetChanged();
	}

}
