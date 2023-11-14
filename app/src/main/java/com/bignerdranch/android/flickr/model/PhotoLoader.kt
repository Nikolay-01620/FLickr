package com.bignerdranch.android.flickr.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.flickr.repository.FlickrRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Класс для управления загрузкой фотографий из Flickr и их обработкой
// перед отображением в UI
class PhotoLoader(private val flickrRepository: FlickrRepository) : ViewModel() {

    //место,в котором хранятся данные о URL адресах фотографий
    // и они могут быть изменены внутри класса
    private val _photoUrls = MutableLiveData<List<String>>()

    /*переменная используется для отслеживания уникальных фотографий
    которые были загружены и обработаны в приложении
    Этот список позволяет избегать повторного добавления одной и
    той же фотографии в список _photoUrls
    который используется для отображения фотографий в пользовательском интерфейсе*/
    private val uniquePhotosList = mutableListOf<PhotoItem>()

    /*свойство,которое предоставляет доступ к данным _photoUrls в виде LiveData
    Это делается для того,чтобы другие части приложения могли наблюдать за изменениями
    в списке URL-адресов фотографий и реагировать на них
    не имея прямого доступа к изменяемым данным*/
    val photoUrls: LiveData<List<String>>

        /*доступ только для чтения к данным нужен для сохранения контроля над их изменениями
        и обеспечении более безопасной и предсказуемой работы с ними извне класса*/
        get() = _photoUrls

    //функция для загрузки фото
    fun loadPhotosFromFlickr() {
        /* Здесь используется корутина для запуска асинхронной операции
         в фоновом потоке,предназначенном для сетевых запросов(.IO)
        ViewModelScope - область видимости,связанная с жизненным циклом ViewModel
        что означает автоматическую отмену операции,при уничтожении ViewModel */
        viewModelScope.launch(Dispatchers.IO) {

            /*если запрос завершится неудачно и возникнет исключение
            (например,из за сетевой ошибки или некорректного ответа от сервера)
            то это исключение будет поймано в блоке catch
            и метод handleLoadPhotosError(e) будет вызван для обработки ошибки*/
            try {
                fetchPhotos()
            } catch (e: Exception) {
                handleLoadPhotosError(e)
            }
        }
    }

    /*функция,выполняющая запроса к серверу
    с целью получения списка фотографий*/
    private fun fetchPhotos() {
        flickrRepository.getPhotos(object : Callback<PhotoResponse> {

            /*проверка успешности ответа из сервера
            Если ответ успешен,то это означает
            что запрос завершился успешно
            и в ответе содержатся данные о фотографиях */
            override fun onResponse(call: Call<PhotoResponse>, response: Response<PhotoResponse>) {
                if (response.isSuccessful) {
                    val photoResponse = response.body()

                    /*фильтрация фото для уникальности и
                    добавления их в список uniquePhotosList */
                    processLoadedPhotos(photoResponse?.items)
                } else {
                    handleLoadPhotosError(Exception("Request failed with code ${response.code()}"))
                }
            }

            //в случае неуспешности ответа,ошибка обработается здесь
            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                handleLoadPhotosError(t)
            }
        })
    }

    // функция фильтрации списка для уникальных фото
    private fun processLoadedPhotos(photoItems: List<PhotoItem>?) {

        //Если photoItems равен null              filter применяется к списку photoItems
        // то используется пустой список          и оставляет только те элементы
        // Это делается,чтобы избежать            для которых isPhotoItemInList(it) возвращает true
        // NullPointerException                   То есть,фильтр оставляет только те фотографии
        // при работе с photoItems                которых нет в списке uniquePhotosList
        val uniquePhotoItems = photoItems.orEmpty().filter { !isPhotoItemInList(it) }

        /*Это условие проверяет,есть ли какие-либо уникальные фотографии после фильтрации
        Если uniquePhotoItems не пустой (то есть,есть уникальные фотографии)
        то выполняется следующий код внутри условия*/
        if (uniquePhotoItems.isNotEmpty()) {

            // генерируется случайный индекс из диапазона индексов уникальных фотографий
            // Этот код позволяет случайным образом выбирать фотографии из списка uniquePhotoItems
            // чтобы показать разнообразие фотографий
            val randomIndex = (uniquePhotoItems.indices).random()
            val randomPhoto = uniquePhotoItems[randomIndex]

            /*Случайная фотография randomPhoto добавляется в
            список uniquePhotosList
            чтобы в будущем избежать повторного добавления той же фотографии*/
            uniquePhotosList.add(randomPhoto)

            /*Здесь обновляется LiveData _photoUrls
            используя метод postValue
            В LiveData теперь будет содержаться список URL-адресов фотографий
            полученных из uniquePhotosList */
            _photoUrls.postValue(uniquePhotosList.map { it.media.m })
                                                 /* map используется для преобразования каждой фотографии
                                                 PhotoItem в URL-адрес media.m(переменная с типом дата-класса Media в PhotoResponse.kt) и создания
                                                 списка URL-адресов. Этот список URL-адресов будет доступен для наблюдения извне и
                                                 обновит пользовательский интерфейс в соответствии с новыми данными.*/
        }
    }

    private fun handleLoadPhotosError(exception: Throwable) {
        /* Обработка ошибок при загрузке фотографий
        будет показана в трассеровке стека*/
        exception.printStackTrace()
    }

    private fun isPhotoItemInList(photoItem: PhotoItem): Boolean {
        // Проверяем,есть ли фото с таким же заголовком уже в списке
        return uniquePhotosList.any { it.title == photoItem.title }
    }
}


