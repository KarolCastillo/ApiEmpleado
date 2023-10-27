package com.example.apilaravel;

import androidx.activity.result.IntentSenderRequest;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void irFormulario(View view){

        Intent irformulario = new Intent(this, FormularioActivity.class);
        startActivity(irformulario);

    }


}