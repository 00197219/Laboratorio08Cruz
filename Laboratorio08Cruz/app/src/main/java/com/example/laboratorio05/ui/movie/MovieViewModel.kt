package com.example.laboratorio05.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.laboratorio05.MovieReviewerApplication
import com.example.laboratorio05.data.models.MovieModel
import com.example.laboratorio05.data.qualification
import com.example.laboratorio05.repository.MovieRepository

class MovieViewModel(private val repository: MovieRepository):ViewModel() {
    var name : MutableLiveData <String> = MutableLiveData("")
    var category = MutableLiveData("")
    var description = MutableLiveData("")
    var calification = MutableLiveData("")
    var status = MutableLiveData("")
    fun getMovies() = repository.getMovies()
    fun addMovie(movie: MovieModel) = repository.addMovie(movie)
    private fun validateData (): Boolean{
        when {
            name.value.isNullOrEmpty() -> return false
            category.value.isNullOrEmpty() -> return false
            description.value.isNullOrEmpty() -> return false
            calification.value.isNullOrEmpty() -> return false


        }
    return true
    }

    fun createMovie(){

            if(!validateData()){
                status.value= WRONG_DATA
                return
            }

        var newMovie = MovieModel(
            name.value.toString(),
            category.value.toString(),
            description.value.toString(),
            calification.value.toString(),
        )
       addMovie(newMovie)

    }

    fun clearStatus() {
        status.value= INACTIVE
    }

    fun clearData(){
        name.value = ""
        category.value = ""
        description.value = ""
        calification.value = ""
    }


    fun setSelectedMovie(movie: MovieModel){
        name.value = movie.name
        category.value = movie.category
        description.value = movie.description
        calification.value = movie.qualification
    }

companion object{
    val Factory = viewModelFactory {
        initializer {
            val app = this [APPLICATION_KEY] as MovieReviewerApplication
            MovieViewModel(app.movieRepository)
        }
    }
    const val MOVIE_CTREATTED = "movie created"
    const val WRONG_DATA = "wrong data"
    const val INACTIVE = ""
}
}