package com.example.project7.ui.viewmodel

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project7.data.entity.Mahasiswa
import com.example.project7.repository.LocalRepositoryMhs
import com.example.project7.repository.RepositoryMhs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMhsViewModel (
    private  val repositoryMhs: RepositoryMhs
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = repositoryMhs.getAllMhs()
        .filterNotNull()
        .map {
            HomeUiState(
                listMhs = it.toList(),
                isloading = false,

            )
        }
        .onStart {
            emit(HomeUiState(isloading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isloading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                isloading = true,
            )
        )
}

data class HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isloading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)