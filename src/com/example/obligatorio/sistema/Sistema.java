package com.example.obligatorio.sistema;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.servicio.ListaPedido;
import com.example.obligatorio.servicio.ListaResultado;

public class Sistema {

	private static Sistema instance;
	private ListaResultado listaResActual;
	private ListaPedido listaPedActual;
	private List<ListaResultado> listaResultados;
	private Direccion currentDir;
	public final static String URL_PEDIDO_RESULTADO = "https://kitchensink-nspace.rhcloud.com/rest/productos/pedidoLista";
	public final static String URL_ESTABLECIMIENTOS = "https://kitchensink-nspace.rhcloud.com/rest/productos/establecimientos";
	public static final String URL_PRODUCTOS = "https://kitchensink-nspace.rhcloud.com/rest/productos/catalogoProductos";
	private BaseDeDatos base;
	
	
	public BaseDeDatos getBaseDeDatos() {
		return base;
	}

	public void setBase(BaseDeDatos base) {
		this.base = base;
	}

	private Sistema(){
		listaPedActual = new ListaPedido();
		listaResActual = new ListaResultado();
		listaResultados = new ArrayList<ListaResultado>();
	}
	
	public static Sistema getInstance(){
		if(instance == null){
			instance = new Sistema();
		}		
		return instance;
	}
	
	public void setContextoBaseDeDatos(Context contexto){
		base = new BaseDeDatos(contexto);
	}

	public ListaResultado getListaResActual() {
		return listaResActual;
	}

	public void setListaResActual(ListaResultado listaResActual) {
		this.listaResActual = listaResActual;
	}

	public ListaPedido getListaPedActual() {
		return listaPedActual;
	}

	public void setListaPedActual(ListaPedido listaPedActual) {
		this.listaPedActual = listaPedActual;
	}

	public List<ListaResultado> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<ListaResultado> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public Direccion getCurrentDir() {
		Direccion res ;
		if (base.isDireccionActualSeted()) {
			res= base.getDireccionActual();
		}else{
			res = new Direccion();
			//ORT
			res.setLatLong(-34.903819, -56.190463);
			return res;
		}
		return res;
	}

	public void setCurrentDir(Direccion currentDir) {
		this.currentDir = currentDir;//se podria ir esto.
		//this.getListaPedActual().setDir(currentDir);
		base.addDireccion(currentDir);
	}
	
	public int[] getCurrentLocation(){
		int[] currentDir = new int[2];
		Direccion dir = getCurrentDir();
		currentDir[0] = Util.getIntDirFormDouble(dir.getLatitud());
		currentDir[1] = Util.getIntDirFormDouble(dir.getLongitud());
		return currentDir;
	}
	
}
