package ca.uvic.k4ncelled;

import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.uvic.k4ncelled.Backend.Food;
import ca.uvic.k4ncelled.Backend.Fridge;
import ca.uvic.k4ncelled.Data.Data;

public class MainActivity extends AppCompatActivity {
    private Fridge fridge;
    private Data data;

    private ConstraintLayout activeLayout;

    private ConstraintLayout lo_browse;
    private ConstraintLayout lo_add;

    private BottomNavigationView nm_navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new Data(getApplicationContext());

        fridge = data.load();

        assignComponents();

        setActiveLayout(lo_browse);

        create_navigationView();

    }

    private void setActiveLayout(ConstraintLayout layout){
        if(activeLayout != null){
            activeLayout.setVisibility(View.GONE);
        }
        activeLayout = layout;
        activeLayout.setVisibility(View.VISIBLE);
    }

    private void assignComponents(){
        nm_navigationView = findViewById(R.id.bottomNavigationView);
        lo_browse = findViewById(R.id.lo_browse);
        lo_add = findViewById(R.id.lo_add);
    }

    private void create_navigationView(){
        nm_navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.mi_browse){
                    setActiveLayout(lo_browse);
                }
                else if(menuItem.getItemId() == R.id.mi_add){
                    setActiveLayout(lo_add);
                }
                return true;
            }
        });
        nm_navigationView.setSelectedItemId(R.id.mi_browse);
    }


    @Override
    protected void onStop() {
        super.onStop();
        data.save(fridge);
    }
}
