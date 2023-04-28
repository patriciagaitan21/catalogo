package com.ugb.catalogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    BD db_catalogo;
    String accion="nuevo";
    String id="";
    Button btn;
    TextView temp;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnGuardar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_catalogo();
            }

        });
        fab =findViewById(R.id.fabRegresarLista);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarListaProducto();
            }
        });
        mostrar_datos_producto();
    }

    void mostrar_datos_producto(){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if (accion.equals("modificar")){
            String productos[] = parametros.getStringArray("amigos");
            id = productos[0];

            temp = findViewById(R.id.txtcodigo);
            temp.setText(productos[1]);

            temp = findViewById(R.id.txtdescripcion);
            temp.setText(productos[2]);

            temp = findViewById(R.id.txtmarca);
            temp.setText(productos[3]);


            temp = findViewById(R.id.txtpresentacion);
            temp.setText(productos[4]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(productos[5]);

            temp = findViewById(R.id.txtstock);
            temp.setText(productos[6]);
        }
    }
     void guardar_catalogo(){
        try {
            temp = (TextView) findViewById(R.id.txtcodigo);
            String codigo = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtdescripcion);
            String descripcion =temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtmarca);
            String marca =temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtpresentacion);
            String presentacion =temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio =temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtstock);
            String stock =temp.getText().toString();

             db_catalogo = new BD(MainActivity.this, "", null, 1);
             String result = db_catalogo.administrar_catalogo(id,codigo,descripcion,marca,presentacion,precio,stock,accion, "Eliminar");
             String msg = result;
             if (result.equals("ok")){
                 msg = "Producto Guardado Con Exito";
                 regresarListaProducto();
             }
             Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar catalogo:"+ e.getMessage(), Toast.LENGTH_SHORT).show();
     }

}

void regresarListaProducto(){
    Intent iListaProducto = new Intent(MainActivity.this, lista_producto.class);
    startActivity(iListaProducto);
}

}

