package tech.halitpractice.OZKVeterinaryAdmin.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import tech.halitpractice.OZKVeterinaryAdmin.Fragments.HomeFragment;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChangeFragments changeFragments = new ChangeFragments(MainActivity.this);
        changeFragments.changeBack(new HomeFragment());
    }
}
