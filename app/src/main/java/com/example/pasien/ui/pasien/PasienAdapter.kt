package com.example.pasienapp.ui.pasien

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pasienapp.data.model.Pasien
import com.example.pasienapp.databinding.ItemPasienBinding

class PasienAdapter(private val list: List<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    inner class PasienViewHolder(private val binding: ItemPasienBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pasien: Pasien) {
            binding.tvNama.text = pasien.nama
            binding.tvAlamat.text = "📍 ${pasien.alamat}"
            binding.tvTelepon.text = "📞 ${pasien.no_telepon}"
            binding.tvTanggalLahir.text = formatTanggal(pasien.tanggal_lahir)

            val jenisKelamin = if (pasien.jenis_kelamin == "L") "Laki-laki" else "Perempuan"
            binding.tvJenisKelamin.text = jenisKelamin

            // Inisial dari nama
            val inisial = pasien.nama
                .split(" ")
                .take(2)
                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                .joinToString("")
            binding.tvInisial.text = inisial.ifEmpty { "?" }
        }

        private fun formatTanggal(raw: String): String {
            return try {
                val parts = raw.split("-")
                "${parts[2]}-${parts[1]}-${parts[0]}"
            } catch (e: Exception) {
                raw
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val binding = ItemPasienBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PasienViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}