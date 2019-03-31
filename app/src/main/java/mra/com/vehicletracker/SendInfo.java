package mra.com.vehicletracker;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;

import mra.com.vehicletracker.Databaseclasses.PoliceAdd;

public class SendInfo extends AppCompatActivity
{
    EditText id,name,contact,username,pass;
    Button send;
    FirebaseAuth mauth;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info);


        id=(EditText)findViewById(R.id.pid);
        name=(EditText)findViewById(R.id.pname);
        contact=(EditText)findViewById(R.id.pcontact);
        username=(EditText)findViewById(R.id.pusername);
        pass=(EditText)findViewById(R.id.ppass);
        progressDialog=new ProgressDialog(this);

        send=(Button)findViewById(R.id.addpolice);
        mauth=FirebaseAuth.getInstance();

        RegisterVehicle();


    }

    private void RegisterVehicle()
    {

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String companyname=name.getText().toString();
                final String number=id.getText().toString();
                final String color=contact.getText().toString();
                final String user=username.getText().toString();
                final String type=pass.getText().toString();

                if(TextUtils.isEmpty(companyname)||TextUtils.isEmpty(number)||TextUtils.isEmpty(color)||TextUtils.isEmpty(user)||TextUtils.isEmpty(type))
                {
                    Toast.makeText(SendInfo.this,"Wrong Appempt",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    progressDialog.setMessage("Registering New Vechicle");
                    progressDialog.show();
                    final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("v");

                    reference.orderByChild("number").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.exists())
                            {
                                Toast.makeText(SendInfo.this,"Already Registered",Toast.LENGTH_SHORT).show();
                                progressDialog.setMessage("Registerd Number");
                                progressDialog.dismiss();
                            }

                            else
                            {
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("v");
                                DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("WhiteListVehicle");

                                String id=reference.push().getKey();
                                String id1=reference1.push().getKey();
                                Vehicleinfo policeAdd=new Vehicleinfo(id,type,color,number,companyname,user);
                                Vehicleinfo policeAdd1=new Vehicleinfo(id,type,color,number,companyname,user);
                                reference.child(id).setValue(policeAdd);
                                reference1.child(id).setValue(policeAdd1);
                                Toast.makeText(SendInfo.this,"Added",Toast.LENGTH_SHORT).show();
                                finish();
                                progressDialog.dismiss();




                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });

    }

}

