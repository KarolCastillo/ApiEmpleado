package com.example.apilaravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameRegisterEditText, emailRegisterEditText, passwordRegisterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameRegisterEditText = findViewById(R.id.nameEditText);
        emailRegisterEditText = findViewById(R.id.emailRegisterEditText);
        passwordRegisterEditText = findViewById(R.id.passwordRegisterEditText);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String url = "http://20.42.112.204/api/register";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.getString("message");
                            if ("Usuario Registrado con exito".equals(message)) {
                                // Redireccionar al login o a otra actividad luego del registro exitoso
                                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 400) { // Asumiendo que el código 400 es para la respuesta del error
                    try {
                        String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String message = jsonResponse.getString("message");
                        JSONObject errors = jsonResponse.getJSONObject("errors");

                        StringBuilder errorMessages = new StringBuilder(message + ": ");

                        if (errors.has("name")) {
                            String nameError = errors.getJSONArray("name").getString(0);
                            errorMessages.append(nameError).append(" ");
                        }

                        if (errors.has("email")) {
                            String emailError = errors.getJSONArray("email").getString(0);
                            errorMessages.append(emailError).append(" ");
                        }

                        if (errors.has("password")) {
                            String passwordError = errors.getJSONArray("password").getString(0);
                            errorMessages.append(passwordError).append(" ");
                        }

                        Toast.makeText(RegisterActivity.this, errorMessages.toString(), Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en el registro.", Toast.LENGTH_SHORT).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", nameRegisterEditText.getText().toString());
                params.put("email", emailRegisterEditText.getText().toString());
                params.put("password", passwordRegisterEditText.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
