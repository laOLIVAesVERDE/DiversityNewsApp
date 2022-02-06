package com.oliva.verde.android.divercitynewsapp.presentation.article_list.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.domain.use_case.get_articles.GetArticlesUseCase
import com.oliva.verde.android.divercitynewsapp.presentation.article_list.ArticleListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ArticleListState())
    val state: State<ArticleListState> = _state

    init {
        loadArticles()
    }

    /**
     * ViewModelScope は、アプリで ViewModel ごとに定義される。
     * このスコープ内で起動されたすべてのコルーチンは、ViewModel が消去されると自動的にキャンセルされる。
     * ViewModelがアクティブな場合にのみ行う必要がある作業があるとき、コルーチンが役に立つ。
     * 例：レイアウト用のデータを計算している場合、作業を ViewModel にスコープする必要があるため、
     * 　　ViewModel が消去されると、作業はリソースの消費を避けるために自動的にキャンセルされる。
     */
    private fun loadArticles(searchWord: String = "") {
        getArticlesUseCase(searchWord).onEach { result ->

        }
    }

    suspend fun insertTargetArticle(stockArticle: Article.StockArticle) {
//        repository.insertArticle(stockArticle)
    }


}