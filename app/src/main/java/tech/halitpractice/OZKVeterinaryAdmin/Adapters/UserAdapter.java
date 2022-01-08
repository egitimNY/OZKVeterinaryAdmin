package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.halitpractice.OZKVeterinaryAdmin.Models.KullanicilarModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    List<KullanicilarModel> list;
    Context context;
    Activity activity;

    public UserAdapter(List<KullanicilarModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
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
        holder.userPetEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView kullaniciNameText;
        Button userPetEkle,userAramaYap;
        CardView userCardView;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            kullaniciNameText = itemView.findViewById(R.id.kullaniciNameText);
            userPetEkle = itemView.findViewById(R.id.userPetEkle);
            userAramaYap = itemView.findViewById(R.id.userAramaYap);
            userCardView = itemView.findViewById(R.id.userCardView);

        }
    }

    public void ara(String numara){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }



}
