package mra.com.vehicletracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import mra.com.vehicletracker.R;
import mra.com.vehicletracker.Vehicleinfo;

/**
 * Created by mr. A on 14-03-2019.
 */

public class BlackList extends RecyclerView.Adapter<BlackList.ViewHolder>
{
    Context mcontext;
    List<Vehicleinfo> vehicleinfos;

    public BlackList(Context mcontext,List<Vehicleinfo> vehicleinfos)
    {
        this.mcontext=mcontext;
        this.vehicleinfos=vehicleinfos;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.blacklistvehiclecard,parent,false);

        return new BlackList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {

        Vehicleinfo vehicleinfo=vehicleinfos.get(position);
        holder.vname.setText(vehicleinfo.getName());
        holder.vcoloe.setText(vehicleinfo.getColor());
        holder.vnumber.setText(vehicleinfo.getNumber());
        holder.cname.setText(vehicleinfo.getCname());


        final String type=vehicleinfo.getType();
        final  String color=vehicleinfo.getColor();
        final  String number=vehicleinfo.getNumber();
        final  String companyname=vehicleinfo.getCname();
        final  String user=vehicleinfo.getName();



        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String text=holder.vnumber.getText().toString();
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("BlackListVehicle");

                reference.orderByChild("number").equalTo(text).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists())
                        {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                snapshot.getRef().removeValue();

                                DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("WhiteListVehicle");
                                String id=reference.push().getKey();
                                Vehicleinfo policeAdd1=new Vehicleinfo(id,type,color,number,companyname,user);
                                reference1.child(id).setValue(policeAdd1);

                            }
                        }
                        else
                        {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleinfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView vname,vcoloe,vnumber,cname;
        ImageButton del;

        public ViewHolder(View itemView) {
            super(itemView);

            vname=itemView.findViewById(R.id.vname);
            vnumber=itemView.findViewById(R.id.vnum);
            vcoloe=itemView.findViewById(R.id.vcolor);
            del=itemView.findViewById(R.id.delbtn);
            cname=itemView.findViewById(R.id.cname);
        }
    }
}
