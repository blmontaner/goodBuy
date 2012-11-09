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
import android.widget.SectionIndexer;

import android.widget.TextView;

public class ProductosAdaptador extends ArrayAdapter<Producto> implements
		SectionIndexer {

	private Activity context;
	private ArrayList<Producto> productos;
	private ArrayList<Producto> productosOriginales;
	private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
	// http://justcallmebrian.com/?p=139
	@Override
	public int getCount() {
		return productos.size();
	}

	@Override
	public Producto getItem(int arg0) {
		return productos.get(arg0);
	}

	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

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
		// http://www.sgoliver.net/blog/?p=1431

		// http://eureka.ykyuen.info/2010/03/15/android-%E2%80%93-applying-alternate-row-color-in-listview-with-simpleadapter/
		int colorPos = position % colors.length;
		item.setBackgroundColor(colors[colorPos]);
		return item;

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

	// SECTIONINDEXER
	public int getPositionForSection(int section) {
		// If there is no item for current section, previous section will be
		// selected
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// For numeric section
					for (int k = 0; k <= 9; k++) {
						if (match(String.valueOf(getItem(j).GetNombre().charAt(
								0)), String.valueOf(k)))
							return j;
					}
				} else {
					if (match(String.valueOf(getItem(j).GetNombre().charAt(0)),
							String.valueOf(mSections.charAt(i))))
						return j;
				}
			}
		}
		return 0;
	}

	public int getSectionForPosition(int position) {
		return 0;
	}

	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}

	private boolean match(String value, String keyword) {
		if (value == null || keyword == null)
			return false;
		if (keyword.length() > value.length())
			return false;

		int i = 0, j = 0;
		do {
			if (keyword.charAt(j) == value.charAt(i)) {
				i++;
				j++;
			} else if (j > 0)
				break;
			else
				i++;
		} while (i < value.length() && j < keyword.length());

		return (j == keyword.length()) ? true : false;
	}

}
