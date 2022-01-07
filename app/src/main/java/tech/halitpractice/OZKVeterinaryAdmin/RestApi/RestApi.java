package tech.halitpractice.OZKVeterinaryAdmin.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tech.halitpractice.OZKVeterinaryAdmin.Models.AsiOnaylaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.CevaplaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaEkleModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaSilModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.PetAsiTakipModel;
import tech.halitpractice.OZKVeterinaryAdmin.Models.SoruModel;

public interface RestApi {

    @GET("/veteriner/kampanyaidli.php")
    Call<List<KampanyaModel>> getKampanya();

    @FormUrlEncoded
    @POST("/veteriner/kampanyaekle.php")
    Call<KampanyaEkleModel> addKampanya(@Field("baslik") String baslik, @Field("text") String text, @Field("resim") String resim);

    @FormUrlEncoded
    @POST("/veteriner/kampanyasil.php")
    Call<KampanyaSilModel> kampanyaSil(@Field("id") String kamid );

    @FormUrlEncoded
    @POST("/veteriner/veterinerAdminAsitakip.php")
    Call<List<PetAsiTakipModel>> getPetAsiTakip(@Field("tarih") String tarih );

    @FormUrlEncoded
    @POST("/veteriner/veterinerAdminAsiOnayla.php")
    Call<AsiOnaylaModel> asiOnayla(@Field("id") String petid );

    @FormUrlEncoded
    @POST("/veteriner/veterinerAdminAsiIptal.php")
    Call<AsiOnaylaModel> asiIptal(@Field("id") String petid );

    @GET("/veteriner/veterinerAdminSorular.php")
    Call<List<SoruModel>> getSoru();

    @FormUrlEncoded
    @POST("/veteriner/veterinerAdminCevapla.php")
    Call<CevaplaModel> cevapla(@Field("musid") String musid, @Field("soruid") String soruid, @Field("text") String text);

}
