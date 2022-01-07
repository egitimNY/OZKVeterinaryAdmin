package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tech.halitpractice.OZKVeterinaryAdmin.Models.PetAsiTakipModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;

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


}
