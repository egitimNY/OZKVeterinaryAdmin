package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Fragments.KullaniciPetlerFragment;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullaniciSilModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullanicilarModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    List<KullanicilarModel> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;

    public UserAdapter(List<KullanicilarModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kullanici_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.kullaniciNameText.setText(list.get(position).getKadi().toString());
        holder.userAramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon().toString());

            }
        });
        holder.userPetlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.changeWithParameters(new KullaniciPetlerFragment(),list.get(position).getId().toString());
            }
        });
        holder.kullanicilarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullaniciSilAlert(position);
            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView kullaniciNameText;
        Button userPetlerButton,userAramaYap;
        CardView userCardView;
        LinearLayout kullanicilarLayout;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            kullaniciNameText = itemView.findViewById(R.id.kullaniciNameText);
            userPetlerButton = itemView.findViewById(R.id.userPetList);
            userAramaYap = itemView.findViewById(R.id.userAramaYap);
            userCardView = itemView.findViewById(R.id.userCardView);
            kullanicilarLayout = itemView.findViewById(R.id.kullanicilarLayout);

        }
    }

    public void ara(String numara){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }

    public void kullaniciSilAlert(final int position){
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kullanici_sil_layout,null);
        Button kullaniciSilButon = view.findViewById(R.id.kullaniciSilButon);
        Button kullaniciSilIptalButon = view.findViewById(R.id.kullaniciSilIptalButon);


        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        kullaniciSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullaniciSil(list.get(position).getId().toString(),position);
                alertDialog.cancel();
            }
        });
        kullaniciSilIptalButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();

    }
    public void kullaniciSil(String id, final int position){

        Call<KullaniciSilModel> req = ManagerAll.getInstance().userDelete(id);
        req.enqueue(new Callback<KullaniciSilModel>() {
            @Override
            public void onResponse(Call<KullaniciSilModel> call, Response<KullaniciSilModel> response) {

                if (response.body().isTf()){
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    deleteToList(position);

                }else {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<KullaniciSilModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void deleteToList(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
