package com.numble.backend.common.utils;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

@Service
public class SliceUtils<T> {
	public Slice<T> makeSlice(List<T> response, Pageable pageable) {
		boolean hasNext = false;
		if (response.size() > pageable.getPageSize()) {
			response.remove(pageable.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(response, pageable, hasNext);
	}
}
