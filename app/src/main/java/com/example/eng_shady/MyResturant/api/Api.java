package com.example.eng_shady.MyResturant.api;

import com.example.eng_shady.MyResturant.ContactUs;
import com.example.eng_shady.MyResturant.models.Client.Login.ClientLogin;
import com.example.eng_shady.MyResturant.models.Client.Orders.MyOrders;
import com.example.eng_shady.MyResturant.models.Client.Pass.NewPass;
import com.example.eng_shady.MyResturant.models.Client.Pass.ResetPass;
import com.example.eng_shady.MyResturant.models.Client.Register.ClientRegister;
import com.example.eng_shady.MyResturant.models.Client.ReviewAdd.CommentAdd;
import com.example.eng_shady.MyResturant.models.Client.UserNotification.Notifications;
import com.example.eng_shady.MyResturant.models.General.Category.Categories;
import com.example.eng_shady.MyResturant.models.General.Cities;
import com.example.eng_shady.MyResturant.models.General.Offers.Offers;
import com.example.eng_shady.MyResturant.models.General.Region;
import com.example.eng_shady.MyResturant.models.General.ResturantD.ResturantDetails;
import com.example.eng_shady.MyResturant.models.General.ResturantItem.ResturantItems;
import com.example.eng_shady.MyResturant.models.General.ResturantReview.Comments;
import com.example.eng_shady.MyResturant.models.Resturant.Login.ResturantLogin;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantCommission.Commission;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantDtails;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantItem.Newitem.DeleteItem.ItemsDelete;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantItem.Newitem.UpdateItem.ItemsUpdate;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.AddOffer.OfferAdd;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.DeleteOffer.OffersDelete;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.NewOffer.ResturantOffers;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantOffer.UpdateOffer.OffersUpdate;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantRegister.ResRegister;
import com.example.eng_shady.MyResturant.models.Resturant.ResturantStatus.ChangeStatus;
import com.example.eng_shady.MyResturant.models.Settings;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ENG-SHADY on 12/24/2018.
 */

public interface Api {

    /**
     * Start General Api
     **/
    @GET("settings")
    Call<Settings> getSetting();

    @FormUrlEncoded
    @POST("contact")
    Call<ContactUs> contact(
            @Field("name") String Name,
            @Field("email") String Email,
            @Field("phone") String Phone,
            @Field("type") String type,
            @Field("content") String content);

    @GET("cities")
    Call<Cities> GetCities();

    @GET("regions")
    Call<Region> getListRegion(@Query("city_id") int id);

    @GET("categories")
    Call<Categories> getCatagory();

    @GET("offers")
    Call<Offers> Getoffer();

    @GET("categories")
    Call<Categories> getcategories();

    @GET("restaurants")
    Call<ResturantDtails> GetResturant();

    @GET("restaurant")
    Call<ResturantDetails> GetResDetails(@Query("restaurant_id") int resturantId);

    @GET("items")
    Call<ResturantItems> GetResItems(@Query("restaurant_id") int restaurant_id);

    @GET("restaurant/reviews")
    Call<Comments> GetReview(@Query("api_token") String ApiToken,

                             @Query("restaurant_id") int restaurant_id);

    /**
     End General Api
     **/


    /**
     * Start Client Api
     **/

