package aashay.com.firedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fact extends AppCompatActivity {

    Button post;
    EditText data;
    FirebaseAuth.AuthStateListener listener;
    DatabaseReference ref;
    FirebaseUser user;
    TextView complete, text;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact);


        post = findViewById(R.id.post);
        data = findViewById(R.id.data);
        complete = findViewById(R.id.completeText);
        text = findViewById(R.id.text);

        complete.setVisibility(View.INVISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent i = new Intent(Fact.this, MainActivity.class);
            startActivity(i);
        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.getText().toString().trim().length() > 0){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    ref = database.getReference().child("facts").child(user.getUid()).push();
                    ref.setValue(data.getText().toString());
                    text.setText(data.getText().toString());
                    data.setText("");
                    complete.setVisibility(View.VISIBLE);

                }
                else{
                    Toast.makeText(getBaseContext(),"This field cannot be blank",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent i = new Intent(Fact.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
