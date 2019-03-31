package mra.com.vehicletracker;

import android.app.ProgressDialog;
import android.os.Build;
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

public class AddPoliceAdminActivity extends AppCompatActivity
{
    EditText id,name,contact,username,pass;
    Button send;
    FirebaseAuth mauth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_police_admin);

        id=(EditText)findViewById(R.id.pid);
        name=(EditText)findViewById(R.id.pname);
        contact=(EditText)findViewById(R.id.pcontact);
        username=(EditText)findViewById(R.id.pusername);
        pass=(EditText)findViewById(R.id.ppass);
        progressDialog=new ProgressDialog(this);

        send=(Button)findViewById(R.id.addpolice);
        mauth=FirebaseAuth.getInstance();





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pname=name.getText().toString();
                final String pid=id.getText().toString();
                final String pcnt=contact.getText().toString();
                final String user=username.getText().toString();
                final String password=pass.getText().toString();

                if(TextUtils.isEmpty(pname)||TextUtils.isEmpty(pid)||TextUtils.isEmpty(pcnt)||TextUtils.isEmpty(user)||TextUtils.isEmpty(password))
                {
                    Toast.makeText(AddPoliceAdminActivity.this,"Wrong Appempt",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    progressDialog.setMessage("New Police Adding");
                    progressDialog.show();
                    final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Police");

                    reference.orderByChild("pname").equalTo(pname).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.exists())
                            {
                                Toast.makeText(AddPoliceAdminActivity.this,"Name Exits",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                            else
                            {
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Police");

                                reference.orderByChild("pid").equalTo(pid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists())
                                        {
                                            Toast.makeText(AddPoliceAdminActivity.this,"id Exits",Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        }
                                        else
                                        {
                                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Police");

                                            reference.orderByChild("puser").equalTo(user).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    if(dataSnapshot.exists())
                                                    {
                                                        Toast.makeText(AddPoliceAdminActivity.this,"User Exits",Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();

                                                    }
                                                    else
                                                    {
                                                        mauth.createUserWithEmailAndPassword(user,password)
                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task)
                                                                    {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            FirebaseUser userid=mauth.getCurrentUser();
                                                                            String id1=userid.getUid();
                                                                            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Police").child(id1);

                                                                            String id=reference.push().getKey();

                                                                            PoliceAdd policeAdd=new PoliceAdd(id,pid,pname,pcnt,user,password);
                                                                            reference.setValue(policeAdd);
                                                                            Toast.makeText(AddPoliceAdminActivity.this,"Added",Toast.LENGTH_SHORT).show();
                                                                            finish();
                                                                            progressDialog.dismiss();
                                                                        }
                                                                        else
                                                                        {
                                                                            Toast.makeText(AddPoliceAdminActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                            progressDialog.dismiss();

                                                                        }

                                                                    }
                                                                });


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



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
