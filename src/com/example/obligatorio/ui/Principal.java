package com.example.obligatorio.ui;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity implements OnClickListener {

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_principal);
//
//		View btnDir = findViewById(R.id.btnDir);
//		btnDir.setOnClickListener(this);
//
//		View btnCrearLista = findViewById(R.id.btnCrearLista);
//		btnCrearLista.setOnClickListener(this);
//
//		View btnListaActual = findViewById(R.id.btnListaActual);
//		btnListaActual.setOnClickListener(this);
//
//		View btnElegirEstablecimiento = findViewById(R.id.btnElegir_Establecimiento);
//		btnElegirEstablecimiento.setOnClickListener(this);
//
//		View btnHistorial = findViewById(R.id.btnHistorial);
//		btnHistorial.setOnClickListener(this);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_principal, menu);
//		return true;
//	}
//
//	public void onClick(View v) {
//		Intent abrir;
//		if (v.getId() == findViewById(R.id.btnDir).getId()) {
//			abrir = new Intent(this, ActivityDireccionActual.class);
//			startActivity(abrir);
//		} else if (v.getId() == findViewById(R.id.btnCrearLista).getId()) {
//			abrir = new Intent(this, ActivityCrearLista.class);
//			startActivity(abrir);
//		} else if (v.getId() == findViewById(R.id.btnListaActual).getId()) {
//			abrir = new Intent(this, ActivityListaActual.class);
//			startActivity(abrir);
//		} else if (v.getId() == findViewById(R.id.btnElegir_Establecimiento)
//				.getId()) {
//			abrir = new Intent(this, ActivityEstablecimiento.class);
//			startActivity(abrir);
//		} else if (v.getId() == findViewById(R.id.btnHistorial).getId()) {
//			abrir = new Intent(this, ActivityHistorial.class);
//			startActivity(abrir);			
//		}
//
//	}
//
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.dashboard_layout);
	 
	        /**
	         * Creating all buttons instances
	         * */
	        // Dashboard crear lista button
	        final Button btn_crearlista = (Button) findViewById(R.id.btn_create_list);
	 
	        // Dashboard chekLista button
	        Button btn_chklista = (Button) findViewById(R.id.btn_check_list);
	 
	        // Dashboard establecimiento button
	        Button btn_elegirestab = (Button) findViewById(R.id.btn_store);
	 
	        // Dashboard historial button
	        Button btn_historial = (Button) findViewById(R.id.btn_history);
	 
//	        // Dashboard Events button
	        Button btn_map = (Button) findViewById(R.id.btn_map);
//	 
//	        // Dashboard Photos button
//	        Button btn_photos = (Button) findViewById(R.id.btn_photos);
//	 
	        /**
	         * Handling all button click events
	         * */
	 
	        // Listening to News Feed button click
	        btn_crearlista.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                // Launching News Feed Screen
	            	btn_crearlista.setBackgroundResource(R.drawable.create_list_hover);
	                Intent i = new Intent(getApplicationContext(), ActivityCrearLista.class);
	                startActivity(i);
	            }
	        });
	 
	       // Listening Friends button click
	        btn_chklista.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                // Launching News Feed Screen
	                Intent i = new Intent(getApplicationContext(), ActivityListaActual.class);
	                startActivity(i);
	            }
	        });
	 
	        // Listening Messages button click
	        btn_elegirestab.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                // Launching News Feed Screen
	                Intent i = new Intent(getApplicationContext(), ActivityEstablecimiento.class);
	                startActivity(i);
	            }
	        });
	 
	        // Listening to Places button click
	        btn_map.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                // Launching News Feed Screen
	                Intent i = new Intent(getApplicationContext(), ActivityDireccionActual.class);
	                startActivity(i);
	            }
	        });
	 
	        // Listening to Events button click
	        btn_historial.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                // Launching News Feed Screen
	                Intent i = new Intent(getApplicationContext(), ActivityHistorial.class);
	                startActivity(i);
	            }
	        });
	 
	        // Listening to Photos button click
//	        btn_photos.setOnClickListener(new View.OnClickListener() {
//	 
//	            public void onClick(View view) {
//	                // Launching News Feed Screen
//	                Intent i = new Intent(getApplicationContext(), PhotosActivity.class);
//	                startActivity(i);
//	            }
//	        });
	    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
