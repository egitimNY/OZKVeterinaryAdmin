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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.halitpractice.OZKVeterinaryAdmin.Models.SoruModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;

public class VeterinerSoruAdapter extends RecyclerView.Adapter<VeterinerSoruAdapter.ViewHolder>{

    List<SoruModel> list;
    Context context;
    Activity activity;

    public VeterinerSoruAdapter(List<SoruModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sorular_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.soruKullaniciText.setText(list.get(position).getKadi().toString());
        holder.soruSoruText.setText(list.get(position).getSoru().toString());
        holder.soruAramaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView soruKullaniciText,soruSoruText;
        ImageView soruCevaplaButon,soruAramaButon;
//        CardView kampanyaCardView;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            soruKullaniciText = itemView.findViewById(R.id.soruKullaniciText);
            soruSoruText = itemView.findViewById(R.id.soruSoruText);
            soruCevaplaButon = itemView.findViewById(R.id.soruCevaplaButon);
            soruAramaButon = itemView.findViewById(R.id.soruAramaButon);


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



}
