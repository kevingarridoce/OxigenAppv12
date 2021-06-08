package com.tit.oxigenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Patient extends AppCompatActivity {
    Button historicoBtn, infoBtn, diagramaBtn, medicacionBtn;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    TextView bienvenido_txt;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        historicoBtn = findViewById(R.id.historico_btn);
        infoBtn = findViewById(R.id.info_btn);
        diagramaBtn = findViewById(R.id.diagrama_btn);
        medicacionBtn = findViewById(R.id.medicacion_Pac_btn);
        bienvenido_txt = findViewById(R.id.bienvenido_txt);

        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        idUser = fAuth.getCurrentUser().getUid();

        Button logout = findViewById(R.id.logout_paciente_btn);

        bienvenido_txt.setText(user.getEmail());

        //Para llamar a otras clases y interfaz
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( "Paciente",idUser);
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getApplicationContext(),Login.class));
             //   finish();
            }
        });

        historicoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Historico_Paciente.class));
                finish();
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Informacion_Paciente.class));
                finish();
            }
        });

        diagramaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Diagrama_Paciente.class));
                finish();
            }
        });

        medicacionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Medicacion_Paciente.class));
                finish();
            }
        });
    }
}