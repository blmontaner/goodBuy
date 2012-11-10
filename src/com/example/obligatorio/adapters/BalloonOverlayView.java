package com.example.obligatorio.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.ui.R;
import com.google.android.maps.OverlayItem;

public class BalloonOverlayView extends FrameLayout {

	private LinearLayout layout;
	private TextView nombreEst;
	private TextView direccion;

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
		direccion.setVisibility(VISIBLE);
		direccion.setText(item.getEst().getDireccion().getCalle());

	}

}
