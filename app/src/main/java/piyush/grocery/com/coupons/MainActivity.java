package piyush.grocery.com.coupons;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout available,clip;
    Toolbar toolbar;
    TextView txt_available,txt_clip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Coupons");
        clip=(LinearLayout)findViewById(R.id.clip);
        available=(LinearLayout)findViewById(R.id.available);
        txt_available=(TextView)findViewById(R.id.txt_available);
        txt_clip=(TextView)findViewById(R.id.txt_clip);

        loadFragment(new AvailableFragment());

        clip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Fetch Available Clipped Fragment
                loadFragment(new ClippedFragment());
                txt_available.setTextColor(Color.parseColor("#ABB2B9"));
                txt_clip.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Fetch Available Coupons Fragment
                loadFragment(new AvailableFragment());
                txt_available.setTextColor(Color.parseColor("#FFFFFF"));
                txt_clip.setTextColor(Color.parseColor("#ABB2B9"));
            }
        });
    }

    private void loadFragment(Fragment fragment) {

        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
