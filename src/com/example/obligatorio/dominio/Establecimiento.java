package com.example.obligatorio.dominio;

import java.io.Serializable;

public class Establecimiento implements Serializable{
	
	private long id;
	private String nombre;
	private Direccion direccion;
	
	public Establecimiento(){
		
	}
	
	public Establecimiento(String nomb, Direccion dir){
		this.nombre = nomb;
		this.direccion = dir;		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	@Override
	public String toString() {		
		return " NombEst: "+this.nombre+ this.direccion.toString();
	}	
	@Override
	public boolean equals(Object obj) {	
		return this.direccion.equals(((Establecimiento) obj).getDireccion());
	}
	
}
