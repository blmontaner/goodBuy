package com.example.obligatorio.servicio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.sistema.Sistema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
				establecimiento.setId(Integer.parseInt(jsonObject
						.getString("id")));
				establecimiento.setNombre(jsonObject.getString("nombre"));

				JSONObject direccionesJSON = jsonObject
						.getJSONObject("direccion");

				if (!direccionesJSON.getString("longitud").equals("null")
						&& !direccionesJSON.getString("latitud").equals("null")) {
					double lon = direccionesJSON.getDouble("longitud");
					double lat = direccionesJSON.getDouble("latitud");
					Direccion dir = new Direccion();
					dir.setLatLong(lat, lon);
					dir.setDepartamento(direccionesJSON
							.getString("departamento"));
					dir.setCiudad(direccionesJSON.getString("ciudad"));

					String calle = direccionesJSON.getString("calle").replace(
							'+', ' ');
					String[] aux = calle.split(",");
					calle = aux[0];
					dir.setCalle(calle);

					establecimiento.setDireccion(dir);
					establecimientos.add(establecimiento);
				}
			}

		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}

		return establecimientos;

	}

	public static ArrayList<Producto> ObtenerProductos() {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Producto pro;

		try {
			URL url = new URL(Sistema.URL_PRODUCTOS);
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
				// Arroz blanco - Aruba tipo Patna (Bolsa 1 kg - Calidad Media)
				// Café envasado (no instantáneo) - Águila (Paquete 250 grs)
				// String[] aux = informacion.split(" \\(");
				String[] aux = informacion.split(" - ");
				pro.SetNombre(aux[0]);
				String marcaYEspecificacion = "";
				for (int j = 1; j < aux.length; j++) {
					marcaYEspecificacion += aux[j];
				}
				String[] subAux = marcaYEspecificacion.split(" \\(");
				pro.SetMarca(subAux[0]);
				pro.SetEspecificacion(subAux[1].substring(0,
						subAux[1].length() - 1));
				productos.add(pro);
				//System.out.println(pro.toString());
			}

		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}
		return productos;
	}
	
	public static void buscarResultadosListaActual() {

		List<ListaResultado> res = null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(Sistema.URL_PEDIDO_RESULTADO);
			post.setHeader("Content-type", "application/json");

			ListaPedido lp = Sistema.getInstance().getListaPedActual();
			
			lp.setDir(Sistema.getInstance().getCurrentDir());
			StringEntity request = new StringEntity(new Gson().toJson(lp),
					HTTP.UTF_8);
			System.out.println("==================");
			System.out.println(new Gson().toJson(lp));
			System.out.println("==================");
			post.setEntity(request);

			HttpResponse resp = httpClient.execute(post);

			String respString = EntityUtils.toString(resp.getEntity());
			System.out.println("==================");
			System.out.println(respString);
			System.out.println("==================");
			Type type = new TypeToken<List<ListaResultado>>() {
			}.getType();
			res = new Gson().fromJson(respString, type);
			Sistema.getInstance().setListaResultados(res);
			System.out.println("==========>>>>Resutls Size "
					+ Sistema.getInstance().getListaResultados().size());
			System.out.println("==================END");

		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}

	}

}
