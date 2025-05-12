package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.ReminderPatchDto;
import com.belejki.belejki.restful.dto.WishDto;
import com.belejki.belejki.restful.dto.WishPatchDto;
import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.Wish;
import com.belejki.belejki.restful.exception.WishNotFoundException;
import com.belejki.belejki.restful.repository.WishRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public Wish update(Wish wish, @Valid WishDto dto) {
        wish.setDescription(dto.getDescription());
        wish.setApproximatePrice(dto.getApproximatePrice());
        wish.setLink(dto.getLink());
        wishRepository.save(wish);
        return wish;
    }

    public Wish patchWish(Long id, @Valid WishPatchDto dto) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new WishNotFoundException("Wish not found for id: " + id));

        // Update only fields that are provided
        if (dto.getDescription() != null) {
            wish.setDescription(dto.getDescription());
        }

        if (dto.getApproximatePrice() != null) {
            wish.setApproximatePrice(dto.getApproximatePrice());
        }

        if (dto.getLink() != null) {
            wish.setLink(dto.getLink());
        }

        return wishRepository.save(wish);
    }
}
