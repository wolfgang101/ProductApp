package com.rakuten.training.service;

import com.rakuten.training.domain.Review;

public interface ReviewService {

	int addNewReview(Review toBeAdded,  int productId);

	void removeReview(int id);
	
	Review findReviewById(int id);


}
