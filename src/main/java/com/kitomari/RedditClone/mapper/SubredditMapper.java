package com.kitomari.RedditClone.mapper;

import com.kitomari.RedditClone.dto.SubredditDto;
import com.kitomari.RedditClone.models.Post;
import com.kitomari.RedditClone.models.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }
}
