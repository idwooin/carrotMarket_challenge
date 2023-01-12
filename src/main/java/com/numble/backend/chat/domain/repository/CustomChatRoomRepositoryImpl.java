// package com.numble.backend.chat.domain.repository;
//
// import java.util.List;
//
// import com.numble.backend.post.domain.StockStatus;
// import com.numble.backend.user.mypage.common.dto.response.MyChat;
// import com.numble.backend.user.mypage.common.dto.response.MyChatsResponse;
// import com.querydsl.jpa.impl.JPAQueryFactory;
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// public class CustomChatRoomRepositoryImpl implements CustomChatRoomRepository{
//
// 	private final JPAQueryFactory queryFactory;
// 	@Override
// 	public MyChatsResponse findByBuyer(String userId) {
// 		List<MyChat> myChatList= queryFactory.select(QMyChat(
//
// 		private String nickname;
// 		private String profileUrl;
// 		private String lastChat;
// 		private String postUrl;
// 		private StockStatus stockStatus;
// 			))
// 			.from(chatRoom)
// 			.where(chatRoom.buyer.eq(userId).or(chatRoom.seller.eq(userId)))
// 			.fetch();
//
// 		return new MyChatsResponse(myChatList);
// 	}
//
// 	@Override
// 	public MyChatsResponse findBySeller(String postId) {
// 		return null;
// 	}
// }
