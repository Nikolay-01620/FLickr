package com.bignerdranch.android.flickr.service

import com.bignerdranch.android.flickr.model.PhotoResponse
import retrofit2.http.GET


// интерфейс Retrofit для взаимодействия с API Flickr
// определяющий метод,используемый для
// отправки GET-запроса к API Flickr и получения данных о фотографиях

interface FlickrService {

    //аннотация указывающая,что следующий метод должен выполнять HTTP GET-запрос к указанному URL
    // представляющий собой конечную точку API Flickr для получения списка фотографий в формате JSON
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")

   /* метод,выполняющий запрос на получение списка фотографий
      и возвращающий объект Call<PhotoResponse>
      который представляет асинхронный запрос
      и будет использоваться Retrofit для отправки запроса и получения ответа */
      fun getPhotos(): retrofit2.Call<PhotoResponse>
}






