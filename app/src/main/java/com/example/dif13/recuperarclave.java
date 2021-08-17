package com.example.dif13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class recuperarclave extends AppCompatActivity {
    public LinearLayout at;
    public TextView cc;
    public Button rr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarclave);

        at = findViewById(R.id.atras);
        rr = findViewById(R.id.rcr);
        cc = findViewById(R.id.user);

        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (recuperarclave.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    public void validate(){
        String email = cc.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            cc.setError("Correo invalido!!!!");
            return;
        }

        sendEmail(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(recuperarclave.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    public void sendEmail (String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(recuperarclave.this, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(recuperarclave.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }else
                    Toast.makeText(recuperarclave.this, "Correo invalido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}