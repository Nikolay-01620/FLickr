package com.bignerdranch.android.flickr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.Flickr.R
import com.bumptech.glide.Glide

// Адаптер для отображения фотографий

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    //пустой изменяемый список по умолчанию
    // чтобы в него можно было добавлять фото
    var photoUrls: List<String> = mutableListOf()

    //Использование пользовательского ViewHolder помогает увеличить производительность при работе
    // с RecyclerView или другими компонентами списка в Android
    // меньшая время обработки и улучшая плавность прокрутки
    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        //привязка данных к ViewHolder
        fun bind(photoUrl: String) {

            //В методе используется библиотека Glide
            // для загрузки и отображения изображения по указанному URL-адресу
            Glide.with(itemView)
                .load(photoUrl)
                .centerCrop() // Масштабирование с сохранением соотношения сторон
                .into(imageView)
        }
    }

    // Создание нового объекта ViewHolder для отображения элемента списка
    // функция создает новый экземпляр класса PhotoViewHolder
    // который является пользовательским ViewHolder для элементов списка
    // В этой функции также происходит надувание макета элемента списка из ресурсов и создание объекта PhotoViewHolder
    // который связывается с этим макетом
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    // Привязка данных к ViewHolder для отображения элемента списка в конкретной позиции
    // Функция получает position и объект PhotoViewHolder,который был создан в onCreateViewHolder
    // Затем она вызывает метод bind(photoUrl) у PhotoViewHolder
    // передавая URL фотографии (photoUrl) для загрузки и отображения в элементе списка
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoUrl = photoUrls[position]
        holder.bind(photoUrl)
    }

    //функция,определяющая сколько элементов будет отображаться в RecyclerView
    override fun getItemCount(): Int {
        return photoUrls.size
    }

}















