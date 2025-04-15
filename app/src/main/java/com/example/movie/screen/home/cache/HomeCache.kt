package com.example.movie.screen.home.cache

import com.example.model.model.Movie


class HomeCache {
    private constructor()
    var trendingMovieDay: List<Movie>? = null
    var trendingMovieWeek: List<Movie>? = null

    fun reset() {
        trendingMovieDay = null
        trendingMovieWeek = null
    }
    companion object {

        /*
         *  All changes of INSTANCE will be shipped to main memory(RAM), not save in local memory (Cache of cpu)
         *  Each thread map to os thread, os thread run into each thread of cpu, each thread of cpu has cache (L1, L2), L3 use for all
        */

        /*
         *  Một thao tác nguyên tử (atomic) là một thao tác không thể bị ngắt quãng và được thực hiện hoàn toàn trong một lần, không bị gián đoạn.
         *  Nếu một thao tác là nguyên tử, thì dữ liệu sẽ luôn ở trong một trạng thái hợp lệ khi thao tác đó hoàn thành.
         *  count++ -> 3 atomic (read, plus, write)
        */
        @Volatile
        private var INSTANCE: HomeCache? = null
        fun getInstance(): HomeCache {
            /*
            * synchronized: only one thread can execute this function at a time
            * make executing of a series of atomic be safe
            */
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeCache().also { INSTANCE = it }
            }
        }
    }
}