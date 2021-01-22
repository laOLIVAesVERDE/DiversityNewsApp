package com.oliva.verde.android.divercitynewsapp.service.repository.database

import androidx.room.*

@Dao
interface StockArticleDao {
    @Query("SELECT * FROM StockArticle")
    suspend fun findAll() : MutableList<StockArticle>

    /**
     * レコードが存在したら置き換え、存在しなければインサート
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(targetArticle: StockArticle)

    @Delete
    suspend fun delete(targetArticle: StockArticle)
    /*
    val LOGTAG = "StockArticleDao"
    var realm: Realm = Realm.getInstance(
        RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
    )

    fun insert(targetArticle: Article) {
        Log.d(LOGTAG, "insert")
        realm.executeTransaction {
            val article = realm.createObject(Article::class.java, UUID.randomUUID().toString())
            article.apply {
                url = targetArticle.url
                urlToImage = targetArticle.urlToImage
                publishedAt = targetArticle.publishedAt
                title = targetArticle.title
                isReadFlag = false
            }
            realm.copyToRealm(article)
        }


        /*
        realm.executeTransactionAsync(Realm.Transaction {
            override fun execute(realm: Realm) {
                val article = realm.createObject(Article::class.java, UUID.randomUUID().toString())
                article.apply {
                    url = targetArticle.url
                    urlToImage = targetArticle.urlToImage
                    publishedAt = targetArticle.publishedAt
                    title = targetArticle.title
                    isReadFlag = false
                }
                realm.copyToRealm(article)
            }
        }

         */
    }

    fun selectAll() : RealmResults<Article> {
        Log.d(LOGTAG, "selectAll")
        val articles = realm.where(Article::class.java).findAll()
        return articles
    }

    suspend fun delete(targetArticle: Article) {
        withContext(Dispatchers.IO) {
            realm.executeTransaction{
                val article = realm.where(Article::class.java).equalTo("id", targetArticle.id).findFirst()
                article?.deleteFromRealm()
            }
        }
    }

    fun search(query : String): MutableList<Article> {
        val searchedArticles = realm.where(Article::class.java).contains("title", query).findAll()
        return searchedArticles
    }

    // 未読記事から記事を検索
    fun searchFromIsNotRead(query: String) : MutableList<Article> {
        val searchedArticles =  realm.where(Article::class.java)
            .equalTo("isReadFlag", false)
            .contains("title", query)
            .findAll()
        return searchedArticles
    }

    fun updateFlag(id : String) {
        realm.executeTransaction {
            val article = realm.where(Article::class.java).equalTo("id", id).findFirst()
            if (article != null) {
                article.isReadFlag = true
            }
        }
    }

    fun searchNotReadArticles() :RealmResults<Article> {
        val searchedArticles = realm.where(Article::class.java).equalTo("isReadFlag", false).findAll()
        return searchedArticles
    }

     */

}