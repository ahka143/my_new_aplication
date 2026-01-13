package com.example.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt // Renk uyarısını çözen import
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var suAnkiKategori = "Genel"
    // İnternetten gelen sözleri burada tutacağız
    private var tumSozler = mutableListOf<SozModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Arayüz elemanları
        val metinAlani = findViewById<TextView>(R.id.sozMetni)
        val degistirButonu = findViewById<Button>(R.id.sozDegistirButon)
        val anaLayout = findViewById<LinearLayout>(R.id.anaLayout)
        val paylasButon = findViewById<Button>(R.id.paylasButon)
        val kopyalaButon = findViewById<Button>(R.id.kopyalaButon)

        val btnGenel = findViewById<Button>(R.id.btnGenel)
        val btnSpor = findViewById<Button>(R.id.btnSpor)
        val btnBasari = findViewById<Button>(R.id.btnBasari)

        // 1. Fonksiyon: Sözü ve Rengi Güncelleme
        fun sozuGuncelle() {
            // Filtreleme: İnternetten gelenler boşsa sabit bir yazı göster
            val secilenListe = tumSozler.filter { it.kategori == suAnkiKategori }

            if (secilenListe.isNotEmpty()) {
                metinAlani.text = secilenListe.random().icerik
            } else {
                metinAlani.text = "Sözler yükleniyor veya bu kategoride söz yok..."
            }

            val pastelRenkler = listOf("#FFD1DC", "#FFECB3", "#B2DFDB", "#E1BEE7", "#C8E6C9")
            anaLayout.setBackgroundColor(pastelRenkler.random().toColorInt())
        }

        // 2. Fonksiyon: Buton Renklerini Güncelleme
        fun butonRenkleriniGuncelle(secilenButon: Button, digerButonlar: List<Button>) {
            secilenButon.setBackgroundColor(Color.parseColor("#6200EE"))
            secilenButon.setTextColor(Color.WHITE)
            for (buton in digerButonlar) {
                buton.setBackgroundColor(Color.LTGRAY)
                buton.setTextColor(Color.BLACK)
            }
        }

        // 3. Fonksiyon: İnternetten Veri Çekme (Hata Aldığın Yer Burasıydı!)
        fun internettenSozleriCek() {
            RetrofitClient.instance.sozleriGetir().enqueue(object : Callback<List<SozModel>> {
                override fun onResponse(call: Call<List<SozModel>>, response: Response<List<SozModel>> ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            println("Gelen veri sayısı: ${body.size}")
                            tumSozler.clear()
                            tumSozler.addAll(body)
                            sozuGuncelle()
                        } else {
                            println("Body boş geldi!")
                        }
                    } else {
                        println("Hata Kodu: ${response.code()}")
                    }
                    println("Gelen veri sayısı: ${response.body()?.size}")
                }

                override fun onFailure(call: Call<List<SozModel>>, t: Throwable) {
                    // Hatanın gerçek sebebini Logcat'e yazdırıyoruz
                    println("Retrofit Hatası: ${t.localizedMessage}")

                    // Yedek sözleri yükle
                    tumSozler.add(SozModel("Genel", "Hata olsa da denemeye devam et!"))
                    sozuGuncelle()
                }
            })
        }

        // --- TIKLAMA OLAYLARI ---
        btnGenel.setOnClickListener {
            suAnkiKategori = "Genel"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnGenel, listOf(btnSpor, btnBasari))
        }

        btnSpor.setOnClickListener {
            suAnkiKategori = "Spor"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnSpor, listOf(btnGenel, btnBasari))
        }

        btnBasari.setOnClickListener {
            suAnkiKategori = "Başarı"
            sozuGuncelle()
            butonRenkleriniGuncelle(btnBasari, listOf(btnGenel, btnSpor))
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

        // UYGULAMA AÇILIŞINDA ÇALIŞACAKLAR
        internettenSozleriCek()
        butonRenkleriniGuncelle(btnGenel, listOf(btnSpor, btnBasari))
    }
}