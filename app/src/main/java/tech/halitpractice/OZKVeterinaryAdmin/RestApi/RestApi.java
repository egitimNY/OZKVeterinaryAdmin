package tech.halitpractice.OZKVeterinaryAdmin.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import tech.halitpractice.OZKVeterinaryAdmin.Models.KampanyaModel;

public interface RestApi {

    @GET("/veteriner/kampanyaidli.php")
    Call<List<KampanyaModel>> getKampanya();

}
