package com.oliva.verde.android.divercitynewsapp.domain.use_case.get_stock_articles

import com.oliva.verde.android.divercitynewsapp.common.Resource
import com.oliva.verde.android.divercitynewsapp.data.local.dto.toStockArticle
import com.oliva.verde.android.divercitynewsapp.data.remote.dto.toArticle
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStockArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleRepository
) {
    operator fun invoke(): Flow<Resource<List<Article.StockArticle>>> = flow {
        try {
            emit(Resource.Loading())
            val articles = articlesRepository.getStockArticles().map { it.toStockArticle() }
            emit(Resource.Success<List<Article.StockArticle>>(articles))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Article.StockArticle>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Article.StockArticle>>("Couldn't reach server. Check your internet connection"))
        }
    }
}