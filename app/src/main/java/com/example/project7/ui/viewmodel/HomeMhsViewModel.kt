package com.example.project7.ui.viewmodel

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.example.project7.data.entity.Mahasiswa

data class HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isloading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)