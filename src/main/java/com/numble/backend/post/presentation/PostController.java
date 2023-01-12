package com.numble.backend.post.presentation;

import static com.numble.backend.common.dto.ResponseEnum.POST_CHANGE_STATUS_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_CREATE_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_DELETE_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_FIND_ALL_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_FIND_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_LIKE_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_UNLIKE_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.POST_UPDATE_SUCCESS;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.dto.Response;
import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.post.common.dto.response.ProductDetailPageResponse;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.post.domain.StockStatus;
import com.numble.backend.post.facade.PostFacadeGateway;
import com.numble.backend.user.auth.annotation.CurrentUser;
import com.numble.backend.user.auth.domain.CustomUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostFacadeGateway postService;

	@PostMapping
	public ResponseEntity<Response<Void>> createPost(
		@Valid @RequestBody PostCreateRequest postCreateRequest,
		@CurrentUser CustomUser user,
		List<MultipartFile> multipartFiles) {
		postService.create(postCreateRequest, user.getId(), multipartFiles);

		return new ResponseEntity<>(new Response<>(POST_CREATE_SUCCESS,null), HttpStatus.CREATED);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Response<Void>> deletePost(@PathVariable("postId") String postId, @CurrentUser CustomUser user) {
		postService.delete(postId, user.getId());

		return new ResponseEntity<>(new Response<>(POST_DELETE_SUCCESS,null), HttpStatus.OK);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<Response<Void>> updatePost(
		@Valid @RequestBody PostCreateRequest postCreateRequest,
		@CurrentUser CustomUser user,
		List<MultipartFile> multipartFiles,
		@PathVariable("postId") String postId
	) {
		postService.update(postCreateRequest, user.getId(), multipartFiles, postId);

		return new ResponseEntity<>(new Response<>(POST_UPDATE_SUCCESS, null), HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Response<ProductDetailPageResponse>> findPost(@PathVariable("postId") String postId) {
		ProductDetailPageResponse productDetailPageResponse= postService.findById(postId);

		return new ResponseEntity<>(new Response<>(POST_FIND_SUCCESS,productDetailPageResponse), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Response<ProductPageResponse>> findPosts(@PageableDefault(size = 10) Pageable pageable) {
		ProductPageResponse productPageResponse = postService.findAll(pageable);

		return new ResponseEntity<>(new Response<>(POST_FIND_ALL_SUCCESS,productPageResponse), HttpStatus.OK);
	}

	@PutMapping("/{postId}/like")
	public ResponseEntity<Response<Void>> likePost(
		@PathVariable String postId,
		@CurrentUser CustomUser user
	) {
		postService.likePost(postId, user.getId());
		return ResponseEntity.ok(new Response<>(POST_LIKE_SUCCESS, null));
	}

	@PutMapping("/{postId}/unlike")
	public ResponseEntity<Response<Void>> unlikePost(
		@PathVariable String postId,
		@CurrentUser CustomUser user
	) {
		postService.unlikePost(postId, user.getId());
		return ResponseEntity.ok(new Response<>(POST_UNLIKE_SUCCESS, null));
	}

	@PutMapping("/{postId}/reserved")
	public ResponseEntity<Response<Void>> changeStatusToReserved(
		@PathVariable String postId,
		@CurrentUser CustomUser user
	) {
		postService.changeStatus(postId, user.getId(), StockStatus.RESERVED);
		return ResponseEntity.ok(new Response<>(POST_CHANGE_STATUS_SUCCESS, null));
	}

	@PutMapping("/{postId}/sold")
	public ResponseEntity<Response<Void>> changeStatusToSold(
		@PathVariable String postId,
		@CurrentUser CustomUser user
	) {
		postService.changeStatus(postId, user.getId(), StockStatus.SOLD);
		return ResponseEntity.ok(new Response<>(POST_CHANGE_STATUS_SUCCESS, null));
	}
}
