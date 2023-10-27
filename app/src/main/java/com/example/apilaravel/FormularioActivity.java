package com.example.apilaravel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormularioActivity extends AppCompatActivity {

    EditText edtCodigo, edtEmpleado, edtTelefono, edtCorreo, edtDireccion, edtDepartamento;
    Button btnEditar;
    Button btnBuscar;
   // Button btnListar;
    Button btnEliminar;

    Button btnRegistrar;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        edtCodigo=(EditText) findViewById(R.id.edtCodigo);
        edtEmpleado=(EditText) findViewById(R.id.edtEmpleado);
        edtTelefono=(EditText) findViewById(R.id.edtTelefono);
        edtCorreo=(EditText) findViewById(R.id.edtCorreo);
        edtDireccion=(EditText) findViewById(R.id.edtDireccion);
        edtDepartamento=(EditText) findViewById(R.id.edtDepartamento);
        btnEditar=(Button) findViewById(R.id.btnEditar);
        btnBuscar=(Button) findViewById(R.id.btnBuscar);
        //btnListar=(Button) findViewById(R.id.btnListar);
        btnEliminar=(Button) findViewById(R.id.btnEliminar);
        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarEmpleado("http://20.42.112.204/api/edit-empleados/24");
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarEmpleado("http://20.42.112.204/api/get-empleado/"+edtCodigo.getText()+"");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarEmpleado("http://20.42.112.204/api/delete-empleados/24");
            }

        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearEmpleado("http://20.42.112.204/api/save-empleados");
            }
        });
    }
    private void editarEmpleado(String URL){

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "GUARDADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("codigo_empleado",edtCodigo.getText().toString());
                parametros.put("nombre_empleado",edtEmpleado.getText().toString());
                parametros.put("numero_telefono",edtTelefono.getText().toString());
                parametros.put("correo",edtCorreo.getText().toString());
                parametros.put("direccion",edtDireccion.getText().toString());
                parametros.put("departamento",edtDepartamento.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void crearEmpleado(String URL){

        StringRequest stringRequest= new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "SE REGISTRO NUEVO EMPLEADO", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                Map<String,String> parametros = new HashMap<String, String>();
                String tipocontrol = "api";
                parametros.put("codigo_empleado",edtCodigo.getText().toString());
                parametros.put("nombre_empleado",edtEmpleado.getText().toString());
                parametros.put("numero_telefono",edtTelefono.getText().toString());
                parametros.put("correo",edtCorreo.getText().toString());
                parametros.put("direccion",edtDireccion.getText().toString());
                parametros.put("departamento",edtDepartamento.getText().toString());
                parametros.put("control", tipocontrol);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void buscarEmpleado(String URL){
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "EMPLEADO ENCONTRADO", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    edtCodigo.setText(jsonObject.getString("codigo_empleado"));
                    edtEmpleado.setText(jsonObject.getString("nombre_empleado"));
                    edtTelefono.setText(jsonObject.getString("numero_telefono"));
                    edtCorreo.setText(jsonObject.getString("correo"));
                    edtDireccion.setText(jsonObject.getString("direccion"));
                    edtDepartamento.setText(jsonObject.getString("departamento"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("XX ERROR XX", error.getMessage());
            }
        }
        );
        Volley.newRequestQueue(this).add(postRequest);

    }

    private void eliminarEmpleado(String URL){
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "EMPLEADO ELIMINADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    edtCodigo.setText(jsonObject.getString("codigo_empleado"));
                    edtEmpleado.setText(jsonObject.getString("nombre_empleado"));
                    edtTelefono.setText(jsonObject.getString("numero_telefono"));
                    edtCorreo.setText(jsonObject.getString("correo"));
                    edtDireccion.setText(jsonObject.getString("direccion"));
                    edtDepartamento.setText(jsonObject.getString("departamento"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("XX ERROR XX", error.getMessage());
            }
        }
        );
        Volley.newRequestQueue(this).add(postRequest);

    }

    public void regresarMenu(View view){
        Intent regresarmenu = new Intent(this, MainActivity.class);
        startActivity(regresarmenu);

    }
    public void irFormulariolista(View view){

        Intent irformulariolista = new Intent(this, ListarActivity.class);
        startActivity(irformulariolista);

    }
}