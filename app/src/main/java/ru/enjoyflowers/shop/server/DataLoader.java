package ru.enjoyflowers.shop.server;


import android.os.Debug;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.enjoy.server.data.Category;
import ru.enjoy.server.data.MainCategory;
import ru.enjoy.server.data.Root;

/**
 * Created by dm on 21.05.16.
 */
public class DataLoader {
    private Root root;
    public void load(){
        Gson gson = new GsonBuilder().create();
        URL url;
        InputStream is;
        try {
            url = new URL("https://raw.githubusercontent.com/dmbel/Enjoy/master/app/src/test/json.gz");
        } catch (MalformedURLException e) {return;}
        try {
            is = url.openStream();
        } catch (IOException e) {
            return;
        }
        try {
            Log.d("enjoytest", String.format("Память до : %d", Debug.getNativeHeapFreeSize()));
            Root root = gson.fromJson(new InputStreamReader(new GZIPInputStream(is)), Root.class);
            Log.d("enjoytest", String.format("Память после : %d", Debug.getNativeHeapFreeSize()));
            Log.d("enjoytest", String.format("menuItems.length %d",root.menuItems.length) );
            for(Category cat : root.categories) {
                Log.d("enjoytest", String.format("Категория : %-30s id : %d", cat.name, cat.id));
            }
            Log.d("enjoytest", "-------------------------------------------------");
            for(MainCategory cat : root.mainCategories){
                Log.d("enjoytest", String.format("Main category %s",cat.categoryId) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
