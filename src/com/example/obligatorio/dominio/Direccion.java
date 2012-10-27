package com.example.obligatorio.dominio;

import java.io.Serializable;

public class Direccion implements Serializable{
	
	private long id;
	public static String PAIS = "uruguay";
	private String departamento;
	private String ciudad;
	private String calle;	
	private Double latitud;
	private Double longitud;
	
	public Direccion(){
		
	}
	
	public Direccion(String depto, String ciudad, String calle){
		this.departamento = depto;
		this.ciudad = ciudad;
		this.calle = calle;		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	
	public void setLatLong(Double lat, Double lon){
		this.longitud = lon;
		this.latitud = lat;
	}
	public String getDireccionDeBusqueda(){
		return (this.calle.replace("N�","").replace("Bvar.", "Boulevard").replace("Avda.", "Avenida").replace("Gral.", "General").replace("Dr.", "Doctor").replace("Ma.", "Maria").replace("A.", "Alverto").replace("Mcal.", "Mariscal")+","+this.ciudad+","+this.departamento+","+PAIS).replace(' ', '+').replace("+de+", "+");
	}
	
	public void setDireccionDeBusqueda(String val){
		this.calle = val;
	}
	
	public String toString() {		
		return " dirEst: "+this.calle +" ciuEst: "+ this.ciudad+ " depEst: "+this.departamento+" lat: "+ this.latitud+" long: "+this.longitud;
	}
	
	public boolean equals(Object obj) {	
		return this.getDireccionDeBusqueda().equals(((Direccion) obj).getDireccionDeBusqueda());
	}
}
