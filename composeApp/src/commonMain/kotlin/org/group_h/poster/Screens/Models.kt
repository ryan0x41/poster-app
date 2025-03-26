package org.group_h.poster.Screens

data class Post(
    val title: String,
    val content: String,
    val hasImage: Boolean = false,
    val hasSong: Boolean = false
)

data class User(
    val username: String,
    val joinDate: String,
    val isCurrentUser: Boolean,
    val posts: List<Post>
)