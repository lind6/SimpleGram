package com.example.simplegram.fragments

import android.util.Log
import com.example.simplegram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : HomeFragment() {

    override fun queryPosts() {

        val query: ParseQuery<Post> = ParseQuery.getQuery( Post::class.java)

        // find all post objects
        query.include( Post.KEY_USER )
        // sort by field "createdAt" in descending order
        query.addDescendingOrder( "createdAt" )
        // only return posts from current user
        query.whereEqualTo( Post.KEY_USER, ParseUser.getCurrentUser() )

        // get 20 most recent posts
        query.findInBackground( object: FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException? ) {
                if ( e != null ) {
                    // Doh!
                    Log.e( TAG, "Error fetching posts" )
                } else {
                    // OK
                    if ( posts != null ) {
                        for ( post in posts ) {
                            Log.i( TAG, "Post: " + post.getDescription() + ", User: " + post.getUser()?.username )
                        }

                        allPosts.addAll( posts )
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}