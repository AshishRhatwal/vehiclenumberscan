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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mra.com.vehicletracker.Databaseclasses.Admin;

public class SendAdmin extends AppCompatActivity {

    EditText on,op,nn,np;
    Button osnd,nsnd;
    ProgressDialog progressDialog;
    RelativeLayout O,N;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_admin);

        on=(EditText)findViewById(R.id.oname);
        op=(EditText)findViewById(R.id.opass);
        nn=(EditText)findViewById(R.id.NewN);
        np=(EditText)findViewById(R.id.NewP);
        progressDialog=new ProgressDialog(this);

        osnd=(Button)findViewById(R.id.check);
        nsnd=(Button)findViewById(R.id.check1);

        O=(RelativeLayout)findViewById(R.id.orel);
        N=(RelativeLayout)findViewById(R.id.nrel);


        OldPassCheck();
       NewPassSend();
    }

    private void NewPassSend()
    {
        nsnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Newname=nn.getText().toString();
                String Newpass=np.getText().toString();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Admin");
                String id=reference.push().getKey();
                Admin admin=new Admin(id,Newname,Newpass);
                reference.setValue(admin);
                finish();


            }
        });


    }

    private void OldPassCheck()
    {
        osnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname=on.getText().toString();
                final String pass=op.getText().toString();
                if(TextUtils.isEmpty(uname)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(SendAdmin.this,"Wrong Attempt",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    progressDialog.setMessage("Checking Please Wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Admin");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            Admin i=dataSnapshot.getValue(Admin.class);
                            if(i.getName().equals(uname))
                            {
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Admin");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        Admin i=dataSnapshot.getValue(Admin.class);
                                        if(i.getPass().equals(pass))
                                        {
                                            O.setVisibility(View.GONE);
                                            N.setVisibility(View.VISIBLE);
                                            progressDialog.dismiss();

                                        }
                                        else
                                        {
                                            Toast.makeText(SendAdmin.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                                            // progressBar.setVisibility(View.VISIBLE);
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
                                Toast.makeText(SendAdmin.this,"Invalid User Name",Toast.LENGTH_SHORT).show();
                                //  progressBar.setVisibility(View.VISIBLE);
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
