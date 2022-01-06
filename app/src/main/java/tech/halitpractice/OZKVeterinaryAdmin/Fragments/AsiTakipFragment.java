package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Models.PetAsiTakipModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class AsiTakipFragment extends Fragment {

    private View view;
    private DateFormat format;
    private Date date;
    private String today;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_takip, container, false);
        tanimla();
        istekAt(today);
        return view;
    }

    public void tanimla(){
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date();
        date = Calendar.getInstance().getTime();
        today = format.format(date);
        Log.i( "bugununTarihi",today);
        changeFragments = new ChangeFragments(getContext());

    }

    public void istekAt(String tarih){
        Call<List<PetAsiTakipModel>> req = ManagerAll.getInstance().getAsiPet(tarih);
        req.enqueue(new Callback<List<PetAsiTakipModel>>() {
            @Override
            public void onResponse(Call<List<PetAsiTakipModel>> call, Response<List<PetAsiTakipModel>> response) {
                if (response.body().get(0).isTf()){
                    Toast.makeText(getContext(), "Bugun "+response.body().size() + " pete asi yapilacaktir", Toast.LENGTH_LONG).show();
                    Log.i( "bugunlukPetler",response.body().toString());
                }else {
                    Toast.makeText(getContext(), "Bugun asi yapilacak pet yoktur.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetAsiTakipModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}
