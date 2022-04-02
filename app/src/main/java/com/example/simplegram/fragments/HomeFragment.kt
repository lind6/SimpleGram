package com.example.simplegram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.simplegram.Post
import com.example.simplegram.PostAdapter
import com.example.simplegram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class HomeFragment : Fragment() {
    //
    lateinit var swipeContainer: SwipeRefreshLayout

    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_home, container, false )
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )
        // set up views and click listeners
        postsRecyclerView = view.findViewById( R.id.postRecyclerView )

        // procedure to populate recyclerView
        // 1. create layout for each row in list (item_post.xml)
        // 2. Create data source for each row (this is post class)
        // 3. Create adapter to bridge data and row layout (PostAdapter Class)
        // 4. set adapter on RecyclerView
        adapter                   = PostAdapter( requireContext(), allPosts )
        postsRecyclerView.adapter = adapter

        // 5. set layout manager on RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager( requireContext() )

        queryPosts()


        // pull-to-refresh

        // get reference to the swipeContainer view
        swipeContainer = view.findViewById( R.id.swipeContainer )

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            refreshFeed()
        }
    }

    // query for all posts on server
    open fun queryPosts() {

        val query: ParseQuery<Post> = ParseQuery.getQuery( Post::class.java)

        // find all post objects
        query.include( Post.KEY_USER )

        // sort by field "createdAt" in descending order
        query.addDescendingOrder( "createdAt" )

        // get 20 most recent posts
        query.setLimit( 20 )

        query.findInBackground( object: FindCallback<Post> {
            override fun done( posts: MutableList<Post>?, e: ParseException? ) {
                if ( e != null ) {
                    // Doh!
                    Log.e( TAG, "Error fetching posts" )
                } else {
                    // OK
                    if ( posts != null ) {
                        for ( post in posts ) {
                            Log.i( TAG, "Post: " + post.getDescription() + ", User: " + post.getUser()?.username )
                        }

                        allPosts.clear()

                        allPosts.addAll( posts )
                        swipeContainer.setRefreshing( false )

                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    fun refreshFeed() {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        queryPosts()
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}