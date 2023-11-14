package com.bignerdranch.android.flickr.repository

import com.bignerdranch.android.flickr.service.FlickrService
import com.bignerdranch.android.flickr.model.PhotoResponse
import retrofit2.Callback

// Использую репозиторий для разделения ответственности между компонентами приложения
// и улучшения модульности кода,а так же для поддерживаемости проекта

//репозиторий использует FlickrService для выполнения запросов к серверу
class FlickrRepository(private val flickrService: FlickrService) {

    //функция,выполняющая запрос к серверу для получения фото
    // Она принимает callback,который является объектом Callback<PhotoResponse>(дата-класс)
    // Этот callback будет использоваться для обработки ответа от сервера после выполнения запроса
    fun getPhotos(callback: Callback<PhotoResponse>) {

        //асинхронная отправка запроса с передачей результата
        flickrService.getPhotos().enqueue(callback)
    }

}


