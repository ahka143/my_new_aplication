package com.example.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var suAnkiKategori = "Genel"
    private var tumSozler = mutableListOf<SozModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Arayüz elemanları
        val metinAlani = findViewById<TextView>(R.id.sozMetni)
        val degistirButonu = findViewById<Button>(R.id.sozDegistirButon)
        val anaLayout = findViewById<RelativeLayout>(R.id.anaLayout) // RelativeLayout olarak düzelttik
        val paylasButon = findViewById<Button>(R.id.paylasButon)
        val kopyalaButon = findViewById<Button>(R.id.kopyalaButon)

        val btnGenel = findViewById<Button>(R.id.btnGenel)
        val btnSpor = findViewById<Button>(R.id.btnSpor)
        val btnBasari = findViewById<Button>(R.id.btnBasari)
        val btnMotivasyon = findViewById<Button>(R.id.btnMotivasyon)

        fun sozuGuncelle() {
            val secilenListe = tumSozler.filter { it.kategori == suAnkiKategori }

            if (secilenListe.isNotEmpty()) {
                metinAlani.text = secilenListe.random().icerik
            } else {
                metinAlani.text = "Sözler yükleniyor veya bu kategoride söz yok..."
            }

            val pastelRenkler = listOf("#FFD1DC", "#FFECB3", "#B2DFDB", "#E1BEE7", "#C8E6C9")
            anaLayout.setBackgroundColor(pastelRenkler.random().toColorInt())
        }

        fun butonRenkleriniGuncelle(secilenButon: Button, digerButonlar: List<Button>) {
            secilenButon.setBackgroundColor(Color.parseColor("#6200EE"))
            secilenButon.setTextColor(Color.WHITE)
            for (buton in digerButonlar) {
                buton.setBackgroundColor(Color.LTGRAY)
                buton.setTextColor(Color.BLACK)
            }
        }

        fun internettenSozleriCek() {
            RetrofitClient.instance.sozleriGetir().enqueue(object : Callback<List<SozModel>> {
                override fun onResponse(call: Call<List<SozModel>>, response: Response<List<SozModel>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            tumSozler.clear()
                            tumSozler.addAll(it)
                            sozuGuncelle()
                        }
                    }
                }

                override fun onFailure(call: Call<List<SozModel>>, t: Throwable) {
                    tumSozler.add(SozModel("Genel", "Bağlantı hatası, denemeye devam et!"))
                    sozuGuncelle()
                }
            })
        }

        // Tıklama Olayları
        val tumKategoriler = listOf(btnGenel, btnSpor, btnBasari, btnMotivasyon)

        btnGenel.setOnClickListener {
            suAnkiKategori = "Genel"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnGenel, tumKategoriler.filter { it != btnGenel })
        }

        btnSpor.setOnClickListener {
            suAnkiKategori = "Spor"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnSpor, tumKategoriler.filter { it != btnSpor })
        }

        btnBasari.setOnClickListener {
            suAnkiKategori = "Başarı"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnBasari, tumKategoriler.filter { it != btnBasari })
        }

        btnMotivasyon.setOnClickListener {
            suAnkiKategori = "Motivasyon"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnMotivasyon, tumKategoriler.filter { it != btnMotivasyon })
        }

        degistirButonu.setOnClickListener { sozuGuncelle() }

        paylasButon.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, metinAlani.text.toString())
            startActivity(Intent.createChooser(intent, "Paylaş"))
        }

        kopyalaButon.setOnClickListener {
            val pano = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val veri = ClipData.newPlainText("Söz", metinAlani.text.toString())
            pano.setPrimaryClip(veri)
            Toast.makeText(this, "Kopyalandı!", Toast.LENGTH_SHORT).show()
        }

        internettenSozleriCek()
        butonRenkleriniGuncelle(btnGenel, tumKategoriler.filter { it != btnGenel })
    }
}