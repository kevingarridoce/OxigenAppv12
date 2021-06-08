package com.tit.oxigenapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Historico_Paciente_Doctor extends AppCompatActivity {
    Button regresarBtn;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    private String idUser;
    RecyclerView recyclerViewHisorico;
    historicoAdapter mAdapter;
    FirebaseFirestore mFirestore;
    String numero = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_paciente_doctor);

        regresarBtn = findViewById(R.id.doctor_regresar_btn);

        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        idUser = fAuth.getCurrentUser().getUid();

        Bundle parametros = this.getIntent().getExtras();
        String datos = parametros.getString("datos");
        Log.d( "Paciente",datos);
        recyclerViewHisorico = findViewById(R.id.recyclerHistorico);
        recyclerViewHisorico.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();

        Query query = mFirestore.collection("Usuarios").document(datos).collection("spo2");

        FirestoreRecyclerOptions<historico> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<historico>().setQuery(query, historico.class).build();
        mAdapter = new historicoAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewHisorico.setAdapter(mAdapter);


        regresarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Doctor.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}

