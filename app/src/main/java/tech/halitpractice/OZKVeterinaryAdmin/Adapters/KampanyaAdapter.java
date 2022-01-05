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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaSilModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

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

                kampanyaSilAlert(position);

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

    public void kampanyaSilAlert(final int position){
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
                kampanyaSil(list.get(position).getId().toString(),position);
                alertDialog.cancel();
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
    public void kampanyaSil(String id, final int position)
    {
        Call<KampanyaSilModel> req = ManagerAll.getInstance().kampanyaSil(id);
        req.enqueue(new Callback<KampanyaSilModel>() {
            @Override
            public void onResponse(Call<KampanyaSilModel> call, Response<KampanyaSilModel> response) {
                if (response.body().isTf())
                {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                    deleteToList(position);
                }else {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<KampanyaSilModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void deleteToList(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
