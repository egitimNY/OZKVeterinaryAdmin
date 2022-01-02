package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Adapters.KampanyaAdapter;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView kampanyaRecyclerView;
    private List<KampanyaModel> kampanyaList;
    private KampanyaAdapter kampanyaAdapter;
    private ChangeFragments changeFragments;
    private Button kampanyaEkle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampanya, container, false);
        tanimla();
        getKampanya();
        click();
        return view;
    }

    public void tanimla(){
        kampanyaRecyclerView = view.findViewById(R.id.kampanyaRecView);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(),1);
        kampanyaRecyclerView.setLayoutManager(mng);
        kampanyaList = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
        kampanyaEkle = view.findViewById(R.id.kampanyaEkle);
    }

    public void click(){
        kampanyaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKampanyaAlert();
            }
        });
    }

    public void getKampanya(){
        Call<List<KampanyaModel>> req = ManagerAll.getInstance().getKampanya();
        req.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {

                Log.i( "kampanyalar",response.body().toString());
                if (response.body().get(0).isTf())
                {
                    kampanyaList = response.body();
                    kampanyaAdapter = new KampanyaAdapter(kampanyaList,getContext());
                    kampanyaRecyclerView.setAdapter(kampanyaAdapter);


                }else {
                    Toast.makeText(getContext(), "Herhangi bir kampanya bulunmamaktadir..", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }

            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addKampanyaAlert(){
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanya_ekle_layout,null);

        EditText kampanyaBaslikEditText = view.findViewById(R.id.kampanyaBaslikEditText);
        EditText kampanyaIcerikEditText = view.findViewById(R.id.kampanyaIcerikEditText);
        ImageView kampanyaEkleImageView = view.findViewById(R.id.kampanyaEkleImageView);
        Button kampanyaEkleButon = view.findViewById(R.id.kampanyaEkleButon);
        Button kampanyaImageEkleButon = view.findViewById(R.id.kampanyaImageEkleButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        alertDialog.show();

    }
}
