package com.example.tebakangka

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Variabel Penyimpan Data Game
    private var targetNumber = 0
    private var score = 100
    private var isGameOver = false

    // Deklarasi Komponen Tampilan
    private lateinit var tvScore: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var etGuess: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menghubungkan variabel dengan ID di XML
        tvScore = findViewById(R.id.tvScore)
        tvFeedback = findViewById(R.id.tvFeedback)
        etGuess = findViewById(R.id.etGuess)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)

        // Memulai game pertama kali
        startNewGame()

        // Aksi saat tombol "Tebak" diklik
        btnSubmit.setOnClickListener {
            if (!isGameOver) {
                processGuess()
            }
        }

        // Aksi saat tombol "Main Lagi" diklik
        btnReset.setOnClickListener {
            startNewGame()
        }
    }

    private fun startNewGame() {
        // Acak angka dari 0 sampai 100
        targetNumber = Random.nextInt(0, 101)
        score = 100
        isGameOver = false

        // Reset tampilan
        tvScore.text = "Skor: $score"
        tvFeedback.text = "Silakan masukkan angka (0 - 100) dan klik Tebak."
        etGuess.text.clear()
        etGuess.isEnabled = true
        btnSubmit.isEnabled = true
        btnReset.visibility = View.GONE
    }

    private fun processGuess() {
        val input = etGuess.text.toString()

        // Validasi jika kolom input kosong
        if (input.isEmpty()) {
            tvFeedback.text = "Masukkan angka terlebih dahulu!"
            return
        }

        val userGuess = input.toInt()

        // Validasi batas angka
        if (userGuess < 0 || userGuess > 100) {
            tvFeedback.text = "Angka harus di antara 0 sampai 100!"
            return
        }

        // Cek tebakan
        if (userGuess == targetNumber) {
            tvFeedback.text = "Selamat! Tebakan Anda BENAR.\nAngka rahasia: $targetNumber"
            endGame()
        } else {
            // Kurangi skor jika salah
            score -= 10
            tvScore.text = "Skor: $score"

            if (score <= 0) {
                tvFeedback.text = "Game Over! Skor Anda habis.\nAngka rahasianya adalah $targetNumber"
                endGame()
            } else {
                val hint = if (userGuess < targetNumber) "terlalu KECIL" else "terlalu BESAR"
                tvFeedback.text = "Tebakan Anda $hint!"
            }
        }
        etGuess.text.clear()
    }

    private fun endGame() {
        isGameOver = true
        etGuess.isEnabled = false
        btnSubmit.isEnabled = false
        btnReset.visibility = View.VISIBLE
    }
}