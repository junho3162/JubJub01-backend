package org.example.jubjub.domain.favorite.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jubjub.domain.store.entity.Store;
import org.example.jubjub.domain.user.entity.MemberProfile;
import org.example.jubjub.global.common.BaseTimeEntity;

@Entity
@Table(name = "store_favorites")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StoreFavorite extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id", nullable = false)
    private MemberProfile memberProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}