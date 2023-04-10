package com.example.detail.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.destination.DetailDestination.ID_PARAM
import com.example.navigation.navigation.Navigator
import com.example.shared.data.databases.model.UserModel
import com.example.shared.data.network.model.DetailModel
import com.example.shared.domain.GetDetailUserUseCase
import com.example.shared.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle,
    private val useCase: GetDetailUserUseCase
) : ViewModel() {

    private val userName
        get() = savedStateHandle.get<String>(ID_PARAM)
            ?: throw IllegalStateException("Null Pointer")

    private val _uiState by lazy { mutableStateOf<DetailUiState>(DetailUiState.Loading) }
    internal val uiState: State<DetailUiState> by lazy { _uiState.apply { loadUiState() } }

    private val _isFavorite by lazy { mutableStateOf<Boolean>(false) }
    val isFavorite: State<Boolean> = _isFavorite

    lateinit var userData: UserModel

    private fun loadUiState() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            _uiState.value = when (val response = useCase(userName)) {
                is Resource.Success -> {
                    if (response.data != null) {
                        userData =
                            UserModel(
                                id = response!!.data!!.id,
                                login = response!!.data!!.login,
                                score = 1.0,
                                type = response!!.data!!.type,
                                url = response!!.data!!.url,
                                favourites = false
                            )
                        checkFavourite(userData.id)
                        DetailUiState.Shows(response.data!!)
                    } else {
                        DetailUiState.Error(response.message ?: "Data not found")
                    }

                }
                is Resource.Failed -> DetailUiState.Error(response.message ?: "Error")
            }
        }
    }

    fun addData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCase.addLocal(userData)
            }
            _isFavorite.value = true
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCase.deleteLocal(userData)
            }
            _isFavorite.value = false
        }
    }

    private fun checkFavourite(id: Int) {
        viewModelScope.launch {
            when (val user = useCase.getUserById(id)) {
                is Resource.Success -> _isFavorite.value = true
                is Resource.Failed -> _isFavorite.value = false
            }
        }
    }

    fun navigateUp() {
        navigator.navigateUp()
    }
}

internal sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Error(val message: String) : DetailUiState()
    data class Shows(val users: DetailModel) :
        DetailUiState()
}
