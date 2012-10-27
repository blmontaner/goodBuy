package com.example.obligatorio.servicio;

import java.io.Serializable;
import java.util.List;

import com.example.obligatorio.dominio.Establecimiento;



public class ListaResultado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProductoCantidadPrecio> productosPrecios;	
	private double total;
	
	public List<ProductoCantidadPrecio> getProductosPrecios() {
		return productosPrecios;
	}

	public void setProductosPrecios(List<ProductoCantidadPrecio> productosPrecios) {
		this.productosPrecios = productosPrecios;
	}
	
	public double getTotal() {
		double ret = 0;
		for(ProductoCantidadPrecio p : getProductosPrecios()){
			ret+=p.getPrecioProducto();
		}
		return ret;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void addProductoCantidadPrecio(ListaPedido.ProductoCantidad pp,double precio,Establecimiento est){
		this.productosPrecios.add(new ListaResultado.ProductoCantidadPrecio(pp, precio,est));
	}

	public class ProductoCantidadPrecio implements Serializable{
		private ListaPedido.ProductoCantidad prodCantidad;
		private double precioProducto;
		private Establecimiento est;
		
		public ProductoCantidadPrecio(ListaPedido.ProductoCantidad pc, double precio, Establecimiento est){
			this.prodCantidad = pc;
			this.precioProducto = precio;
			this.est = est;
		}
		
		public ListaPedido.ProductoCantidad getProdCantidad() {
			return prodCantidad;
		}
		public void setProdCantidad(ListaPedido.ProductoCantidad prodCantidad) {
			this.prodCantidad = prodCantidad;
		}
		public double getPrecioProducto() {
			return precioProducto;
		}
		public void setPrecioProducto(double precioProducto) {
			this.precioProducto = precioProducto;
		}
		public Establecimiento getEst() {
			return est;
		}

		public void setEst(Establecimiento est) {
			this.est = est;
		}

		
	}


}
