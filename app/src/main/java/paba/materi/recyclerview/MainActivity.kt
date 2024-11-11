package paba.materi.recyclerview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var _nama: Array<String>
    private lateinit var _karakter: Array<String>
    private lateinit var _deskripsi: Array<String>
    private lateinit var _gambar: Array<String>
    private lateinit var _rvWayang: RecyclerView

    private var arWayang = arrayListOf<wayang>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _rvWayang = findViewById(R.id.rvWayang)

        SiapkanData()
        TambahData()
        TampilkanData()
    }

    private fun SiapkanData() {
        _nama = resources.getStringArray(R.array.namaWayang)
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang)
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang)
        _gambar = resources.getStringArray(R.array.gambarWayang)
    }

    private fun TambahData() {
        for (position in _nama.indices) {
            val data = wayang(
                _gambar[position],
                _nama[position],
                _karakter[position],
                _deskripsi[position]
            )
            arWayang.add(data)
        }
    }

    private fun TampilkanData() {
        _rvWayang.layoutManager = LinearLayoutManager(this)
        _rvWayang.adapter = adapterRecView(arWayang)
    }
}
