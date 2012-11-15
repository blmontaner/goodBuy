package com.example.obligatorio.sistema;

import com.example.obligatorio.servicio.ListaResultado;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;

public class Util {

	public static int getIntDirFormDouble(double d) {
		return (int) (d * 1E6);
	}

	public static int[] getColoresItems() {

		return new int[] { Color.parseColor("#E8E8E8"), Color.WHITE };
	}
	
	public static AlertDialog.Builder getDialogListaResultado(ListaResultado lres,Context contx){
		AlertDialog.Builder builder = new AlertDialog.Builder(contx);
		String[] mensaje= new String[lres.getProductosPrecios().size()+1]; 
		int i = 0;
		String promedio ="";
		for(ListaResultado.ProductoCantidadPrecio pcp : lres.getProductosPrecios()){
			promedio = pcp.isEsPromedio()?"*":"";
			mensaje[i]= pcp.getProdCantidad().getCantidad()+" "+pcp.getProdCantidad().getProducto().GetNombre()+promedio+" $"+pcp.getPrecioProducto();
			i++;
		}
		mensaje[i]="Total: "+lres.getTotal();
		
		builder.setItems(mensaje,null)
		       .setTitle(lres.getEst().getNombre());
		return builder;
	}
}
