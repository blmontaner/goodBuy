package com.example.obligatorio.servicio;

import java.lang.reflect.Type;
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

import android.os.AsyncTask;
import android.util.Log;

import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.sistema.Sistema;
import com.example.obligatorio.ui.ActivityCrearLista;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;

public class WebServiceInteractionObtenerResultado extends AsyncTask<String , Void, List<ListaResultado>> {

	@Override
	protected List<ListaResultado> doInBackground(String... urls) {

		List<ListaResultado> listaResultados = new ArrayList<ListaResultado>();
		ListaResultado listaRes;
		List<ListaResultado> res=null;

		try {
			HttpClient httpClient = new DefaultHttpClient();
			URL url = new URL(urls[0]);
			HttpPost post = new HttpPost(Sistema.URL_PEDIDO_RESULTADO);
			post.setHeader("Content-type", "application/json");
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			
			ListaPedido lp = Sistema.getInstance().getListaPedActual();
			Direccion dir = new Direccion();
			///"latitud":,"longitud":
			dir.setLatLong(-34.9079606, -56.157705);
			lp.setDir(dir);
			StringEntity request = new StringEntity(new Gson().toJson(lp), HTTP.UTF_8);
			System.out.println("==================");
			System.out.println(new Gson().toJson(lp));
			System.out.println("==================");
			post.setEntity(request);
			
			HttpResponse resp = httpClient.execute(post);
			
			String respString = EntityUtils.toString(resp.getEntity());
			System.out.println("==================");
			System.out.println(respString);
			System.out.println("==================");
			Type type = new TypeToken<List<ListaResultado>>(){}.getType();
			 res = new Gson().fromJson(respString, type);
			
			
		} catch (Exception e) {
			Log.e("error", e.getMessage());
		}
		Sistema.getInstance().setListaResultados(res);
		return res;
	}
        
	 public static void work(){
	
			ListaResultado listaRes;
			List<ListaResultado> res=null;

			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(Sistema.URL_PEDIDO_RESULTADO);
				post.setHeader("Content-type", "application/json");
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				
				ListaPedido lp = Sistema.getInstance().getListaPedActual();
				Direccion dir = new Direccion();
				///"latitud":,"longitud":
				dir.setLatLong(-34.9079606, -56.157705);
				lp.setDir(dir);
				StringEntity request = new StringEntity(new Gson().toJson(lp), HTTP.UTF_8);
				System.out.println("==================");
				System.out.println(new Gson().toJson(lp));
				System.out.println("==================");
				post.setEntity(request);
				
				HttpResponse resp = httpClient.execute(post);
				
				String respString = EntityUtils.toString(resp.getEntity());
				System.out.println("==================");
				System.out.println(respString);
				System.out.println("==================");
				Type type = new TypeToken<List<ListaResultado>>(){}.getType();
				 res = new Gson().fromJson(respString, type);
				 Sistema.getInstance().setListaResultados(res);
				 System.out.println("==========>>>>Resutls Size "+Sistema.getInstance().getListaResultados().size());
				 System.out.println("==================END");
				
			} catch (Exception e) {
				Log.e("error", e.getMessage());
			}
			
		}
}
