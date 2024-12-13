package com.example.project7.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project7.data.entity.Mahasiswa
import com.example.project7.repository.RepositoryMhs
import com.example.project7.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var updateUiState by mutableStateOf(MhsUIState())
        private set
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch{
            updateUiState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }
    fun updateState (mahasiswaEvent: MahasiswaEvent) {
        updateUiState = updateUiState.copy(
            mahasiswaEvent =mahasiswaEvent,
        )
    }

    fun validateField(): Boolean {
        val event = updateUiState.mahasiswaEvent
        val errorState =FormErrorState (
            nim = if (event.nim.isNotEmpty()) null else "NIM Tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin Tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat Tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas Tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan Tidak boleh kosong"
        )

        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val  currentEvent = updateUiState.mahasiswaEvent

        if (validateField()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                }catch (e: Exception) {
                    updateUiState =updateUiState.copy(
                        snackBarMessage = "Data gaga; diupdate"
                    )
                }
            }
            fun  resetSnackBarMessage(){
                updateUiState = updateUiState.copy(snackBarMessage = null)
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = " Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }

}
fun Mahasiswa.toUIStateMhs() : MhsUIState = MhsUIState(
    mahasiswaEvent = this.toDetailUiEvent(),
)