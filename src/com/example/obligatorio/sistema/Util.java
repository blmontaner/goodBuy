package com.example.obligatorio.sistema;

import android.graphics.Color;

public class Util {

	public static int getIntDirFormDouble(double d) {
		return (int) (d * 1E6);
	}

	public static int[] getColoresItems() {

		return new int[] { Color.parseColor("#D3DFEE"), Color.WHITE };
	}
}
