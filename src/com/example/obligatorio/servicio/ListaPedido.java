package com.example.obligatorio.servicio;

import java.io.Serializable;
import java.util.List;

import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.dominio.Producto;


public class ListaPedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Direccion dir;
	private List<ProductoCantidad> productos;
	
	public Direccion getDir() {
		return dir;
	}
	public void setDir(Direccion dir) {
		this.dir = dir;
	}

	public List<ProductoCantidad> getProductos() {
		return productos;
	}
	public void setPedidos(List<ProductoCantidad> productos) {
		this.productos = productos;
	}

	public class ProductoCantidad implements Serializable{
		private Producto producto;
		private int cantidad;
		public ProductoCantidad(){}
		
		public Producto getProducto() {
			return producto;
		}
		public void setProducto(Producto prod) {
			this.producto = prod;
		}
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		
	}

}
