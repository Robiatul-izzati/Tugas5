package com.example.pasienapp.ui.pasien

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pasienapp.databinding.ActivityPasienBinding

class PasienActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasienBinding
    private val viewModel: PasienViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("TOKEN") ?: ""
        val namaUser = intent.getStringExtra("NAMA_USER") ?: "-"

        binding.tvNamaUser.text = "Halo, $namaUser 👋"

        binding.rvPasien.layoutManager = LinearLayoutManager(this)

        observePasienState()
        viewModel.fetchPasien(token)
    }

    private fun observePasienState() {
        viewModel.pasienState.observe(this) { state ->
            when (state) {
                is PasienState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvPasien.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is PasienState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvPasien.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.rvPasien.adapter = PasienAdapter(state.data)
                }
                is PasienState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvPasien.visibility = View.GONE
                    binding.tvError.text = state.message
                    binding.tvError.visibility = View.VISIBLE
                }
            }
        }
    }
}