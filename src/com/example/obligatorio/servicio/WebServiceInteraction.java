package com.example.obligatorio.servicio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.sistema.Sistema;

public class WebServiceInteraction {

	public static ArrayList<Establecimiento> ObtenerEstablecimientos() {
		ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
		Establecimiento establecimiento;

		try {
			URL url = new URL(Sistema.URL_ESTABLECIMIENTOS);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000);
			con.setConnectTimeout(15000);
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String valor = reader.readLine();

			JSONArray jsonArray = new JSONArray(valor);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				establecimiento = new Establecimiento();
				establecimiento.setId(Integer.parseInt(jsonObject.getString("id")));
				establecimiento.setNombre(jsonObject.getString("nombre"));
				
				JSONObject direccionesJSON = jsonObject.getJSONObject("direccion");
				
				//String auxx = direccionesJSON.getString("latitud");
				if(!direccionesJSON.getString("longitud").equals("null") && !direccionesJSON.getString("latitud").equals("null")){
					double lon = direccionesJSON.getDouble("longitud");
					double lat = direccionesJSON.getDouble("latitud");
					Direccion dir = new Direccion();
					dir.setLatLong(lat, lon);
					establecimiento.setDireccion(dir);
					establecimientos.add(establecimiento);
				}
//				// "nombre":"Aceite de soja - Salad (Envase 900 cc)"
//				String[] aux = informacion.split(" - ");
//				if (aux.length == 2) { // esto es por "Producto Prueba" viene
//										// con formato no deseado
//					pro.SetNombre(aux[0]);
//					String[] subAux = aux[1].split(" \\(");
//					pro.SetMarca(subAux[0]);
//					pro.SetEspecificacion(subAux[1].substring(0,subAux[1].length() - 1));// saco el ultimo
//														// parentesis
//					productos.add(pro);
//				}

			}

		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}

		return establecimientos;

	}
	
}
