package com.example.obligatorio.maps;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class DireccionItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();

	public DireccionItemizedOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return puntos.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return puntos.size();
	}

	public void agregarPuntos(OverlayItem punto) {
		puntos.add(punto);
		populate();
	}
}
