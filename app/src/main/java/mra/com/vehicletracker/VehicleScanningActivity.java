package mra.com.vehicletracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.Iterator;
import java.util.List;

import mra.com.vehicletracker.Databaseclasses.PoliceAdd;

public class VehicleScanningActivity extends AppCompatActivity {

    ImageView imageView;
    Button dbtn,sbtn;
    EditText txthai,q;
    Bitmap imageBitmap;
    Button getinfo;
    TextView check;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_scanning);
        imageView=(ImageView)findViewById(R.id.img12);
        dbtn=(Button)findViewById(R.id.detect);
        sbtn=(Button)findViewById(R.id.snap);
        txthai=(EditText) findViewById(R.id.texthai);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        check=(TextView)findViewById(R.id.prac);
       // q=(TextView)findViewById(R.id.texthai2);
        getinfo=(Button)findViewById(R.id.getinfo);
        progressDialog=new ProgressDialog(this);

        dbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textdetct();
            }
        });

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        info();

    }

    private void info()
    {
        getinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String value = txthai.getText().toString();
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("v");

                if(TextUtils.isEmpty(value))
                {
                    Toast.makeText(VehicleScanningActivity.this,"Enter Value Please...",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    progressDialog.setMessage("Fetching Information");
                    progressDialog.show();
                    reference.orderByChild("number").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {


                            if(dataSnapshot.exists())
                            {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    Vehicleinfo vehicleinfo=snapshot.getValue(Vehicleinfo.class);
                                    check.setText(vehicleinfo.getId());
                                    finish();



                                }
                                String id=check.getText().toString();
                                Intent i=new Intent(VehicleScanningActivity.this,FetchingValuesOfCardDetails.class);
                                i.putExtra("n",value);
                                i.putExtra("id",id);
                                startActivity(i);
                                progressDialog.dismiss();


                            }
                            else
                            {
                                check.setVisibility(View.VISIBLE);
                                check.setText("Not A Valid Number");
                                Toast.makeText(VehicleScanningActivity.this,"Not a Register Vehicle ",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {
                            Toast.makeText(VehicleScanningActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();


                        }
                    });


                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Logout11:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VehicleScanningActivity.this,PoliceLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                return true;
        }
        return false;
    }

    static final int Request=1;

    private void textdetct()
    {
        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector detector= FirebaseVision.getInstance().getVisionTextDetector();
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processText(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void processText(FirebaseVisionText firebaseVisionText)
    {
        List<FirebaseVisionText.Block> blocks=firebaseVisionText.getBlocks();
        if(blocks.size()==0)
        {
            Toast.makeText(VehicleScanningActivity.this,"text not detected...Try again",Toast.LENGTH_LONG).show();
            return;
        }

        for(FirebaseVisionText.Block block:firebaseVisionText.getBlocks())
        {
            String txt=block.getText();
            txthai.setTextSize(24);
            txthai.setText(txt);
        }
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePicture.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(takePicture,Request);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==Request&&resultCode==RESULT_OK)
        {
            Bundle extras=data.getExtras();
            imageBitmap=(Bitmap)extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }




}

