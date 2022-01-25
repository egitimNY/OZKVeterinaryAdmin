package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import tech.halitpractice.OZKVeterinaryAdmin.Activities.MainActivity;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.GetSharedPreferences;

public class HomeFragment extends Fragment {

    private LinearLayout kampanyaLayout,asiTakipLayout,soruLayout,kullanicilarLayout,cikisLayout;
    private View view;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        clickToLayout();
        return view;
    }

    public void tanimla(){
        kampanyaLayout = view.findViewById(R.id.kampanyaLayout);
        asiTakipLayout = view.findViewById(R.id.asiTakipLayout);
        soruLayout = view.findViewById(R.id.soruLayout);
        kullanicilarLayout = view.findViewById(R.id.kullanicilarLayout);
        cikisLayout = view.findViewById(R.id.cikisLayout);
        changeFragments = new ChangeFragments(getContext());
    }

    public void clickToLayout(){
        kampanyaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changeFragments.changeBack(new KampanyaFragment());
            }
        });
        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.changeBack(new AsiTakipFragment());
            }
        });
        soruLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.changeBack(new SorularFragment());
            }
        });
        kullanicilarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.changeBack(new KullanicilarFragment());
            }
        });

        cikisLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences =new GetSharedPreferences(getActivity());
                getSharedPreferences.deleteToSession();
                Intent intent= new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
