package com.bignerdranch.android.flickr.model


// Классы,представляющие структуру данных фотографий и ответа от сервера

// представляет ответ от сервера Flickr,который содержит список элементов (фотографий)
//Каждый элемент  списка будет являться объектом PhotoItem
data class PhotoResponse(val items: List<PhotoItem>)

/* представляет отдельную фотографию из ответа сервера Flickr
title - поле,содержащее название фотографии
media - поле,содержащее объект Media,который представляет медиа-содержимое фотографии */
data class PhotoItem(val title: String, val media: Media)

data class Media(val m: String)





