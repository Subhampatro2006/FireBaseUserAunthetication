package com.example.firebaseuseraunthetication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    EditText edt1,edt2,edt3;
    Button btn1,btn2;
    Toolbar tb1;
 FirebaseDatabase firebaseDatabase;
 DatabaseReference databaseReference;
 StudentData studentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        edt1=findViewById(R.id.edt1);
        edt2=findViewById(R.id.edt2);
        edt3=findViewById(R.id.edt3);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);

        tb1=findViewById(R.id.tb1);
        setSupportActionBar(tb1);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tb1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();

        // Get reference for the database
        databaseReference = firebaseDatabase.getReference("StudentData");
        studentData = new StudentData();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt1.getText().toString();
                String phone = edt2.getText().toString();
                String address = edt3.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || address.isEmpty()){
                    Toast.makeText(AddActivity.this, "Enter data", Toast.LENGTH_SHORT).show();
                }else{
                    addDatatoFirebase(name,phone,address);

                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt1.setText("");
                edt2.setText("");
                edt3.setText("");

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addDatatoFirebase(String name, String phone, String address) {

        studentData.setName(name);
        studentData.setPhone(phone);
        studentData.setAddress(address);

        databaseReference.push().setValue(studentData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}