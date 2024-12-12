package paba.materi.recyclerview

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences

    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi: MutableList<String> = emptyList<String>().toMutableList()
    private var _karakter: MutableList<String> = emptyList<String>().toMutableList()
    private var _gambar: MutableList<String> = emptyList<String>().toMutableList()

    private var arWayang = arrayListOf<wayang>()
    private lateinit var _rvWayang: RecyclerView
    private lateinit var adapterwayang: adapterRecView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _rvWayang = findViewById(R.id.rvWayang)

        if (arWayang.size == 0) {
            SiapkanData()
        } else {
            arWayang.forEach {
                _nama.add(it.nama)
                _gambar.add(it.foto)
                _deskripsi.add(it.deskripsi)
                _karakter.add(it.karakter)
            }
            arWayang.clear()
        }

        sp = getSharedPreferences("dataSP", MODE_PRIVATE)

        TambahData()
        TampilkanData()

        val gson = Gson()
        val isiSP = sp.getString("spWayang", null)
        val type = object : TypeToken<ArrayList<wayang>>() {}.type

        if (isiSP != null)
            arWayang = gson.fromJson(isiSP, type)

        // Listener untuk SearchView
        val _searchView = findViewById<SearchView>(R.id.searchView)
        _searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = if (!newText.isNullOrEmpty()) {
                    arWayang.filter {
                        it.nama.contains(newText, ignoreCase = true) ||
                                it.karakter.contains(newText, ignoreCase = true) ||
                                it.deskripsi.contains(newText, ignoreCase = true)
                    } as ArrayList<wayang>
                } else {
                    arWayang
                }
                adapterwayang.filterList(filteredList)
                return true
            }
        })
    }

    fun SiapkanData() {
        _nama = resources.getStringArray(R.array.namaWayang).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang).toMutableList()
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang).toMutableList()
        _gambar = resources.getStringArray(R.array.gambarWayang).toMutableList()
    }

    fun TambahData() {
        val gson = Gson()
        val editor = sp.edit()
        arWayang.clear()
        for (position in _nama.indices) {
            val data = wayang(
                _gambar[position],
                _nama[position],
                _karakter[position],
                _deskripsi[position]
            )
            arWayang.add(data)
        }
        val json = gson.toJson(arWayang)
        editor.putString("spWayang", json)
        editor.apply()
    }

    fun TampilkanData() {
        _rvWayang.layoutManager = LinearLayoutManager(this)

        adapterwayang = adapterRecView(arWayang)
        _rvWayang.adapter = adapterwayang

        adapterwayang.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: wayang) {
                Toast.makeText(this@MainActivity, data.nama, Toast.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, detWayang::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah Benar Data " + _nama[pos] + " akan dihapus?")
                    .setPositiveButton(
                        "HAPUS"
                    ) { dialog, which ->
                        _gambar.removeAt(pos)
                        _nama.removeAt(pos)
                        _deskripsi.removeAt(pos)
                        _karakter.removeAt(pos)
                        TambahData()
                        TampilkanData()
                    }
                    .setNegativeButton(
                        "BATAL"
                    ) { dialog, which ->
                        Toast.makeText(
                            this@MainActivity,
                            "Data Batal Dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                    }.show()
            }
        })
    }
}
