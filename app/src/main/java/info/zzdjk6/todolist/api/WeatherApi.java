package info.zzdjk6.todolist.api;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherApi {

    public static class Weather {
        public String temperature;
        public String location;
        public String condition;
        public String time;
    }

    public static Observable<Weather> fetchWeather() {
        Observable<Weather> o = Observable
                .create(emitter -> {
                    try {
                        String url = "http://52.62.86.120/api/weather/wellington";
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        OkHttpClient client = new OkHttpClient();
                        Response response = client.newCall(request).execute();
                        String json = response.body().string();

                        JSONObject jsonObject = new JSONObject(json);
                        jsonObject = jsonObject.getJSONObject("weather");
                        Weather weather = new Weather();
                        weather.location = jsonObject.optString("location");
                        weather.temperature = jsonObject.optString("temperature");
                        weather.condition = jsonObject.optString("condition");
                        weather.time = jsonObject.optString("time");
                        emitter.onNext(weather);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });
        return o.subscribeOn(Schedulers.newThread());
    }
}
