package com.example.list.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.destination.DetailDestination
import com.example.navigation.navigation.Navigator
import com.example.shared.data.network.model.UsersRandomModelItem
import com.example.shared.domain.GetUsersUseCase
import com.example.shared.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private var page = 1
    private val users = mutableListOf<UsersRandomModelItem>()
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = ListUiState.Error("")
    }

    private var query = "super"
    private var sort = ""

    private val _uiState by lazy { mutableStateOf<ListUiState>(ListUiState.Loading) }
    internal val uiState: State<ListUiState> by lazy { _uiState.apply { loadUiState() } }

    private fun loadUiState() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = ListUiState.Loading
            loadList()
        }
    }

    fun searchUser(q: String) {
        _uiState.value = ListUiState.Loading
        users.clear()
        query = q
        loadList()
    }

    fun sortUser(sort: String) {
        _uiState.value = ListUiState.Loading
        users.clear()
        this.sort = sort
        loadList()
    }

    private fun loadList(
        q: String = query.ifEmpty { "super" },
        sort: String = this.sort
    ) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = when (val result = getUsersUseCase(q = q, page = page, sort = sort)) {
                is Resource.Success -> {
                    users.addAll(result.data?.items ?: emptyList())
                    if (users.isEmpty()) {
                        ListUiState.Error(result.message ?: "Data not found")
                    } else {
                        ListUiState.Shows(users = users, loadNextPage = false)
                    }
                }
                is Resource.Failed -> ListUiState.Error(result.message ?: "Not Found")
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch(exceptionHandler) {
            if (_uiState.value is ListUiState.Shows && (_uiState.value as ListUiState.Shows).loadNextPage.not()) {
                page++
                _uiState.value = ListUiState.Shows(users = users, loadNextPage = true)
                delay(1_000)
                loadList()
            }
        }
    }

    fun selectUser(userName: String) {
        navigator.navigate(DetailDestination.createDetailsRoute(userName = userName))
    }
}

internal sealed class ListUiState {
    object Loading : ListUiState()
    data class Error(val message: String) : ListUiState()
    data class Shows(val users: List<UsersRandomModelItem>, val loadNextPage: Boolean) :
        ListUiState()
}
