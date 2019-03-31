package mra.com.vehicletracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import mra.com.vehicletracker.Databaseclasses.PoliceAdd;
import mra.com.vehicletracker.R;
import mra.com.vehicletracker.SendInfo;

/**
 * Created by mr. A on 14-03-2019.
 */

public class Manage extends RecyclerView.Adapter<Manage.ViewHolder>
{

    private Context mcontext;
    private List<PoliceAdd> items;
    private DatabaseReference reference;
    int overtotal=0;

    public Manage(Context mcontext, List<PoliceAdd> items)
    {
        this.mcontext=mcontext;
        this.items=items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.testing,parent,false);


        return new Manage.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {

        final PoliceAdd b=items.get(position);
        holder.name.setText(b.getPname());
        holder.id.setText(b.getPid());
        holder.user.setText(b.getPuser());
        holder.contact.setText(b.getPcontact());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Inspector:-"+holder.name.getText().toString(),Toast.LENGTH_SHORT).show();

            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=holder.name.getText().toString();
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Police");

                reference.orderByChild("pname").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
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
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,contact,id,user;
        ImageButton del;
        public ViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            contact=itemView.findViewById(R.id.contact);
            id=itemView.findViewById(R.id.id);
            user=itemView.findViewById(R.id.user);
            del=itemView.findViewById(R.id.delbtn);






        }}

}



