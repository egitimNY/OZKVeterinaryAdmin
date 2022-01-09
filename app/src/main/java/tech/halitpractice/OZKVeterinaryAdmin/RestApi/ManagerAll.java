package tech.halitpractice.OZKVeterinaryAdmin.RestApi;

import java.util.List;

import retrofit2.Call;
import tech.halitpractice.OZKVeterinaryAdmin.Models.AsiOnaylaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.CevaplaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaEkleModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaSilModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullaniciPetlerModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KullanicilarModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.PetAsiTakipModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.SoruModel;

public class ManagerAll extends BaseManager{

    private  static ManagerAll ourInstance = new ManagerAll();

    public  static synchronized ManagerAll getInstance()
    {
        return  ourInstance;
    }

    public Call<List<KampanyaModel>> getKampanya() {
        Call<List<KampanyaModel>> x = getRestApi().getKampanya();
        return  x ;
    }

    public Call<KampanyaEkleModel> addKampanya(String baslik,String icerik, String imageString) {
        Call<KampanyaEkleModel> x = getRestApi().addKampanya(baslik,icerik,imageString);
        return  x ;
    }

    public Call<KampanyaSilModel> kampanyaSil(String id ) {
        Call<KampanyaSilModel> x = getRestApi().kampanyaSil(id);
        return  x ;
    }

    public Call<List<PetAsiTakipModel>> getAsiPet(String tarih ) {
        Call<List<PetAsiTakipModel>> x = getRestApi().getPetAsiTakip(tarih);
        return  x ;
    }

    public Call<AsiOnaylaModel> asiOnayla(String id ) {
        Call<AsiOnaylaModel> x = getRestApi().asiOnayla(id);
        return  x ;
    }

    public Call<AsiOnaylaModel> asiIptal(String id ) {
        Call<AsiOnaylaModel> x = getRestApi().asiIptal(id);
        return  x ;
    }

    public Call<List<SoruModel>> getSoru() {
        Call<List<SoruModel>> x = getRestApi().getSoru();
        return  x ;
    }

    public Call<CevaplaModel> cevapla(String musid, String soruid, String text) {
        Call<CevaplaModel> x = getRestApi().cevapla(musid,soruid,text);
        return  x ;
    }

    public Call<List<KullanicilarModel>> getKullanicilar() {
        Call<List<KullanicilarModel>> x = getRestApi().getKullanicilar();
        return  x ;
    }

    public Call<List<KullaniciPetlerModel>> getPets(String id ) {
        Call<List<KullaniciPetlerModel>> x = getRestApi().getPets(id);
        return  x ;
    }

}
