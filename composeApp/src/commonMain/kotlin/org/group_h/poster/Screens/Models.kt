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
    val title: String,//post heading
    val content: String,//post content
    val hasImage: Boolean = false,//if post has an image
    val hasSong: Boolean = false//if post has a song
)
/*
    same with Post, it just defines what should
    be in the user info on the profile screen.
    "posts" will only work when user has made a
    post
 */
data class User(
    val username: String,//username
    val joinDate: String,//user join date
    val isCurrentUser: Boolean,//if the user is logged in
    val posts: List<Post>//all the users posts (if they have any)
)