package com.example.pasienapp.ui.pasien

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasienapp.data.model.Pasien
import com.example.pasienapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

sealed class PasienState {
    object Loading : PasienState()
    data class Success(val data: List<Pasien>) : PasienState()
    data class Error(val message: String) : PasienState()
}

class PasienViewModel : ViewModel() {

    private val _pasienState = MutableLiveData<PasienState>()
    val pasienState: LiveData<PasienState> = _pasienState

    fun fetchPasien(token: String) {
        _pasienState.value = PasienState.Loading
        viewModelScope.launch {
            try {
                val bearerToken = "Bearer $token"
                val response = RetrofitClient.instance.getPasien(bearerToken)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        _pasienState.value = PasienState.Success(body.data)
                    } else {
                        _pasienState.value = PasienState.Error(body?.message ?: "Data tidak ditemukan")
                    }
                } else {
                    _pasienState.value = PasienState.Error("Gagal mengambil data: ${response.code()}")
                }
            } catch (e: Exception) {
                _pasienState.value = PasienState.Error("Koneksi gagal: ${e.message}")
            }
        }
    }
}