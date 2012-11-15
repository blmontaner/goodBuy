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
	private TextView precio;
	private ListaResultado listRes;
	
	private LinearLayout layoutTAG;

	public BalloonOverlayView(Context context, int balloonBottomOffset) {
		super(context);
		setPadding(10, 0, 10, balloonBottomOffset);
		layout = new LinearLayout(context);
		layout.setVisibility(VISIBLE);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay, layout);
		nombreEst = (TextView) v.findViewById(R.id.balloon_item_title);
		precio = (TextView) v.findViewById(R.id.balloon_item_snippet);

		layoutTAG = (LinearLayout) v.findViewById(R.id.balloon_inner_layout);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.NO_GRAVITY;

		addView(layout, params);

	}

	public void setData(OverlayItem item) {

		layout.setVisibility(VISIBLE);
		nombreEst.setVisibility(VISIBLE);
		nombreEst.setText(item.getTitle());
		precio.setVisibility(VISIBLE);
		precio.setText(item.getSnippet());

	}
	public void setData(ListaResultado item) {

		layout.setVisibility(VISIBLE);
		nombreEst.setVisibility(VISIBLE);
		nombreEst.setText(item.getEst().getNombre());
		//layout.setTag(item);//////
		layoutTAG.setTag(item);
		precio.setVisibility(VISIBLE);
		precio.setText("$"+item.getTotal());
		listRes = item;

	}
	

}
