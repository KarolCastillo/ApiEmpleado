package com.example.apilaravel;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ListarActivity extends AppCompatActivity {
    Button btnListar2;
    public class Empleado {
        private String codigo_empleado;
        private String nombre_empleado;
        private String numero_telefono;
        private String correo;
        private String direccion;
        private String departamento;

        public String getCodigo_empleado() {
            return codigo_empleado;
        }

        public void setCodigo_empleado(String codigo_empleado) {
            this.codigo_empleado = codigo_empleado;
        }

        public String getNombre_empleado() {
            return nombre_empleado;
        }

        public void setNombre_empleado(String nombre_empleado) {
            this.nombre_empleado = nombre_empleado;
        }

        public String getNumero_telefono() {
            return numero_telefono;
        }

        public void setNumero_telefono(String numero_telefono) {
            this.numero_telefono = numero_telefono;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getDepartamento() {
            return departamento;
        }

        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        btnListar2=(Button) findViewById(R.id.btnListar2);

        btnListar2 = (Button) findViewById(R.id.btnListar2);
        btnListar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarEmpleados("http://20.42.112.204/api/get-empleados");
            }
        });

    }
    private void listarEmpleados(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parsea la respuesta JSON
                    JSONArray jsonArray = new JSONArray(response);

                    // Crea una lista de empleados para almacenar los datos
                    List<Empleado> empleados = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Crea un nuevo objeto Empleado y añádelo a la lista
                        Empleado empleado = new Empleado();
                        empleado.setCodigo_empleado(jsonObject.getString("codigo_empleado"));
                        empleado.setNombre_empleado(jsonObject.getString("nombre_empleado"));
                        empleado.setNumero_telefono(jsonObject.getString("numero_telefono"));
                        empleado.setCorreo(jsonObject.getString("correo"));
                        empleado.setDireccion(jsonObject.getString("direccion"));
                        empleado.setDepartamento(jsonObject.getString("departamento"));

                        empleados.add(empleado);
                    }

                    // Aquí debes mostrar la lista de empleados en tu vista. Puedes usar un RecyclerView, ListView o cualquier otro componente adecuado.
                    // Por ejemplo, puedes crear una nueva actividad o fragmento para mostrar la lista de empleados.

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error al obtener la lista de empleados", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
    public void irFormulario(View view){

        Intent irformulario = new Intent(this, FormularioActivity.class);
        startActivity(irformulario);

    }

}