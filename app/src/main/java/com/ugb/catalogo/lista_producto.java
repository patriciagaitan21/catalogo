package com.ugb.catalogo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
public class lista_producto extends AppCompatActivity {
    Bundle parametros = new Bundle();
    BD db_catalogo;
    ListView lts;
    Cursor cProducto;
    FloatingActionButton btn;
    final ArrayList<String> alProducto = new ArrayList<String>();
    final ArrayList<String> alProductoCopy = new ArrayList<String>();
    protected void onCreate(Bundle instance){
        super.onCreate(instance);
        setContentView(R.layout.lista_producto);
        obtenerDatosProducto();
        buscarProducto();
        btn = findViewById(R.id.btnAgregarProducto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirAgregarProducto(parametros);
            }
        });
    }
    public void abrirAgregarProducto(Bundle parametros){
        Intent iAgregarProducto = new Intent(lista_producto.this, MainActivity.class);
        iAgregarProducto.putExtras(parametros);
        startActivity(iAgregarProducto);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        cProducto.moveToPosition(info.position);
        menu.setHeaderTitle(cProducto.getString(1));
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirAgregarProducto(parametros);
                    return true;
                case R.id.mnxModificar:

                    String producto[] = {
                            cProducto.getString(0),
                            cProducto.getString(1),
                            cProducto.getString(2),
                            cProducto.getString(3),
                            cProducto.getString(4),
                            cProducto.getString(5),
                            cProducto.getString(6),
                    };
                    parametros.putString("accion", "modificar");
                    parametros.putStringArray("producto", producto);
                    abrirAgregarProducto(parametros);
                    return true;
                case R.id.mnxEliminar:
                    eliminarDatosProducto();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch (Exception e){
            return super.onContextItemSelected(item);
        }
    }

    void eliminarDatosProducto(){
        try{
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(lista_producto.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(cProducto.getString(1));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_catalogo.administrar_catalogo(cProducto.getString(0),"", "", "", "", "", "", "Eliminar", "Eliminar");
                    obtenerDatosProducto();
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            Toast.makeText(this, "Error al eliminar: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerDatosProducto(){
        try {
            db_catalogo = new BD(lista_producto.this, "", null, 1);
            cProducto = db_catalogo.consultar_catalogo();
            if(cProducto.moveToFirst()){
                lts = findViewById(R.id.ltsProducto);
                final ArrayAdapter<String> adProducto = new ArrayAdapter<String>(lista_producto.this,
                        android.R.layout.simple_expandable_list_item_1, alProducto);
                lts.setAdapter(adProducto);
                do{
                    alProducto.add(cProducto.getString(1));//1 es el nombre del amigo, pues 0 es el idAmigo.
                }while(cProducto.moveToNext());
                adProducto.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{
                Toast.makeText(this, "NO HAY datos que mostrar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener producto: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void buscarProducto(){
        TextView temp = findViewById(R.id.txtBuscarProducto);
        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    alProducto.clear();
                    String valor = temp.getText().toString().trim().toLowerCase();
                    if (valor.length() <= 0) {
                        alProducto.addAll(alProductoCopy);
                    } else {

                    }

                }catch (Exception e){
                    Toast.makeText(lista_producto.this, "Error al buscar producto: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
