package com.lazydo.backend.service;

import com.lazydo.backend.domain.Coupon;
import com.lazydo.backend.domain.User;
import com.lazydo.backend.repository.CouponRepository;
import com.lazydo.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public CouponService(CouponRepository couponRepository, UserRepository userRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    public String redeemCoupon(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid coupon"));

        if (!coupon.isActive() || coupon.getUsesLeft() <= 0) {
            return "Coupon expired or already used.";
        }

        // Mark user as premium
        user.setPremium(true);
        userRepository.save(user);

        // Reduce coupon usage
        coupon.setUsesLeft(coupon.getUsesLeft() - 1);
        if (coupon.getUsesLeft() == 0) {
            coupon.setActive(false);
        }
        couponRepository.save(coupon);

        return "Coupon redeemed successfully! Premium unlocked for user: " + user.getName();
    }
}
