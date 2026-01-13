package com.example.myapplication // Kendi paket adın olduğundan emin ol

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Arayüz elemanlarını bağlıyoruz (ID'lerin XML ile birebir aynı olması gerekir)
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

        // 3. Değiştir Butonu tıklandığında yapılacak işlemler
        degistirButonu.setOnClickListener {
            // Sözü değiştir
            val rastgeleSoz = sozler.random()
            metinAlani.text = rastgeleSoz // HATA BURADAYDI: metinAlani olarak düzelttik

            // Gözü yormayan pastel renk listesi
            val pastelRenkler = listOf(
                "#FFD1DC", "#FFECB3", "#B2DFDB", "#E1BEE7", "#C8E6C9", "#BBDEFB", "#F5F5F5"
            )

            // Listeden rastgele bir renk seç ve uygula
            val secilenRenk = pastelRenkler.random().toColorInt()
            anaLayout.setBackgroundColor(secilenRenk)
        }

        // 4. Paylaş Butonu tıklandığında yapılacak işlemler
        paylasButon.setOnClickListener {
            val mesaj = metinAlani.text.toString()
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mesaj)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, "Şununla paylaş:"))
        }
    }
}