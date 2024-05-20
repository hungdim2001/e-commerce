package com.example.core.service;

import com.example.core.entity.Order;
import com.example.core.entity.Product;
import com.example.core.entity.Rating;
import com.example.core.exceptions.NotFoundException;
import com.example.core.repository.OrderRepository;
import com.example.core.repository.ProductRepository;
import com.example.core.repository.RatingRepository;
import com.example.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    public String createComment(Rating rating) {
        Order order = orderRepository.getOrderByUserIdAndVariantId(rating.getUserId(), rating.getProductId());
        Rating ratingExits = ratingRepository.getRatingByUserIdAndProductId(rating.getUserId(), rating.getProductId());
        if (Utils.isNullOrEmpty(order)) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST, "Must order to comment");
        }
        if (!Utils.isNullOrEmpty(ratingExits)) {
            throw new NotFoundException(HttpStatus.BAD_REQUEST, "You already commented");
        }
        ratingRepository.save(rating);
        return null;
    }
}
