package com.example.myapplication // <-- Burası senin projenin adı olmalı

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Arayüz elemanlarını bağlıyoruz
        val anaKapsayici = findViewById<View>(R.id.anaKapsayici) // Az önce verdiğimiz ID
        val metinAlani = findViewById<TextView>(R.id.sozMetni)
        val degistirButonu = findViewById<Button>(R.id.sozDegistirButon)
        val anaLayout = findViewById<LinearLayout>(R.id.anaLayout)
        val paylasButon = findViewById<Button>(R.id.paylasButon)
        // 2. Söz listemiz
        val sozler = listOf(
            "Gelecek, bugünden hazırlananlara aittir.",
            "Batan güneş için ağlama; yeniden doğduğunda ne yapacağına karar ver.",
            "Zorluklar, başarının değerini artıran süslerdir.",
            "Yarınlar yorgun ve bezgin kimselere değil, rahatını terk edebilen gayretli kişilere aittir.",
            "Sadece çok uzağa gitme riskini göze alanlar, ne kadar uzağa gidebileceğini bilir.",
            "Karanlığa küfretmektense bir mum yakmak daha iyidir.",
            "Başarı, başarısızlıktan başarısızlığa, hevesini kaybetmeden gitmektir.",
            "Uçamazsan koş, koşamazsan yürü, yürüyemezsen sürün ama ne yaparsan yap ilerlemeye devam et.",
            "Dağı yerinden oynatan, küçük taşları taşımakla başlar.",
            "Hayatın %10'u başına gelenler, %90'ı ise onlara nasıl tepki verdiğindir."
        )

        // 3. Buton tıklandığında yapılacak işlemler
        degistirButonu.setOnClickListener {
            // 1. Sözü değiştir
            metinAlani.text = sozler.random()

            // 2. Rastgele açık bir renk oluştur
            val renk = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            anaLayout.setBackgroundColor(renk)

            // 3. Arka planı boya (Kritik nokta burası)
            anaKapsayici.setBackgroundColor(renk)
        }
        paylasButon.setOnClickListener {
            val mesaj = metinAlani.text.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, mesaj)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Şununla paylaş:"))
        }
    }
}