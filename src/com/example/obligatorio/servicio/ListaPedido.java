package com.example.obligatorio.servicio;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	public ListaPedido(){
		productos = new ArrayList<ProductoCantidad>();
	}
	
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
	public List<Producto> getProds(){
		List<Producto> ret = new ArrayList<Producto>();
		
		for(ProductoCantidad p : getProductos()){
			ret.add(p.getProducto());
		}
		
		return ret;
		
	}
	public int eliminarProducto(Producto pro){
		ProductoCantidad aEliminar = new ProductoCantidad(pro,1);
		
		//System.out.println(this.productos.indexOf(aEliminar)+ " wwwwwwwwwwwwwwwwwwwwwwwww");
		int retorno = this.productos.indexOf(aEliminar);
		this.productos.remove(aEliminar);
		return retorno;
	}
	

	public static class ProductoCantidad implements Serializable{
		private Producto producto;
		private int cantidad;
		public ProductoCantidad(){}
		
		public ProductoCantidad(Producto producto,int cantidad){
			this.producto = producto;
			this.cantidad = cantidad;
		}
		
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
		@Override public boolean equals(Object other) {
			ProductoCantidad aux = ((ProductoCantidad)other);
			return this.getProducto().getId() == aux.getProducto().getId();
		   }
	}

}
