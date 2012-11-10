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
		return base.getDireccionActual();
	}

	public void setCurrentDir(Direccion currentDir) {
		this.currentDir = currentDir;//se podria ir esto.
		//this.getListaPedActual().setDir(currentDir);
		base.addDireccion(currentDir);
	}
	
	public int[] getCurrentLocation(){
		int[] currentDir = new int[2];
		if (base.isDireccionActualSeted()) {
			Direccion dir = base.getDireccionActual();
			currentDir[0] = (int) (dir.getLatitud() * 1e6);
			currentDir[1] = (int) (dir.getLongitud() * 1e6);
		} else {
			currentDir[0] = -34903819;// ORT
			currentDir[1] = -56190463;
		}
		return currentDir;
	}
	
}
