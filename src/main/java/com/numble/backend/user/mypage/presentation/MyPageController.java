package com.numble.backend.user.mypage.presentation;

import static com.numble.backend.common.dto.ResponseEnum.MYCHATS_FIND_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.MYLIKES_FIND_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.MYPAGE_FIND_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.MYPAGE_UPDATE_SUCCESS;
import static com.numble.backend.common.dto.ResponseEnum.MYSTOCK_FIND_SUCCESS;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.numble.backend.common.dto.Response;
import com.numble.backend.post.common.dto.response.ProductPageResponse;
import com.numble.backend.user.auth.annotation.CurrentUser;
import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.mypage.common.dto.request.UpdateMyPageRequest;
import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
import com.numble.backend.user.mypage.common.dto.response.MyPageResponse;
import com.numble.backend.user.mypage.facade.MyPageFacadeGateway;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypages")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageFacadeGateway myPageService;

	@GetMapping
	public ResponseEntity<Response<MyPageResponse>> showMyPage(@CurrentUser CustomUser user) {
		MyPageResponse myPageResponse = myPageService.findByUserId(user.getId());

		return new ResponseEntity<>(new Response<>(MYPAGE_FIND_SUCCESS, myPageResponse), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Response<Void>> updateMyPage(
		@CurrentUser CustomUser user,
		@Valid @RequestBody UpdateMyPageRequest updateMyPageRequest,
		MultipartFile multipartFile) {
		myPageService.updateByUserId(updateMyPageRequest, user.getId(), multipartFile);

		return new ResponseEntity<>(new Response<>(MYPAGE_UPDATE_SUCCESS, null), HttpStatus.OK);
	}

	@GetMapping("/stock")
	public ResponseEntity<Response<ProductPageResponse>> findMyStock(
		@CurrentUser CustomUser user,
		@PageableDefault(size=10) Pageable pageable) {
		ProductPageResponse productPageResponse = myPageService.findMyStock(user.getId(), pageable);

		return new ResponseEntity<>(new Response<>(MYSTOCK_FIND_SUCCESS, productPageResponse), HttpStatus.OK);
	}

	@GetMapping("/likes")
	public ResponseEntity<Response<ProductPageResponse>> findMyLikes(
		@CurrentUser CustomUser user,
		@PageableDefault(size=10) Pageable pageable) {
		ProductPageResponse productPageResponse = myPageService.findMyLikes(user.getId(), pageable);

		return new ResponseEntity<>(new Response<>(MYLIKES_FIND_SUCCESS, productPageResponse), HttpStatus.OK);
	}

	@GetMapping("/chats")
	public ResponseEntity<Response<MyChatsResponse>> findMyChats(@CurrentUser CustomUser user) {
		MyChatsResponse myPageResponse = myPageService.findMyChats(user.getId());

		return new ResponseEntity<>(new Response<>(MYCHATS_FIND_SUCCESS, myPageResponse), HttpStatus.OK);
	}
}
