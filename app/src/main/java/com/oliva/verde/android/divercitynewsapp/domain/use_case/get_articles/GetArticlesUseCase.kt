package com.oliva.verde.android.divercitynewsapp.domain.use_case.get_articles

import com.oliva.verde.android.divercitynewsapp.common.Resource
import com.oliva.verde.android.divercitynewsapp.data.remote.dto.toArticle
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleRepository
) {
    operator fun invoke(searchWord: String): Flow<Resource<List<Article.ResponseArticle>>> = flow {
        try {
            emit(Resource.Loading())
            val articles = articlesRepository.getArticles(searchWord).map { it.toArticle() }
            emit(Resource.Success<List<Article.ResponseArticle>>(articles))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Article.ResponseArticle>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Article.ResponseArticle>>("Couldn't reach server. Check your internet connection"))
        }
    }
}