package ru.limeek.codesample.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.limeek.codesample.databinding.ItemRepoBinding
import ru.limeek.codesample.domain.entities.GithubRepo

class ReposAdapter(var repoList: List<GithubRepo>,
                   private var onItemClick: (GithubRepo) -> Unit): RecyclerView.Adapter<RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repoList[position], onItemClick)
    }

}

class RepoViewHolder(var binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(repo: GithubRepo, onItemClick: (GithubRepo) -> Unit){
        Glide.with(itemView.context).load(repo.owner?.avatarUrl).into(binding.imageView)
        binding.tvRepoName.text = repo.name
        binding.tvUsername.text = repo.owner?.login
        itemView.setOnClickListener { onItemClick.invoke(repo) }
    }
}