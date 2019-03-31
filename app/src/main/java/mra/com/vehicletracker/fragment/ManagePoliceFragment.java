package mra.com.vehicletracker.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mra.com.vehicletracker.Databaseclasses.PoliceAdd;
import mra.com.vehicletracker.R;
import mra.com.vehicletracker.adapter.Manage;


public class ManagePoliceFragment extends Fragment {


    RecyclerView recyclerView;
    private List<PoliceAdd> cart;
    private Manage cartAdapter;
    Button byu;
    TextView t;

    private int total=0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_manage_police, container, false);
        recyclerView=view.findViewById(R.id.managerecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        t=view.findViewById(R.id.t);

        cart=new ArrayList<>();
        readPolice();
        return view;


    }

    private void readPolice()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Police");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cart.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    PoliceAdd item=snapshot.getValue(PoliceAdd.class);
                    cart.add(item);






                }

                cartAdapter=new Manage(getContext(),cart);
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {


            }
        });



    }



}
