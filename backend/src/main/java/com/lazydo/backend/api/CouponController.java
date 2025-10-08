package com.lazydo.backend.api;

import com.lazydo.backend.service.CouponService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/redeem/{userId}")
    public Map<String, String> redeemCoupon(@PathVariable Long userId, @RequestBody Map<String, String> body) {
        String code = body.get("code");
        String result = couponService.redeemCoupon(userId, code);
        return Map.of("message", result);
    }
}
