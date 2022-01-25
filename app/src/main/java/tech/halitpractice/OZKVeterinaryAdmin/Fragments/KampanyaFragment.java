package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Adapters.KampanyaAdapter;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaEkleModel;
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
    private ImageView kampanyaEkleImageView;
    private Bitmap bitmap;
    private String imageString;


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
        bitmap = null;
        imageString = "";
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
                    kampanyaAdapter = new KampanyaAdapter(kampanyaList,getContext(),getActivity());
                    kampanyaRecyclerView.setAdapter(kampanyaAdapter);


                }else {
                    Toast.makeText(getContext(), "There are no campaigns..", Toast.LENGTH_SHORT).show();
//                    changeFragments.change(new HomeFragment());
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

        final EditText kampanyaBaslikEditText = view.findViewById(R.id.kampanyaBaslikEditText);
        final EditText kampanyaIcerikEditText = view.findViewById(R.id.kampanyaIcerikEditText);
        kampanyaEkleImageView = view.findViewById(R.id.kampanyaEkleImageView);
        Button kampanyaEkleButon = view.findViewById(R.id.kampanyaEkleButon);
        Button kampanyaImageEkleButon = view.findViewById(R.id.kampanyaImageEkleButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        kampanyaImageEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });
        kampanyaEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageToString().equals("") && !kampanyaBaslikEditText.getText().toString().equals("") &&
                        !kampanyaIcerikEditText.getText().toString().equals("")) {

                        kampanyaEkle(kampanyaBaslikEditText.getText().toString(),kampanyaIcerikEditText.getText().toString(),
                                imageToString(),alertDialog);
                        kampanyaBaslikEditText.setText("");
                        kampanyaIcerikEditText.setText("");

                }else
                    {
                        Toast.makeText(getContext(), "All fields must be filled and the picture must be selected.", Toast.LENGTH_LONG).show();
                    }
            }
        });
        alertDialog.show();

    }

    public void galeriAc(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,777);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.i( "resimTetikleme",""+data.getData());
//        if (resultCode==777 && requestCode ==RESULT_OK && data!=null)

        if (requestCode==777 && data !=null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                kampanyaEkleImageView.setImageBitmap(bitmap);
                kampanyaEkleImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String imageToString(){
        if (bitmap!=null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(byt, Base64.DEFAULT);
            return imageString;
        }else {
            return imageString;
        }
    }

    public void kampanyaEkle(String baslik, String icerik, String imageString, final AlertDialog alertDialog){
        Call<KampanyaEkleModel> req = ManagerAll.getInstance().addKampanya(baslik,icerik,imageString);
        req.enqueue(new Callback<KampanyaEkleModel>() {
            @Override
            public void onResponse(Call<KampanyaEkleModel> call, Response<KampanyaEkleModel> response) {
                if (response.body().isTf()){
                    Toast.makeText(getContext(),response.body().getSonuc(), Toast.LENGTH_SHORT).show();
                    getKampanya();
                    alertDialog.cancel();
                }else {
                    Toast.makeText(getContext(),response.body().getSonuc(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<KampanyaEkleModel> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
