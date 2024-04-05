package br.senac.lojaretrofit.services

import br.senac.lojaretrofit.model.Produto
import retrofit2.Call
import retrofit2.http.*

interface ProdutoService {
//    h

    @GET("/android/rest/produto")
    fun listar(): Call<List<Produto>>

//    @GET("/android/rest/produto/{nome}")
//    fun pesquisar(@Path("nome") nome: String): Call<List<Produto>>

//    @GET("/android/rest/produto")
//    fun pesquisar2(@Query("nome") nome: String): Call<List<Produto>>

//    @POST("/android/rest/produto")
//    fun inserir(produto: Produto): Call<Produto>

//    @PUT("/android/rest/produto")
//    fun atualizar(@Body produto: Produto, @Query("id") id: Int): Call<Produto>

//    @DELETE("/android/rest/produto")
//    fun excluir(@Query("id") id: Int): Call<Produto>

}