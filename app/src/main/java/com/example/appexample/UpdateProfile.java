package com.example.appexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class UpdateProfile extends AppCompatActivity{

    EditText username_up, password_up, email_up;
    Button button_update;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = user.getUid();
        documentReference = db.collection("user").document(currentUser);

        username_up = findViewById(R.id.username_up);
        email_up = findViewById(R.id.email_up);
        password_up = findViewById(R.id.pass_up);
        button_update = findViewById(R.id.btn_up);

        button_update.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                updateProfile();
            }

        });

        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists()){

                            String nameResult = task.getResult().getString("fullName");
                            String passwordResult = task.getResult().getString("password");
                            String emailResult = task.getResult().getString("email");

                            username_up.setText(nameResult);
                            password_up.setText(passwordResult);
                            email_up.setText(emailResult);

                        }else{
                            Toast.makeText(UpdateProfile.this, "No profile", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateProfile() {

        String name = username_up.getText().toString();
        String email = email_up.getText().toString();
        String password = password_up.getText().toString();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = user.getUid();
        final DocumentReference sDoc = db.collection("user").document(currentUser);
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply( Transaction transaction) throws FirebaseFirestoreException {

                transaction.update(sDoc, "fullName", name);
                transaction.update(sDoc, "email", email);
                transaction.update(sDoc, "password", password);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateProfile.this, "updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateProfile.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
