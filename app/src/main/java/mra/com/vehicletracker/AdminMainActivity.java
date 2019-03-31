package mra.com.vehicletracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class AdminMainActivity extends AppCompatActivity {

    CardView cardView,cardView1,cardView3;
    TextView re,newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        cardView=(CardView)findViewById(R.id.addpoli);
        cardView1=(CardView)findViewById(R.id.blacklist);
        cardView3=(CardView)findViewById(R.id.managepolice);
        newpassword=(TextView)findViewById(R.id.slogon);
        re=(TextView)findViewById(R.id.re);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AddPoliceAdminActivity.class));
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,BlackListedVehicleForAdmin.class));
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AdminManagePolice.class));
            }
        });

        newpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,SendAdmin.class));
            }
        });

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,SendInfo.class));
            }
        });

    }


}
