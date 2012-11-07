package com.example.obligatorio.sistema;

import java.util.ArrayList;
import java.util.List;

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
		return currentDir;
	}

	public void setCurrentDir(Direccion currentDir) {
		this.currentDir = currentDir;
	}
	
}
