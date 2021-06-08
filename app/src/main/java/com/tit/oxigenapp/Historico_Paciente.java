package com.tit.oxigenapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Historico_Paciente extends AppCompatActivity {
    Button regresarBtn, buscarFecha;;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    private String idUser;
    RecyclerView recyclerViewHisorico;
    historicoAdapter mAdapter;
    FirebaseFirestore mFirestore;
    private int nYear, nMonth, nDay,sYear,sMonth, sDay;
    static final int DATE_ID = 0;
    Calendar C= Calendar.getInstance();
    EditText t1;
    int validacion;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_paciente);

        regresarBtn =  findViewById(R.id.regresar_btn);

        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        idUser = fAuth.getCurrentUser().getUid();

        recyclerViewHisorico= findViewById(R.id.recyclerHistorico);
        recyclerViewHisorico.setLayoutManager(new LinearLayoutManager(this ));
        mFirestore = FirebaseFirestore.getInstance();
        Date fecha = C.getTime();
        Log.d( "Paciente",C.getTime().toString());
        Query query = mFirestore.collection("Usuarios").document(idUser).collection("spo2");
        
       FirestoreRecyclerOptions<historico> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<historico>().setQuery(query, historico.class).build();
        mAdapter = new historicoAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewHisorico.setAdapter(mAdapter);


        sMonth=C.get(Calendar.MONTH)+1;
        sDay=C.get(Calendar.DAY_OF_MONTH);
        sYear=C.get(Calendar.YEAR);
        buscarFecha=findViewById(R.id.ButtonBuscarFechaHistorico);
        t1= (EditText)findViewById(R.id.editTextFechaHistorico);
        t1.setText(sMonth + "-" + sDay  + "-" + sYear);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);

                validacion=1;
            }
        });








        regresarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Patient.class));
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







    private void colocar_fecha() {
        t1.setText((nMonth +1   ) + "-" + nDay + "-" + nYear);

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    nYear = year;
                    nMonth = monthOfYear;
                    nDay = dayOfMonth;
                    colocar_fecha();

                }

            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYear, sMonth-1, sDay);


        }


        return null;
    }

}
