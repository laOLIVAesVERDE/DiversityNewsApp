package com.oliva.verde.android.divercitynewsapp.presentation.stock_article.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.oliva.verde.android.divercitynewsapp.common.Resource
import com.oliva.verde.android.divercitynewsapp.domain.use_case.get_stock_articles.GetStockArticlesUseCase
import com.oliva.verde.android.divercitynewsapp.presentation.stock_article.StockArticleListState
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class StockFragmentViewModel @Inject constructor(
    private val getStockArticlesUseCase: GetStockArticlesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(StockArticleListState())
    val state: State<StockArticleListState> = _state

    init {
        getAllStockedArticles()
    }

    fun getAllStockedArticles() {
        getStockArticlesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                   _state.value = StockArticleListState(stockArticles = result.data  ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = StockArticleListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = StockArticleListState(isLoading = true)
                }
            }
        }
    }

//    suspend fun deleteTargetArticle(stockArticle: Article.StockArticle) {
//        repository.deleteStockArticle(stockArticle)
//    }

    /*
    suspend fun deleteTargetArticle(targetArticle: Article) {
        Log.d(LOGTAG, "deleteTargetArticle called")
        repository.deleteTargetArticle(targetArticle)
    }

     */
}