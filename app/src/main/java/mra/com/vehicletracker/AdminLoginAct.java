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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mra.com.vehicletracker.Databaseclasses.Admin;

public class AdminLoginAct extends AppCompatActivity
{
    EditText username,password;
    ImageView backbtn;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        progressBar=(ProgressBar)findViewById(R.id.pb);
        progressDialog=new ProgressDialog(this);

        backbtn=(ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginAct.this,StartActivity.class));
            }
        });

        username=(EditText)findViewById(R.id.Auname);
        password=(EditText)findViewById(R.id.Apass);



        login=(Button) findViewById(R.id.Alogin);


        checkuser();
        //adminnameandpassword();


    }

    private void adminnameandpassword()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("v");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Admin i=dataSnapshot.getValue(Admin.class);
               // if(i.getName().equals())

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkuser()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // progressBar.setVisibility(View.VISIBLE);

                final String uname=username.getText().toString();
                final String pass=password.getText().toString();
                if(TextUtils.isEmpty(uname)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(AdminLoginAct.this,"Wrong Attempt",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    progressDialog.setMessage("Taking To Admin Account");
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
                                            startActivity(new Intent(AdminLoginAct.this,AdminMainActivity.class));
                                            // progressBar.setVisibility(View.VISIBLE);
                                            progressDialog.dismiss();
                                            finish();

                                        }
                                        else
                                        {
                                            Toast.makeText(AdminLoginAct.this,"Wrong Password",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AdminLoginAct.this,"Invalid User Name",Toast.LENGTH_SHORT).show();
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
