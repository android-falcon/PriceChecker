package com.falconssoft.pricechecker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiPrice {
    @GET("/GetJRDITEMPRICE")
    Call<List<ItemInfo>> gaItemInfo(@Query("ITEMCODE") String itemNo,@Query("CONO") String co);
}
