package com.example.retrofit

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() ,SearchView.OnQueryTextListener{

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: ArticleAdapter
    private val artileList = mutableListOf<Articles>()
    private var countries = HashMap<String,String>()
    private var country = "us"
    private var category = "business"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchNews.setOnQueryTextListener(this)
        initRecyclerView()
        searchNew(category)
        countries.put("emiratos arabes","ae")
        countries.put("argentina","ar")
        countries.put("austria","ae")
        countries.put("australia","au")
        countries.put("belgica","be")
        countries.put("bulgaria","bg")
        countries.put("brasil","br")
        countries.put("canada","ca")
        countries.put("suiza","ch")
        countries.put("china","cn")
        countries.put("colombia","co")
        countries.put("cuba","cu")
        countries.put("chequia","cz")
        countries.put("alemania","de")
        countries.put("egipto","eg")
        countries.put("france","fr")
        countries.put("inglaterra","gb")
        countries.put("grecia","gr")
        countries.put("hungaria","hu")
        countries.put("indonesia","id")
        countries.put("irlanda","ie")
        countries.put("israel","il")
        countries.put("india","in ")
        countries.put("italia","it")
        countries.put("japon","jp")
        countries.put("corea del sur","kr")
        countries.put("lituania","lt")
        countries.put("letonia","lv")
        countries.put("marruecos","ma")
        countries.put("mexico","mx")
        countries.put("malasia","my")
        countries.put("nigeria","ng")
        countries.put("holanda","nl")
        countries.put("noruega","no")
        countries.put("nueva zelanda","nz")
        countries.put("filipinas","ph")
        countries.put("polonia","pl")
        countries.put("portugal","pt")
        countries.put("rumania","ro")
        countries.put("serbia","rs")
        countries.put("rusia","ru")
        countries.put("sudafrica","sa")
        countries.put("suecia","se")
        countries.put("singapur","sg")
        countries.put("eslovenia","si")
        countries.put("eslovaquia","sk")
        countries.put("tailandia","th")
        countries.put("turquia","tr")
        countries.put("taiwan","tw")
        countries.put("ucrania","ua")
        countries.put("estados unidos","us")
        countries.put("venezuela","ve")

    }

    private fun initRecyclerView(){

        adapter = ArticleAdapter(artileList)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

    }

    private fun searchNew(request:String){

        val api = Retrofit2()

        System.out.println(request)

        if(countries.containsKey(request)){
            country = countries.getValue(request.toLowerCase())
        }

        CoroutineScope(Dispatchers.IO).launch {
            val call = api.getService()?.getNewsByCategory(country,category,"4b94054dbc6b4b3b9e50d8f62cde4f6c")
            val news: NewsResponse? = call?.body()

            runOnUiThread{
                if (call!!.isSuccessful){
                    if (news?.status.equals("ok")){
                        val article = news?.articles ?: emptyList()
                        artileList.clear()
                        artileList.addAll(article)
                        adapter.notifyDataSetChanged()

                    } else{
                        showMessage("Error en retrofit")
                    }
                } else{
                    showMessage("Error en retrofit")
                }
                hideKeyBoard()
            }

        }


    }


    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showMessage(message:String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            var request = query.toLowerCase(Locale.ROOT)
            searchNew(request)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}


