package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullaniciPetlerModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class KullaniciPetlerFragment extends Fragment {

    private String musid;
    private View view;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_kullanici_petler, container, false);
        tanimla();
        getPets(musid);
        return view;
    }

    public void tanimla(){

        musid = getArguments().get("userid").toString();
        Log.i( "gelenMusteriId",musid);
        changeFragments = new ChangeFragments(getContext());

    }

    public void getPets(String id){
        Call<List<KullaniciPetlerModel>> req = ManagerAll.getInstance().getPets(id);
        req.enqueue(new Callback<List<KullaniciPetlerModel>>() {
            @Override
            public void onResponse(Call<List<KullaniciPetlerModel>> call, Response<List<KullaniciPetlerModel>> response) {
                if (response.body().get(0).isTf()){
                    Toast.makeText(getContext(), "Kullanciya Ait "+response.body().size()+" tane pet bulunmustur. ", Toast.LENGTH_LONG).show();

                }else {
//                    changeFragments.changeBack(new KullanicilarFragment());
                    Toast.makeText(getContext(), "Kullanciya ait pet bulunamamistir.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<KullaniciPetlerModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
