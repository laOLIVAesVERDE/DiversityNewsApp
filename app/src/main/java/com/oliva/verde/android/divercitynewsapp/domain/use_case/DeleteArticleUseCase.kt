package com.oliva.verde.android.divercitynewsapp.domain.use_case

import com.oliva.verde.android.divercitynewsapp.common.Resource
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.domain.repository.StockArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val stockArticleRepository: StockArticleRepository
) {
    operator fun invoke(stockArticle: Article.StockArticle): Flow<Resource<Boolean>> = flow {
        try {
            val dto = stockArticle.toStockArticleDto()
            stockArticleRepository.delete(dto)
            emit(Resource.Success<Boolean>(true))
        } catch (e: Throwable) {
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}