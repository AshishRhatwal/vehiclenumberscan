package mra.com.vehicletracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import mra.com.vehicletracker.R;
import mra.com.vehicletracker.Vehicleinfo;

/**
 * Created by mr. A on 27-03-2019.
 */

public class Whitlist extends RecyclerView.Adapter<Whitlist.ViewHolder>
{

    Context mcontext;
    List<Vehicleinfo> vehicleinfos;

    public Whitlist(Context mcontext,List<Vehicleinfo> vehicleinfos)
    {
        this.mcontext=mcontext;
        this.vehicleinfos=vehicleinfos;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.whitelistcard,parent,false);

        return new Whitlist.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Vehicleinfo vehicleinfo=vehicleinfos.get(position);
        holder.vname.setText(vehicleinfo.getName());
        holder.vcoloe.setText(vehicleinfo.getColor());
        holder.vnumber.setText(vehicleinfo.getNumber());
        holder.cname.setText(vehicleinfo.getCname());

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
           // del=itemView.findViewById(R.id.delbtn);
            cname=itemView.findViewById(R.id.cname);
        }
    }
}
