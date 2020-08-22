package com.oliva.verde.android.divercitynewsapp

import android.content.Context
import android.view.ViewGroup
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter
import io.realm.Realm
import io.realm.RealmViewHolder


class StockRecyclerViewAdapter(
    context : Context?,
    realm : Realm?,
    filterColumnName : String?
) : RealmSearchAdapter<Article?, StockRecyclerViewAdapter.ViewHolder?>(
    context!!,
    realm!!,
    filterColumnName!!
) {
    inner class ViewHolder(stockArticleView : StockArticleView) : RealmViewHolder(stockArticleView) {
        val stockAriticleView : StockArticleView

        init {
            this.stockAriticleView = stockArticleView
        }
    }

    override fun onCreateRealmViewHolder(p0: ViewGroup?, p1: Int): ViewHolder? {
        return ViewHolder(StockArticleView(ViewGroup.context))
    }

    override fun onBindRealmViewHolder(p0: ViewHolder?, p1: Int) {
        val article : Article? = realmResults[p1]
        viewHolder.blogItemView.bind(article)
    }

    fun convertViewHolder(viewHolder: RealmViewHolder?): ViewHolder? {
        return ViewHolder::class.java.cast(viewHolder)
    }
}

/**
class BlogRecyclerViewAdapter(
    context: Context?,
    realm: Realm?,
    filterColumnName: String?
) : RealmSearchAdapter<Blog?, BlogRecyclerViewAdapter.ViewHolder?>(
    context,
    realm,
    filterColumnName!!
) {
    inner class ViewHolder(blogItemView: BlogItemView) : RealmViewHolder(blogItemView) {
        val blogItemView: BlogItemView

        init {
            this.blogItemView = blogItemView
        }
    }

    override fun onCreateRealmViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(BlogItemView(viewGroup.context))
    }

    override fun onBindRealmViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        val blog: Blog = realmResults[position]
        viewHolder.blogItemView.bind(blog)
    }

    fun convertViewHolder(viewHolder: RealmViewHolder?): ViewHolder? {
        return ViewHolder::class.java.cast(viewHolder)
    }
}
 */