package com.example.obligatorio.servicio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.obligatorio.dominio.Producto;

import android.os.AsyncTask;
import android.util.Log;

//http://miguelangellv.wordpress.com/2011/01/31/creando-tareas-asincronas-en-android-con-asynctask/
//Guia REST Android.pdf
public class BajarProJSON extends AsyncTask<String, Void, ArrayList<Producto>> {
	// para un futuro se podria poner el PROGRESSDIALOG
	// private final ProgressDialog dialog = new ProgressDialog(
	// AllOffersListActivity.this);

	@Override
	protected void onPreExecute() {
		// this.dialog.setMessage("Fetching offers...");
		// this.dialog.show();
	}

	@Override
	protected ArrayList<Producto> doInBackground(String... urls) {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Producto pro;

		try {
			URL url = new URL(urls[0]);
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
				pro = new Producto();
				pro.setId(Integer.parseInt(jsonObject.getString("id")));
				String informacion = jsonObject.getString("nombre");

				// "nombre":"Aceite de soja - Salad (Envase 900 cc)"
				String[] aux = informacion.split(" - ");
				if (aux.length == 2) { // esto es por "Producto Prueba" viene
										// con formato no deseado
					pro.SetNombre(aux[0]);
					String[] subAux = aux[1].split(" \\(");
					pro.SetMarca(subAux[0]);
					pro.SetEspecificacion(subAux[1].substring(0,
							subAux[1].length() - 1));// saco el ultimo
														// parentesis
					productos.add(pro);
				}

			}

		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}

		return productos;

	}

	@Override
	protected void onPostExecute(ArrayList<Producto> offerList) {

	}
}