package org.group_h.poster.Screens

/*
    created Models.kt because its easier to
    call post/user from here instead of declaring
    it in every screen (gets too messy and wont work half the time)
*/

/*
    defines what a post should have in it
    user doesnt have to choose the image/song
    in their post
*/
data class Post(
    val title: String,
    val content: String,
    val hasImage: Boolean = false,
    val hasSong: Boolean = false
)
/*
    same with Post, it just defines what should
    be in the user info on the profile screen.
    "posts" will only work when user has made a
    post
 */
data class User(
    val username: String,
    val joinDate: String,
    val isCurrentUser: Boolean,
    val posts: List<Post>
)