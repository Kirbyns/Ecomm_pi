package br.senac.lojaretrofit.views

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.senac.lojaretrofit.databinding.ActivityListaProdutosBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import br.senac.lojaretrofit.model.Produto
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.View
import br.senac.lojaretrofit.R
import br.senac.lojaretrofit.databinding.CardItemBinding
import br.senac.lojaretrofit.services.API
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.squareup.picasso.Picasso

class ListaProdutosActivity : AppCompatActivity() {
    lateinit var binding: ActivityListaProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefresh.setOnRefreshListener {
            atualizarProdutos()
        }
    }

    override fun onResume() {
        super.onResume()

        atualizarProdutos()
    }

    fun atualizarProdutos() {

        val callback = object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                desabilitarCarregamento()

                if(response.isSuccessful){
                    val listaProdutos = response.body()
                    atualizarUI(listaProdutos)
                }else{
                    Snackbar.make(binding.container, "Não foi",
                        Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                habilitarCarregamento()

                Snackbar.make(binding.container, "Não foi, não conectou", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha",t)
            }
        }
        API.produto.listar().enqueue(callback)

        habilitarCarregamento()

    }

    private fun desabilitarCarregamento() {
        binding.progressBar.visibility = View.GONE
        binding.shimmer.visibility = View.INVISIBLE
        binding.container.visibility = View.VISIBLE
        binding.shimmer.stopShimmer()
        binding.swipeRefresh.isRefreshing = false
    }

    private fun habilitarCarregamento() {
        binding.progressBar.visibility = View.VISIBLE
        binding.shimmer.visibility = View.INVISIBLE
        binding.container.visibility = View.VISIBLE
        binding.shimmer.stopShimmer()
        binding.swipeRefresh.isRefreshing = true
    }

    fun atualizarUI(lista: List<Produto>?) {
        binding.container.removeAllViews()

        lista?.forEach {
            val cardBinding = CardItemBinding.inflate(layoutInflater)

            cardBinding.textTitulo.text = it.nomeProduto
            cardBinding.textDesc.text = it.descProduto

            val shimmer = Shimmer.ColorHighlightBuilder()
                .setAutoStart(true)
                .setDuration(1000)
                .setBaseAlpha(0.9f)
                .setBaseColor(getColor(R.color.placeholder_grey))
                .setHighlightColor(Color.WHITE)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .build()

            val shimmerDrawable = ShimmerDrawable()
            shimmerDrawable.setShimmer(shimmer)

            Picasso.get().load(
                
                "/produto/image/${it.idProduto}"
            ).placeholder(shimmerDrawable).into(cardBinding.imagem)

            binding.container.addView(cardBinding.root)
        }
    }
}