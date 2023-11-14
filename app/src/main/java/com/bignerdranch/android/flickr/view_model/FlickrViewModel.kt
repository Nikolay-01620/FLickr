package com.bignerdranch.android.flickr.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.flickr.model.PhotoLoader
import com.bignerdranch.android.flickr.repository.FlickrRepository
import com.bignerdranch.android.flickr.service.FlickrServiceFactory

// Вью-модель,связывающая пользовательский интерфейс и PhotoLoader(загрузка и обработка фото)

class FlickrViewModel : ViewModel() {

    private val flickrServiceFactory = FlickrServiceFactory()

    //Создание экземпляра FlickrRepository,который использует FlickrServiceFactory для создания
    // FlickrService и предоставляет репозиторий для взаимодействия с данными из Flickr
    private val flickrRepository = FlickrRepository(flickrServiceFactory.createFlickrService())

    private val photoLoader: PhotoLoader = PhotoLoader(flickrRepository)

    //Создание LiveData которая будет предоставлять список URL-адресов фотографий из photoLoader
    // Это позволяет связать данные с UI и обновлять его при изменении URL адресов фотографий
    val photoUrls: LiveData<List<String>> = photoLoader.photoUrls

    //запуск функции загрузки
    fun loadPhotosFromFlickr() {
        photoLoader.loadPhotosFromFlickr()
    }
}











