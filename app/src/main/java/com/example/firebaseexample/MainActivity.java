package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameet,qtyet,pricet;
    Button postbt,retrievebt;

    //Test commit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameet = findViewById(R.id.name);
        qtyet = findViewById(R.id.price);
        pricet = findViewById(R.id.qty);
        postbt = findViewById(R.id.addData);
        retrievebt = findViewById(R.id.retrieve);

        postbt.setOnClickListener(this);
        retrievebt.setOnClickListener(this);
        //ALT+ENTER
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retrieve:
                retrieveDataFromFirestore();
                break;
            case R.id.addData:
                addDataIntoFirestore();
                break;
        }
    }

    public void addDataIntoFirestore(){
        String name = nameet.getText().toString();
        String qty = qtyet.getText().toString();
        String price = pricet.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> order = new HashMap<>();
        order.put("name", name);
        order.put("qty", qty);
        order.put("price", price);

// Add a new document with a generated ID
     db.collection("Orders")
             .add(order)
             .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
         @Override
         public void onComplete(@NonNull Task<DocumentReference> task) {
             Toast.makeText(MainActivity.this,"Added",Toast.LENGTH_LONG).show();
         }
     }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {

             Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
         }
     });
    }

    public void retrieveDataFromFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        System.out.println(documentSnapshot.getData().toString());
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
