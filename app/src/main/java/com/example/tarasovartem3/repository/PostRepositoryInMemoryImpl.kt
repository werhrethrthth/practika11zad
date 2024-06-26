package com.example.tarasovartem3.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository{
    private var nextId=1
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Государственное бюджетное профессиональное образовательное учреждение",
            content = "ГБПОУ ВО «БТПИТ» образовано в соответствии с  постановлением правительства Воронежской области от 20 мая 2015 года № 401 в результате реорганизации в  форме слияния государственного образовательного бюджетного учреждения среднего профессионального образования Воронежской области «Борисоглебский индустриальный техникум», \\nhttps://btpit36.ru/",
            published = "11 августа в 20:15",
            likes = 999999,
            share = 999,
            likedByMe = false,
            shareByMe=false
        ),
        Post(
            id = nextId++,
            author = "Государственное бюджетное профессиональное образовательное учреждение",
            content = "ГБПОУ ВО «БТПИТ» образовано в соответствии с  постановлением правительства Воронежской области от 20 мая 2015 года № 401 в результате реорганизации в  форме слияния государственного образовательного бюджетного учреждения среднего профессионального образования Воронежской области «Борисоглебский индустриальный техникум», \\nhttps://btpit36.ru/",
            published = "11 августа в 20:15",
            likes = 999999,
            share = 999,
            likedByMe = false,
            shareByMe=false
        ),
        Post(
            id = nextId++,
            author = "Государственное бюджетное профессиональное образовательное учреждение",
            content = "Новости бтпит - переходи по ссылке, \\nhttps://btpit36.ru/",
            published = "11 августа в 20:15",
            likes = 999999,
            share = 999,
            likedByMe = false,
            shareByMe=false
        ),
    ).
    reversed()
    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data
    override fun save(post: Post) {
        if(post.id==0){
            posts = listOf(post.copy(
                id = nextId++,
                author = "Артем тарасов",
                likedByMe = false,
                published = "Сейчас",
                shareByMe = false
            )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map{
            if (it.id != post.id) it else it.copy (content = post.content, likes = post.likes, share = post.share)
        }
        data.value = posts
    }
    override fun likeById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(likedByMe = !it.likedByMe, likes = if (!it.likedByMe) it.likes+1 else it.likes-1)
        }
        data.value = posts
    }
    override fun shareById(id: Int) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(shareByMe = !it.shareByMe, share = it.share+1)
        }
        data.value = posts
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id!=id }
        data.value = posts
    }
    override fun postID(id: Int): LiveData<Post> {
        val postLiveData = MutableLiveData<Post>()
        postLiveData.value = posts.find { it.id == id }

        return postLiveData
    }


}

