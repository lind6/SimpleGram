package com.example.simplegram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter( val context: Context, var posts: MutableList<Post> ) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): PostAdapter.ViewHolder {
        // get the viewholder from the view
        val view = LayoutInflater.from( context ).inflate( R.layout.item_post, parent, false )
        return ViewHolder( view )
    }

    override fun onBindViewHolder( holder: PostAdapter.ViewHolder, position: Int ) {
        // get specified post from list
        val post = posts.get( position )

        // bind post with the viewholder
        holder.bind( post )
    }

    override fun getItemCount(): Int {
        // get size of the list of posts
        return posts.size
    }

    class ViewHolder( itemView: View) : RecyclerView.ViewHolder( itemView ) {

        val tvUserName:    TextView
        val tvDescription: TextView
        val ivImage:       ImageView

        // set up views
        init {
            tvUserName    = itemView.findViewById( R.id.tvUserName    )
            ivImage       = itemView.findViewById( R.id.ivImage       )
            tvDescription = itemView.findViewById( R.id.tvDescription )
        }

        // bind post to the view
        fun bind( post: Post ) {
            tvDescription.text = post.getDescription()
            tvUserName.text    = post.getUser()?.username

            // populate image view (via Glide library)
            Glide.with( itemView.context ).load( post.getImage()?.url ).into( ivImage )
        }
    }

    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun addAll( newPosts: List<Post> ) {
        posts.addAll( newPosts )
        notifyDataSetChanged()
    }
}

