package tech.halitpractice.OZKVeterinaryAdmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.halitpractice.OZKVeterinaryAdmin.Models.AsiEkleModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullaniciPetlerModel;
import tech.halitpractice.OZKVeterinaryAdmin.R;
import tech.halitpractice.OZKVeterinaryAdmin.RestApi.ManagerAll;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.ChangeFragments;
import tech.halitpractice.OZKVeterinaryAdmin.Utils.Warnings;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder>{

    List<KullaniciPetlerModel> list;
    Context context;
    Activity activity;
    ChangeFragments changeFragments;
    String musid;
    String tarih = "",formatliTarih = "";

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

        Picasso.get().load(list.get(position).getPetresim()).resize(200,200).into(holder.petImage);

        holder.petAsiEkleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAsiEkleAlert(list.get(position).getPetid().toString());
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView petBilgiText,petNameText;
        LinearLayout petAsiEkleLayout;
        ImageView petImage;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            petBilgiText = itemView.findViewById(R.id.petBilgiText);
            petNameText = itemView.findViewById(R.id.petNameText);
            petImage = itemView.findViewById(R.id.petImage);
            petAsiEkleLayout = itemView.findViewById(R.id.petAsiEkleLayout);

        }
    }

    public void addAsiEkleAlert(final String petId){
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asi_ekle_layout ,null);

        CalendarView calendarView = view.findViewById(R.id.asiEkleTakvim);
        final EditText asiEkleAsiName = view.findViewById(R.id.asiEkleAsiName);
        Button asiEkleButon = view.findViewById(R.id.asiEkleButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
//          public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

//                tarih = dayOfMonth+"/"+(month+1)+"/"+year;
//                tarih = month+1+"/"+(dayOfMonth)+"/"+year;
//                Toast.makeText(context, tarih, Toast.LENGTH_SHORT).show();




//                  tarih = i1+1+"/"+(i2)+"/"+i;

                    tarih = i2+"/"+(i1+1)+"/"+i;
//                    Toast.makeText(context, tarih, Toast.LENGTH_SHORT).show();

                try {
                    Date date = inputFormat.parse(tarih);
                    formatliTarih = format.format(date).toString();
                } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        });
        asiEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formatliTarih.equals("") && !asiEkleAsiName.getText().toString().equals("")){
                    addAsi(musid,petId,asiEkleAsiName.getText().toString(),formatliTarih,alertDialog);
                }else {
                    Toast.makeText(context, "Tarih seciniz veya asi ismi giriniz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.show();

    }

    public void addAsi(String musid, String petid, String asiName, String tarih, final AlertDialog alertDialog){

        Call<AsiEkleModel> req = ManagerAll.getInstance().addAsi(musid,petid,asiName,tarih);
        req.enqueue(new Callback<AsiEkleModel>() {
            @Override
            public void onResponse(Call<AsiEkleModel> call, Response<AsiEkleModel> response) {
                alertDialog.cancel();
                Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<AsiEkleModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();

            }
        });

    }




}












