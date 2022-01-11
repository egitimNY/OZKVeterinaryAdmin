package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tech.halitpractice.OZKVeterinaryAdmin.Models.KullaniciPetlerModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder>{

    List<KullaniciPetlerModel> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;
    String musid;

    public PetAdapter(List<KullaniciPetlerModel> list, Context context, Activity activity,String musid) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.musid = musid;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_pet_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.petNameText.setText(list.get(position).getPetisim().toString());
        holder.petBilgiText.setText("Bu petin turu "+list.get(position).getPettur().toString()
                +" cinsi "+list.get(position).getPetcins().toString()+"dir. "+list.get(position).getPetisim().toString()
        +" isimli bu pete asi eklemek icin tiklayin ");

        holder.petCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        Picasso.get().load(list.get(position).getPetresim()).resize(200,200).into(holder.petImage);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView petBilgiText,petNameText;
        CardView petCardView;
        ImageView petImage;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            petBilgiText = itemView.findViewById(R.id.petBilgiText);
            petNameText = itemView.findViewById(R.id.petNameText);
            petImage = itemView.findViewById(R.id.petImage);
            petCardView = itemView.findViewById(R.id.petCardView);

        }
    }



}
