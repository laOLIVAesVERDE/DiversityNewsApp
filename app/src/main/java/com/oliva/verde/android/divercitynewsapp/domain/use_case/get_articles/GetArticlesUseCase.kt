package com.oliva.verde.android.divercitynewsapp.domain.use_case.get_articles

import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleRepository
) {
}