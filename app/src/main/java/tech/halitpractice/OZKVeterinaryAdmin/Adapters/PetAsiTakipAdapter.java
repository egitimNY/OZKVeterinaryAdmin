package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Models.AsiOnaylaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.PetAsiTakipModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class PetAsiTakipAdapter extends RecyclerView.Adapter<PetAsiTakipAdapter.ViewHolder>{

    List<PetAsiTakipModel> list;
    Context context;
    Activity activity;

    public PetAsiTakipAdapter(List<PetAsiTakipModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asi_takip_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.asiTakipPetName.setText(list.get(position).getAsiisim());
        holder.asiTakipBilgiText.setText(list.get(position).getKadi()+ " isimli kullanicinin "+ list.get(position).getPetisim()
        + " isimli petinin "+list.get(position).getAsiisim()+ " asisi yapilacaktir.");
        Picasso.get().load(list.get(position).getPetresim()).resize(200,180).into(holder.asiTakipImage);
        holder.asiTakipAraButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon().toString());
            }
        });

        holder.asiTakipOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asiOnayla(list.get(position).getAsiId().toString(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView asiTakipPetName,asiTakipBilgiText;
        ImageView asiTakipImage,asiTakipOkButton,asiTakipCancelButon,asiTakipAraButon;
//        CardView kampanyaCardView;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            asiTakipPetName = itemView.findViewById(R.id.asiTakipPetName);
            asiTakipBilgiText = itemView.findViewById(R.id.asiTakipBilgiText);
            asiTakipImage = itemView.findViewById(R.id.asiTakipImage);
            asiTakipOkButton = itemView.findViewById(R.id.asiTakipOkButton);
            asiTakipCancelButon = itemView.findViewById(R.id.asiTakipCancelButon);
            asiTakipAraButon = itemView.findViewById(R.id.asiTakipAraButon);

        }
    }

    public void deleteToList(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void ara(String numara){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }

    public void asiOnayla(String id, final int position){
        Call<AsiOnaylaModel> req = ManagerAll.getInstance().asiOnayla(id);
        req.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                deleteToList(position);
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
