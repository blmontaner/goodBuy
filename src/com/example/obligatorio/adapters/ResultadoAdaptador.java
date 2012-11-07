package com.example.obligatorio.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.ui.R;

public class ResultadoAdaptador extends ArrayAdapter<ListaResultado> {

	private Activity context;
	private List<ListaResultado> listaResultados;

	public ResultadoAdaptador(Activity context, List<ListaResultado> listRes) {
		super(context, R.layout.activity_resultado_item, listRes);
		this.context = context;
		this.listaResultados = listRes;
	}

	static class ResultadoViewHolder {
		TextView establecimientoTextView;
		TextView direccionTextView;
		TextView totalTextView;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		ResultadoViewHolder holder;
 
		if (convertView == null) {

			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.activity_resultado_item, null);

			holder = new ResultadoViewHolder();
			holder.establecimientoTextView = (TextView) item.findViewById(R.id.LblEstablecimientoRES);
			// preg sobre que tengan los mismos nombres , lbl y tv
			holder.direccionTextView = (TextView) item.findViewById(R.id.LblDireccionRES);
			holder.totalTextView = (TextView) item
					.findViewById(R.id.LblTotalRES);

			item.setTag(holder);
		} else {
			holder = (ResultadoViewHolder) item.getTag();

		}
		holder.establecimientoTextView.setText(listaResultados.get(position).getEst().getNombre());
		holder.direccionTextView.setText(listaResultados.get(position).getEst().getDireccion().getCalle());
		holder.totalTextView.setText("$ "+listaResultados.get(position).getTotal());

		return item;
	}


}
