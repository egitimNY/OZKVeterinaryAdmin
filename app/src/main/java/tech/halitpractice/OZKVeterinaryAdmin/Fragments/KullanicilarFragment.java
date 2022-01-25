package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Adapters.UserAdapter;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullanicilarModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class KullanicilarFragment extends Fragment {

    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView kullaniciRecyView;
    private List<KullanicilarModel> list;
    private UserAdapter userAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanicilar, container, false);
        tanimla();
        getKullanicilar();
        return view;
    }

    public void tanimla(){
        changeFragments = new ChangeFragments(getContext());
        kullaniciRecyView = view.findViewById(R.id.kullaniciRecyView);
        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(getContext(),1);
        kullaniciRecyView.setLayoutManager(layoutManager);
        list = new ArrayList<>();

    }

    public void getKullanicilar(){
        Call<List<KullanicilarModel>> req = ManagerAll.getInstance().getKullanicilar();
        req.enqueue(new Callback<List<KullanicilarModel>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModel>> call, Response<List<KullanicilarModel>> response) {
                if (response.body().get(0).isTf()){
                    list = response.body();
                    userAdapter = new UserAdapter(list,getContext(),getActivity());
                    kullaniciRecyView.setAdapter(userAdapter);
                    Log.i( "kullanicilar",response.body().toString());

                }else {
                    Toast.makeText(getContext(), "There is no user registered to the system..", Toast.LENGTH_LONG).show();
                    changeFragments.changeBack(new HomeFragment());
                }

            }

            @Override
            public void onFailure(Call<List<KullanicilarModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
