package org.bamappli.telefonibackend.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PhoneResponseDto {
    private String title;
    private String description;
    private Double price;
    private String condition;  // Correspond au statut de l'annonce
    private String sellerName;
    private String sellerType;
    private List<String> images;
    private boolean isLiked;
    private int totalLikes;

    public PhoneResponseDto(String title, String description, Double price, String condition, String sellerName, String sellerType, List<String> images, boolean isLiked, int totalLikes) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.sellerName = sellerName;
        this.sellerType = sellerType;
        this.images = images;
        this.isLiked = isLiked;
        this.totalLikes = totalLikes;
    }

    // Getters and setters...
}

