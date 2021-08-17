package com.example.dif13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class registro extends AppCompatActivity {

    public TextView pis;
    public EditText nom, ap, gm, pass, passconf, tel, fn;
    Button rg;
    //private int dia, mes, ano;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        pis=findViewById(R.id.pis);

        nom=findViewById(R.id.nom);
        ap=findViewById(R.id.ap);
        gm=findViewById(R.id.gmail);
        pass=findViewById(R.id.pass);
        passconf=findViewById(R.id.passconf);
        tel=findViewById(R.id.tel);
        fn=findViewById(R.id.fn);
        rg=findViewById(R.id.reg);

        pis.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(registro.this,MainActivity.class);
                startActivity(i);
            }
        });

        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento(v);
            }
        });


    }//aca termina el metodo ON create

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User",""+currentUser);
    }

    public void createUserWithEmailAndPassword(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EXITO", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registro.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void  evento (View v) {

        String gmail = gm.getText().toString().trim();
        String clave = pass.getText().toString().trim();
        String veri = passconf.getText().toString().trim();

        if (!gmail.isEmpty() && !clave.isEmpty() && !veri.isEmpty()) {
            if (clave.equals(veri)){
                if (clave.length()>6 && veri.length()>6){
                    createUserWithEmailAndPassword(gmail,clave);
                    Toast.makeText(registro.this, "Registro satisfactorio", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(registro.this, "La clave debe terner mas de 8 Caracteres", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(registro.this, "las claves no son iguales", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(registro.this, "todos los campos se deben llenar", Toast.LENGTH_SHORT).show();
        }
    }
}