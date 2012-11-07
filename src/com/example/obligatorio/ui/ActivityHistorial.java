package com.example.obligatorio.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class ActivityHistorial extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial);

		ArrayList<String> historial = new ArrayList<String>();
		historial.add("03/05/2012-$415");
		historial.add("23/08/2012-$100");
		historial.add("06/10/2012-$386");

		ListView lstHistorial = (ListView) findViewById(R.id.listHistorial);

		lstHistorial.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, historial));
		//simple_list_item_multiple_choice
		
		
		lstHistorial.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					Intent abrir = new Intent(ActivityHistorial.this, ActivityListaActual.class);//getApplicationContext
						
						startActivity(abrir);
//				**finish();
				
				//ActivityHistorial.this.startActivity(abrir);
			//	startActivityForResult(abrir,0);
				
//				Intent intent = new Intent();
//		           intent.setClass(ActivityHistorial.this, ActivityListaActual.class);
//		          
//		           startActivityForResult(intent, 0); 
			}
		});
		

	}
	
	

}
