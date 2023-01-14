package com.numble.backend.post.application.command;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.numble.backend.common.utils.S3Utils;
import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.post.domain.Photo;
import com.numble.backend.post.domain.Post;
import com.numble.backend.post.domain.StockStatus;
import com.numble.backend.post.domain.repository.PhotoRepository;
import com.numble.backend.post.domain.repository.PostRepository;
import com.numble.backend.post.exception.InvalidAuthorException;
import com.numble.backend.post.exception.NoLikeForOwnerPostException;
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.user.auth.domain.UserInfo;
import com.numble.backend.user.auth.exception.UserNotFoundException;
import com.numble.backend.user.auth.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService{

	private static final int MAX_IMAGE_COUNT = 10;
	private final PostRepository postRepository;
	private final PhotoRepository photoRepository;
	private final UserInfoRepository userInfoRepository;
	private final S3Utils s3Utils;
	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	@Override
	public void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles) {
		UserInfo user = userInfoRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Post post = new Post(
			postCreateRequest.getTitle(),
			postCreateRequest.getStockCategory(),
			postCreateRequest.getPrice(),
			postCreateRequest.getContents(),
			user
		);
		postRepository.save(post);

		uploadFiles(multipartFiles, post);
	}

	@Override
	public void delete(String postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.validateAuthor(userId);

		postRepository.deleteById(postId);
	}

	@Override
	public void update(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles, String postId) {
		UserInfo user = userInfoRepository.findByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException());

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException());

		if (!post.getUserInfo().equals(user)) {
			throw new InvalidAuthorException();
		}

		post.updatePost(postCreateRequest);

		uploadFiles(multipartFiles, post);
	}

	@Override
	public void likePost(String postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		if (post.isAuthor(userId)) {
			throw new NoLikeForOwnerPostException();
		}

		post.likePost(userId);
	}

	@Override
	public void unlikePost(String postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.unlikePost(userId);
	}


	private void uploadFiles(List<MultipartFile> multipartFiles, Post post) {
		if (multipartFiles == null) {
			return;
		}

		List<String> fileNames = s3Utils.uploadMultiFilesS3(
			amazonS3Client,
			bucketName,
			multipartFiles,
			MAX_IMAGE_COUNT);

		List<Photo> photos = new ArrayList<>();

		ListIterator<String> it = fileNames.listIterator();
		while (it.hasNext()) {
			Photo p = new Photo(
				amazonS3Client.getUrl(bucketName, it.next()).toString(),
				post);

			if (it.nextIndex() == 1) {
				p.setThumbnail(Boolean.TRUE);
			}

			photos.add(p);
		}

		photoRepository.saveAll(photos);
		post.updatePhotos(photos);
	}

	public void changeStatus(String postId, String userId, StockStatus stockStatus) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.setStockStatus(stockStatus);
	}
}
