package com.example.obligatorio.servicio;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.dominio.Establecimiento;


public class ListaResultado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProductoCantidadPrecio> productosPrecios;	
	private double total;
	private Establecimiento est;
	
	public ListaResultado(){
		productosPrecios = new ArrayList<ProductoCantidadPrecio>();
	}
	
	public List<ProductoCantidadPrecio> getProductosPrecios() {
		return productosPrecios;
	}

	public void setProductosPrecios(List<ProductoCantidadPrecio> productosPrecios) {
		this.productosPrecios = productosPrecios;
	}
	
	public Establecimiento getEst() {
		return est;
	}

	public void setEst(Establecimiento est) {
		this.est = est;
	}

	public String getTotal() {
		double ret = 0;
		DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
		for(ProductoCantidadPrecio p : getProductosPrecios()){
			ret+=p.getPrecioProducto();
		}
		return oneDigit.format(ret);
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public void addProductoCantidadPrecio(ProductoCantidadPrecio pcp){
		this.productosPrecios.add(pcp);
	}
	
	public void addProductoCantidadPrecio(ListaPedido.ProductoCantidad pp,double precio){
		this.productosPrecios.add(new ListaResultado.ProductoCantidadPrecio(pp, precio));
	}

	public static class ProductoCantidadPrecio implements Serializable{
		private ListaPedido.ProductoCantidad prodCantidad;
		private double precioProducto;
				
		public ProductoCantidadPrecio(ListaPedido.ProductoCantidad pc, double precio){
			this.prodCantidad = pc;
			this.precioProducto = precio;
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

		@Override
		public String toString() {
			return "Prod Cant"+prodCantidad.toString()+" precioProducto: "+precioProducto;
		}
	}
}