    @FormUrlEncoded
    @POST("client/register")
    Call<ClientRegister> creatUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String confirm_password,
            @Field("phone") String phone,
            @Field("address") String Address,
            @Field("region_id") String id

    );

    @FormUrlEncoded
    @POST("client/login")
    Call<ClientLogin> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("client/reset-password")
    Call<ResetPass> resetpass(@Field("email") String email);

    @FormUrlEncoded
    @POST("client/new-password")
    Call<NewPass> newPass(@Field("password") String password,
                          @Field("password_confirmation") String password_confirmation,
                          @Field("code") String code);

    @FormUrlEncoded
    @POST("client/restaurant/review")
    Call<CommentAdd> ReviewAdd(@Field("rate") String rate,
                               @Field("comment") String comment,
                               @Field("restaurant_id") int ResId,
                               @Field("api_token") String ApiToken);


    @GET("client/notifications")
    Call<Notifications> getNotification (@Query("api_token") String ApiToken);

    @GET("client/my-orders")
    Call<MyOrders> getOrders (@Query("api_token") String ApiToken,
                              @Query("state") String state,
                              @Query("page") int page);

    /**
     End Client Api
     **/

    /**
     * Start Resturant Api
     **/

    @GET("restaurant/commissions")
    Call<Commission> GetCommission(@Query("api_token") String ApiToken);

    @FormUrlEncoded
    @POST("restaurant/login")
    Call<ResturantLogin> getResLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("restaurant/register")
    Call<ResRegister> restaurantRegister(@Part("name") RequestBody name,
                                         @Part("email") RequestBody email,
                                         @Part("password") RequestBody password,
                                         @Part("password_confirmation") RequestBody password_confirmation,
                                         @Part("phone") RequestBody phone,
                                         @Part("address") RequestBody address,
                                         @Part("whatsapp") RequestBody whatsapp,
                                         @Part("region_id") RequestBody region_id,
                                         @Part("categories[]") List<RequestBody> categories,
                                         @Part("delivery_period") RequestBody delivery_period,
                                         @Part("delivery_cost") RequestBody delivery_cost,
                                         @Part("minimum_charger") RequestBody minimum_charger,
                                         @Part MultipartBody.Part File,
                                         @Part("availability") RequestBody availability);


    @FormUrlEncoded
    @POST("restaurant/change-state")
    Call<ChangeStatus> getChange(@Field("state") String Status,
                                 @Field("api_token") String ApiToken);

    @Multipart
    @POST("restaurant/new-item")
    Call<ResturantItems> AddItem(@Part("name") RequestBody name,
                                 @Part("description") RequestBody description,
                                 @Part("price") RequestBody price,
                                 @Part("preparing_time") RequestBody preparing_time,
                                 @Part MultipartBody.Part File,
                                 @Part("api_token") RequestBody api_token);

    @FormUrlEncoded
    @POST("restaurant/delete-item?")
    Call<ItemsDelete> getItemDelete(@Field("item_id") int OfferId,
                                    @Field("api_token") String api_token);

    @Multipart
    @POST("restaurant/update-item?")
    Call<ItemsUpdate> getItemUpdate(@Part("name") RequestBody name,
                                    @Part("description") RequestBody description,
                                    @Part("price") RequestBody price,
                                    @Part("preparing_time") RequestBody preparing_time,
                                    @Part MultipartBody.Part File,
                                    @Part("api_token") RequestBody api_token,
                                    @Part("item_id") int item_id );


    @Multipart
    @POST("restaurant/new-offer")
    Call<OfferAdd> AddOffer(@Part("description") RequestBody description,
                            @Part("price") RequestBody price,
                            @Part("starting_at") RequestBody startingAt,
                            @Part("name") RequestBody name,
                            @Part MultipartBody.Part File,
                            @Part("ending_at") RequestBody endingAt,
                            @Part("api_token") RequestBody api_token);


    @Multipart
    @POST("restaurant/update-offer?")
    Call<OffersUpdate> getUpadteOffer(@Part("description") RequestBody description,
                                      @Part("price") RequestBody price,
                                      @Part("starting_at") RequestBody startingAt,
                                      @Part("name") RequestBody name,
                                      @Part MultipartBody.Part File,
                                      @Part("ending_at") RequestBody endingAt,
                                      @Part("offer_id") RequestBody OfferId,
                                      @Part("api_token") RequestBody api_token);

    @GET("restaurant/my-offers")
    Call<ResturantOffers> GetOffers(@Query("api_token") String ApiToken);


    @FormUrlEncoded
    @POST("restaurant/delete-offer?")
    Call<OffersDelete> getDelete(@Field("offer_id") int OfferId,
                                 @Field("api_token") String api_token);

    /**
     End Resturant Api
     **/
}
