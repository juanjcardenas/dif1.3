package com.example.dif13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private Button crear, login;
    private EditText user, clave;
    private TextView rclave;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crear = findViewById(R.id.cc);
        login = findViewById(R.id.login);

        user =findViewById(R.id.user);
        clave = findViewById(R.id.clave);
        rclave = findViewById(R.id.rc);

        mAuth = FirebaseAuth.getInstance();
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, registro.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString().trim();
                String pass = clave.getText().toString().trim();

                if (!email.isEmpty() && !pass.isEmpty()){
                    if (pass.length()>5) {
                        signInWithEmailAndPassword(email, pass);

                    }else{
                        Toast.makeText(MainActivity.this,"Clave incompleta", Toast.LENGTH_SHORT).show();
                    }
                }else
                    validacion();
            }
        });

        rclave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this, recuperarclave.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void validacion() {
        String nuser = user.getText().toString().trim();
        String cl = clave.getText().toString().trim();

        if (nuser.equals("")){
            user.setError("Correo invalido!!!!");
            return;
        }
        else if (cl.equals("")){
            clave.setError("Required!!!!");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User", "" + currentUser);
    }

    public void signInWithEmailAndPassword(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EXITO", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(MainActivity.this, proceso.class);
                            startActivity(i);
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Datos incorrectos.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }//finalizacion del login



}