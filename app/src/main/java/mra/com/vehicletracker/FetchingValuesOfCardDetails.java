package mra.com.vehicletracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FetchingValuesOfCardDetails extends AppCompatActivity {

    TextView Vnum,vname,vcolor,alreay,cname,ctype;
    Intent i;
    String g,h;
    Button Wl,Bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_values_of_card_details);

        Vnum=(TextView)findViewById(R.id.vnum);
        vname=(TextView)findViewById(R.id.vname);
        vcolor=(TextView)findViewById(R.id.vcolor);
        cname=(TextView)findViewById(R.id.cname);
        ctype=(TextView)findViewById(R.id.type);

        Wl=(Button)findViewById(R.id.whitelist);

        Bl=(Button)findViewById(R.id.blacklist);

        alreay=(TextView)findViewById(R.id.ar);

        i=getIntent();
        g=i.getStringExtra("n");
        h=i.getStringExtra("id");

        Vnum.setText(g);


        vehiclesDetails();

        VehicleList();

    }

    private void VehicleList()
    {
        Wl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(FetchingValuesOfCardDetails.this,"Admin Can Access This \n Default Vehicle Is White Listed",Toast.LENGTH_SHORT).show();


            }
        });

        Bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String number = Vnum.getText().toString();
                final String name = vname.getText().toString();
                final String color = vcolor.getText().toString();
                final String companyname=cname.getText().toString();
                final String vtype=ctype.getText().toString();
                if(name.equals("Not Valid"))
                {
                    Toast.makeText(FetchingValuesOfCardDetails.this,"NO Valid Vechicle",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Adding To BlackList

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BlackListVehicle");

                    reference.orderByChild("number").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists())
                            {
                                alreay.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("BlackListVehicle");
                                String id = reference1.push().getKey();
                                Vehicleinfo vehicleinfo = new Vehicleinfo(id, name, color, number,companyname,vtype);
                                reference1.child(id).setValue(vehicleinfo);
                                Toast.makeText(FetchingValuesOfCardDetails.this,"Black List Vehical",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });


                    //Removing From WhitList

                    final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("WhiteListVehicle");

                    reference1.orderByChild("number").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           for(DataSnapshot snapshot:dataSnapshot.getChildren())
                           {
                               snapshot.getRef().removeValue();
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

    private void vehiclesDetails()
    {





        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("v").child(h);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vehicleinfo sendInfo=dataSnapshot.getValue(Vehicleinfo.class);

                String n=Vnum.getText().toString();

                if(n.equals(sendInfo.getNumber()))
                {
                    vname.setText(sendInfo.getName());
                    vcolor.setText(sendInfo.getColor());
                    cname.setText(sendInfo.getCname());
                    ctype.setText(sendInfo.getType());
                }
                else
                {
                    vname.setText("Not Valid");
                    vcolor.setText("Not Valid");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(FetchingValuesOfCardDetails.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });



    }
}
