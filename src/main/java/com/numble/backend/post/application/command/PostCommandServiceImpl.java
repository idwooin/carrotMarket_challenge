package com.numble.backend.post.application.command;

import java.util.ArrayList;
import java.util.List;

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
import com.numble.backend.post.exception.PostNotFoundException;
import com.numble.backend.user.auth.domain.UserInfo;
import com.numble.backend.user.auth.exception.UserNotFoundException;
import com.numble.backend.user.auth.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
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
	@Transactional
	public void create(PostCreateRequest postCreateRequest, String userId, List<MultipartFile> multipartFiles) {
		UserInfo user = userInfoRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		System.out.println("postCreateRequest.getContents() = " + postCreateRequest.getContents());

		Post post = new Post(
			postCreateRequest.getTitle(),
			postCreateRequest.getStockCategory(),
			postCreateRequest.getPrice(),
			postCreateRequest.getContents(),
			user
		);

		uploadFiles(multipartFiles, post);

		postRepository.save(post);
	}

	@Override
	@Transactional
	public void delete(String postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.validateAuthor(userId);

		postRepository.deleteById(postId);
	}

	@Override
	public void likePost(String postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.likePost(userId);
	}

	@Override
	public void unlikePost(String postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.unlikePost(userId);
	}

	@Transactional
	public void uploadFiles(List<MultipartFile> multipartFiles, Post post) {
		if (multipartFiles == null) {
			return;
		}

		List<String> fileNames = s3Utils.uploadMultiFilesS3(
			amazonS3Client,
			bucketName,
			multipartFiles,
			MAX_IMAGE_COUNT);

		List<Photo> photos = new ArrayList<>();

		for (String fileName : fileNames) {
			photos.add(new Photo(amazonS3Client.getUrl(bucketName, fileName).toString()));
		}

		photoRepository.saveAll(photos);
		post.updatePhotos(photos);
	}

	@Transactional
	public void changeStatus(String postId, String userId, StockStatus stockStatus) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		post.setStockStatus(stockStatus);
	}
}
