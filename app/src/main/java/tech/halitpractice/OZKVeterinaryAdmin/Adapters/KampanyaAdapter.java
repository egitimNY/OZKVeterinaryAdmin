package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder>{

    List<KampanyaModel> list;
    Context context;
    Activity activity;

    public KampanyaAdapter(List<KampanyaModel> list, Context context,Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kampanya_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.kampanyaBaslikText.setText(list.get(position).getBaslik().toString());
        holder.kampanyaText.setText(list.get(position).getText().toString());
//        Picasso.get().load(list.get(position).getResim()).into(holder.kampanyaImageView);
        Picasso.get().load(list.get(position).getResim()).resize(200,180).into(holder.kampanyaImageView);
        holder.kampanyaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addKampanyaAlert();

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView kampanyaBaslikText,kampanyaText;
        ImageView kampanyaImageView;
        CardView kampanyaCardView;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            kampanyaText = itemView.findViewById(R.id.kampanyaText);
            kampanyaBaslikText = itemView.findViewById(R.id.kampanyaBaslikText);
            kampanyaImageView = itemView.findViewById(R.id.kampanyaImageView);
            kampanyaCardView = itemView.findViewById(R.id.kampanyaCardView);

        }
    }

    public void addKampanyaAlert(){
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanya_sil_layout,null);

        Button kampanyaSilTamam = view.findViewById(R.id.kampanyaSilTamam);
        Button kampanyaSilIptal = view.findViewById(R.id.kampanyaSilIptal);


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        kampanyaSilTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        kampanyaSilIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();

    }
}
