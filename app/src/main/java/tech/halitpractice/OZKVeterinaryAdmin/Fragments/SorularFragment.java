package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.os.Bundle;
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
import tech.halitpractice.OZKVeterinaryAdmin.Adapters.VeterinerSoruAdapter;
import tech.halitpractice.OZKVeterinaryAdmin.Models.SoruModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;

public class SorularFragment extends Fragment {

    private View view;
    private RecyclerView soruRecyView;
    private List<SoruModel> list;
    private VeterinerSoruAdapter veterinerSoruAdapter;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sorular, container, false);
        tanimla();
        istekAt();
        return view;
    }

    public void tanimla(){
        soruRecyView = view.findViewById(R.id.soruRecyView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        soruRecyView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());

    }

    public void istekAt(){
        Call<List<SoruModel>> req = ManagerAll.getInstance().getSoru();
        req.enqueue(new Callback<List<SoruModel>>() {
            @Override
            public void onResponse(Call<List<SoruModel>> call, Response<List<SoruModel>> response) {
                if (response.body().get(0).isTf()){
                    list = response.body();
                    veterinerSoruAdapter = new VeterinerSoruAdapter(list,getContext(),getActivity());
                    soruRecyView.setAdapter(veterinerSoruAdapter);

                }else {
//                    Toast.makeText(getContext(), "Veteriner hekime hic soru sorulmamistir. ", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), " No questions were asked to the veterinarian. ", Toast.LENGTH_LONG).show();
                    changeFragments.changeBack(new HomeFragment());

                }
            }

            @Override
            public void onFailure(Call<List<SoruModel>> call, Throwable t) {

            }
        });
    }
}
