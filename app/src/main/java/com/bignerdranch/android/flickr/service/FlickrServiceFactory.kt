package com.bignerdranch.android.flickr.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// способ создания экземпляра FlickrService с
// предварительно настроенными параметрами (URL и конвертер)
// для большей структурированности проекта и разделения ответственности

private const val BASE_URL = "https://api.flickr.com/"

class FlickrServiceFactory {

    //функция,отвечающая за создание и возврат экземпляра FlickrService
    fun createFlickrService(): FlickrService {

        //создаем экземпляр Retrofit
        val retrofit = Retrofit.Builder()

            //Установка базового URL-адреса
            .baseUrl(BASE_URL)

            //конвертация из JSON в Gson(преобразование данных,полученных с сервера в объекты для дата-классов)
            .addConverterFactory(GsonConverterFactory.create())

            //Завершение настройки Retrofit и создание экземпляра
            .build()

        //Создание и возврат экземпляра FlickrService, который будет использоваться для выполнения запросов к сайту
        return retrofit.create(FlickrService::class.java)
    }
}