package dev.wxlf.starwarslibrary.core.data.retrofit.models

data class SWModel<T>(
    var count: Int,
    var next: String?,
    var previous: String?,
    var results: ArrayList<T>
) {
    fun hasMore(): Boolean = !next.isNullOrEmpty()
}