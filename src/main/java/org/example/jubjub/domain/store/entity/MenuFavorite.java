package org.example.jubjub.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.user.entity.MemberProfile;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "menu_favorites",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_profile_menu", // 동일 메뉴 중복 찜 방지
                        columnNames = {"member_profile_id", "menu_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MenuFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id", nullable = false)
    private MemberProfile memberProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 찜한 시간

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}