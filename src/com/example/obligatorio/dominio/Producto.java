package com.example.obligatorio.dominio;

import com.example.obligatorio.servicio.ListaPedido;

public class Producto {
	private int id;
	private String nombre;
	private String marca;
	private String especificacion;
	private int cantidad;
	private boolean estaEnListaActual;

	public String GetNombre() {
		return nombre;
	}

	public String GetMarca() {
		return marca;
	}

	public String GetEspecificacion() {
		return especificacion;
	}

	public void SetNombre(String unNombre) {
		this.nombre = unNombre;
	}

	public void SetMarca(String unaMarca) {
		this.marca = unaMarca;
	}

	public void SetEspecificacion(String unaEspecificacion) {
		this.especificacion = unaEspecificacion;
	}

	public Producto(String unNombre, String unaMarca, String unaEspecificacion) {
		this.nombre = unNombre;
		this.marca = unaMarca;
		this.especificacion = unaEspecificacion;
		this.cantidad = 0;
	}
	public Producto(String unNombre, String unaMarca, String unaEspecificacion, int cantidad) {
		this.nombre = unNombre;
		this.marca = unaMarca;
		this.especificacion = unaEspecificacion;
		this.cantidad = cantidad;
	}
	public Producto(){
		
		
	}
	@Override
	public String toString(){
		return this.nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEnListaActual() {
		return estaEnListaActual;
	}

	public void setEnListaActual(boolean estaEnListaActual) {
		this.estaEnListaActual = estaEnListaActual;
	}
	
}
