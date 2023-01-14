package com.numble.backend.post.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.numble.backend.common.domain.BaseEntity;
import com.numble.backend.post.common.dto.request.PostCreateRequest;
import com.numble.backend.post.exception.InvalidAuthorException;
import com.numble.backend.user.auth.domain.CustomUser;
import com.numble.backend.user.auth.domain.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "update post set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	private String ownerId;

	private String title;

	private String region;

	private Point point;

	private String price;

	@Embedded
	private PostLikes likes = new PostLikes();

	@Enumerated(value = EnumType.STRING)
	private StockCategory category;

	@Enumerated(value = EnumType.STRING)
	private StockStatus stockStatus = StockStatus.SELLING;

	@OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Photo> photos = new ArrayList<>();

	@Lob
	private String contents;

	@Column(nullable = false)
	private Boolean deleted = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId")
	private UserInfo userInfo;

	public Post(String title, StockCategory stockCategory, String price, String contents, UserInfo userInfo) {
		this.title = title;
		this.category = stockCategory;
		this.price = price;
		this.contents = contents;
		this.userInfo = userInfo;
		this.ownerId = userInfo.getUserId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return Objects.equals(getId(), post.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	public void validateAuthor(String userId) {
		if (!this.userInfo.getUserId().equals(userId)) {
			throw new InvalidAuthorException();
		}
	}

	public void updatePost(PostCreateRequest postCreateRequest) {
		this.title = postCreateRequest.getTitle();
		this.category = postCreateRequest.getStockCategory();
		this.price = postCreateRequest.getPrice();
		this.contents = postCreateRequest.getContents();
	}

	public void updatePhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Boolean isAuthor(String userId) {
		return userInfo.getId().equals(userId);
	}

	public Integer likeCount() {
		return this.likes.size();
	}

	public Boolean userLiked(String userId) {
		return this.likes.userLiked(userId);
	}

	public void likePost(String userId) {
		this.likes.add(userId);
	}

	public void unlikePost(String userId) {
		this.likes.remove(userId);
	}

	public void setStockStatus(StockStatus stockStatus) {
		this.stockStatus = stockStatus;
	}
}
