package com.example.tarasovartem3.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tarasovartem3.R
import com.example.tarasovartem3.activity.NewPostActivity.Companion.textArg
import com.example.tarasovartem3.adapter.OnInteractionListener
import com.example.tarasovartem3.adapter.PostsAdapter
import com.example.tarasovartem3.databinding.FragmentFeedBinding
import com.example.tarasovartem3.repository.Post
import com.example.tarasovartem3.viewmodel.PostViewModel


class FeedFragment : Fragment() {

private val viewModel: PostViewModel by viewModels(
    ownerProducer = ::requireParentFragment
)
override fun onCreateView(
    inflater:LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
):View? {
    val binding: FragmentFeedBinding = FragmentFeedBinding.inflate(
        inflater,
        container,
        false
    )

    val adapter = PostsAdapter(object : OnInteractionListener {
        override fun onVideo(post: Post) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=VPwQ7OAGe0k&t=3s&ab_channel=%D0%A5%D0%B0%D0%B9%D0%BB%D0%B0%D0%B9%D1%82%D1%8B%D0%9F%D0%B8%D1%80%D0%B0%D1%82%D0%B0"))
            startActivity(intent)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
//                val text = post.content
//                newPostActivity.launch(text)
            val bundle = Bundle()
            bundle.textArg = post.content
            findNavController().navigate(R.id.action_feedFragment_to_newPostActivity, bundle)
        }
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }
        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }
        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser))
            startActivity(shareIntent)

        }
        override fun onAuthorClicked(post: Post) {
            val bundle = Bundle()
            bundle.putInt("postId", post.id)
            findNavController().navigate(R.id.action_feedFragment_to_oneFragmentPost3, bundle)

        }

    })
    binding.list.adapter=adapter
    viewModel.data.observe(viewLifecycleOwner) { posts ->
        adapter.submitList(posts)
    }
    binding.fab.setOnClickListener {
//            val text = ""
//            newPostLauncher.launch(text)

            findNavController().navigate(R.id.action_feedFragment_to_newPostActivity)
        }
    return binding.root

}

    }







