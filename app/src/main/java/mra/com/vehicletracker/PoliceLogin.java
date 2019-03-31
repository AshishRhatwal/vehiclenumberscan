package mra.com.vehicletracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mra.com.vehicletracker.Databaseclasses.PoliceAdd;

public class PoliceLogin extends AppCompatActivity {

    EditText username,password;
    Button login;
    ProgressDialog progressDialog;
    FirebaseUser user;
    FirebaseAuth ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);

        username=(EditText)findViewById(R.id.Auname);
        password=(EditText)findViewById(R.id.Apass);

        login=(Button) findViewById(R.id.Alogin);
        ma=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent i=new Intent(PoliceLogin.this,VehicleScanningActivity.class);
            startActivity(i);
            finish();
        }


        //checkuser();
       check();
    }

    private void check()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {

                    final String name = username.getText().toString();
                    final String pass = password.getText().toString();

                    if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pass))
                    {
                        Toast.makeText(PoliceLogin.this, "Wrong Atempt", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        progressDialog.setMessage("Taking To Account");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        ma.signInWithEmailAndPassword(name, pass).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(PoliceLogin.this, VehicleScanningActivity.class));
                                            finish();
                                            progressDialog.dismiss();

                                        }
                                        else
                                            {
                                            Toast.makeText(PoliceLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }

                                    }
                                });

                    }


                }
                catch (Exception e)
                {
                    Toast.makeText(PoliceLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private void checkuser()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               final String name=username.getText().toString();
               final  String pass=password.getText().toString();


               if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pass))
               {
                   Toast.makeText(PoliceLogin.this,"Enter Values",Toast.LENGTH_SHORT).show();

               }
               else
               {
                   progressDialog.setMessage("Loading ");
                   progressDialog.show();

                   DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Police");

                   reference.orderByChild("puser").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                       {
                           if(dataSnapshot.exists())
                           {
                               DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Police");

                               reference.orderByChild("ppassword").equalTo(pass).addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                   {
                                       if(dataSnapshot.exists())
                                       {
                                           for(DataSnapshot snapshot: dataSnapshot.getChildren())
                                           {
                                               PoliceAdd policeAdd=dataSnapshot.getValue(PoliceAdd.class);
                                               final String id=policeAdd.getId();
                                               finish();
                                           }
                                           Intent i=new Intent(PoliceLogin.this,VehicleScanningActivity.class);
                                           // i.putExtra("id",id);
                                           startActivity(i);
                                           progressDialog.dismiss();


                                       }
                                       else
                                       {
                                           Toast.makeText(PoliceLogin.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                                           progressDialog.dismiss();
                                       }


                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });

                           }
                           else
                           {
                               Toast.makeText(PoliceLogin.this,"Invalid User Name",Toast.LENGTH_SHORT).show();
                               progressDialog.dismiss();
                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError)
                       {

                       }
                   });

               }


            }
        });



    }
}
