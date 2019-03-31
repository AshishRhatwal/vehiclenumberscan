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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mra.com.vehicletracker.R;
import mra.com.vehicletracker.Vehicleinfo;
import mra.com.vehicletracker.adapter.BlackList;
import mra.com.vehicletracker.adapter.Whitlist;


public class WhitelistFragment extends Fragment
{
    RecyclerView recyclerView;
    private List<Vehicleinfo> vehicleinfoList;
    private Whitlist whitlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_whitelist, container, false);

        recyclerView=view.findViewById(R.id.blackrec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        vehicleinfoList=new ArrayList<>();
        readwhitelist();
        return view;
    }

    private void readwhitelist()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhiteListVehicle");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vehicleinfoList.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Vehicleinfo item=snapshot.getValue(Vehicleinfo.class);
                    vehicleinfoList.add(item);


                }

                whitlist=new Whitlist(getContext(),vehicleinfoList);
                recyclerView.setAdapter(whitlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {


            }
        });


    }
}
