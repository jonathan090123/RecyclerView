package paba.materi.recyclerview

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class detWayang : AppCompatActivity() {

    private lateinit var _ivFoto: ImageView
    private lateinit var _tvNama: TextView
    private lateinit var _tvDes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _ivFoto = findViewById(R.id.ivFoto)
        _tvNama = findViewById(R.id.tvNama)
        _tvDes = findViewById(R.id.tvDes)

        enableEdgeToEdge()
        setContentView(R.layout.activity_det_wayang)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val dataIntent : wayang? = intent.getParcelableExtra<wayang>("kirimData",wayang::class.java)
        if (dataIntent != null) {
            Picasso.get()
                .load(dataIntent.foto)
                .into(_ivFoto)
            _tvNama.setText(dataIntent.nama)
            _tvDes.setText(dataIntent.deskripsi)
        }

    }
}