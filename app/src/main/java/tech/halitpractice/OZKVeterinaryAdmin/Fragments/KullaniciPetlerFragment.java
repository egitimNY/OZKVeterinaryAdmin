package tech.halitpractice.OZKVeterinaryAdmin.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;
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
import tech.halitpractice.OZKVeterinaryAdmin.Adapters.PetAdapter;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullaniciPetlerModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.PetEkle;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class KullaniciPetlerFragment extends Fragment {

    private String musid;
    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView userPetListRecView;
    private ImageView petEkleResimYok;
    private TextView petEkleUyariText;
    private Button userPetEkle;
    private List<KullaniciPetlerModel> list;
    private PetAdapter petAdapter;
    private ImageView petEkleImageView;
    private Bitmap bitmap;
    private String imageString = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_kullanici_petler, container, false);
        tanimla();
        getPets(musid);
        click();
        return view;
    }

    public void tanimla(){

        musid = getArguments().get("userid").toString();
        Log.i( "gelenMusteriId",musid);
        changeFragments = new ChangeFragments(getContext());

        userPetListRecView = view.findViewById(R.id.userPetListRecView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        userPetListRecView.setLayoutManager(layoutManager);
        petEkleResimYok = view.findViewById(R.id.petEkleResimYok);
        petEkleUyariText = view.findViewById(R.id.petEkleUyariTex);
        userPetEkle = view.findViewById(R.id.userPetEkle);
        list = new ArrayList<>();
        bitmap = null;
    }

    public void click(){
        userPetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petEkleAlert();
            }
        });
    }

    public void getPets(String id){
        Call<List<KullaniciPetlerModel>> req = ManagerAll.getInstance().getPets(id);
        req.enqueue(new Callback<List<KullaniciPetlerModel>>() {
            @Override
            public void onResponse(Call<List<KullaniciPetlerModel>> call, Response<List<KullaniciPetlerModel>> response) {
                if (response.body().get(0).isTf()){
                    userPetListRecView.setVisibility(View.VISIBLE);
                    petEkleResimYok.setVisibility(View.GONE);
                    petEkleUyariText.setVisibility(View.GONE);
                    list = response.body();
                    petAdapter = new PetAdapter(list,getContext(),getActivity(),musid);
                    userPetListRecView.setAdapter(petAdapter);
//                    Toast.makeText(getContext(), "Kullanciya Ait "+response.body().size()+" tane pet bulunmustur. ", Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "User's "+response.body().size()+" piece pet was found. ", Toast.LENGTH_LONG).show();

                }else {
//                    changeFragments.changeBack(new KullanicilarFragment());
                    Toast.makeText(getContext(), "Pet not found for user.", Toast.LENGTH_LONG).show();
                    petEkleResimYok.setVisibility(View.VISIBLE);
                    petEkleUyariText.setVisibility(View.VISIBLE);
                    userPetListRecView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<KullaniciPetlerModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void petEkleAlert(){
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.pet_ekle_layout,null);

        final EditText petEkleNameEditText = view.findViewById(R.id.petEkleNameEditText);
        final EditText petEkleTurEditText = view.findViewById(R.id.petEkleTurEditText);
        final EditText petEkleCinsEditText = view.findViewById(R.id.petEkleCinsEditText);

        petEkleImageView = view.findViewById(R.id.petEkleImageView);
        Button petEkleButon = view.findViewById(R.id.petEkleButon);
        Button petEkleResimSecButon = view.findViewById(R.id.petEkleResimSecButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        petEkleResimSecButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });
        petEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageToString().equals("") && !petEkleNameEditText.getText().toString().equals("") &&
                        !petEkleTurEditText.getText().toString().equals("") && !petEkleCinsEditText.getText().toString().equals("") ) {

                    petEkle(musid,petEkleNameEditText.getText().toString(),petEkleTurEditText.getText().toString(),
                            petEkleCinsEditText.getText().toString(),imageToString(),alertDialog);
                    petEkleNameEditText.setText("");
                    petEkleTurEditText.setText("");
                    petEkleCinsEditText.setText("");

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==777 && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                petEkleImageView.setImageBitmap(bitmap);
                petEkleImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void petEkle(final String musid, String petismi, String pettur, String petcins, String imageString, final AlertDialog alertDialog){

        Call<PetEkle> req = ManagerAll.getInstance().petEkle(musid,petismi,pettur,petcins,imageString);
        req.enqueue(new Callback<PetEkle>() {
            @Override
            public void onResponse(Call<PetEkle> call, Response<PetEkle> response) {
                if (response.body().isTf()){
                    getPets(musid);
                    Toast.makeText(getContext(), response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }else {
                    Toast.makeText(getContext(), response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<PetEkle> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}