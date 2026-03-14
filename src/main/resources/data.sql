INSERT INTO categories (name, icon_url, display_order, is_active, created_at, updated_at)
VALUES ('한식', 'https://example.com/korean.png', 1, true, NOW(), NOW());

INSERT INTO categories (name, icon_url, display_order, is_active, created_at, updated_at)
VALUES ('일식', 'https://example.com/japanese.png', 2, true, NOW(), NOW());

INSERT INTO categories (name, icon_url, display_order, is_active, created_at, updated_at)
VALUES ('카페/디저트', 'https://example.com/cafe.png', 3, true, NOW(), NOW());

INSERT INTO stores (category_id, name, address, phone, status, base_cooking_time_min, created_at, updated_at)
VALUES (1, '준호네 김치찜', '서울시 강남구...', '02-123-4567', 'OPEN', 20, NOW(), NOW());

INSERT INTO menus (store_id, name, price, description, is_sold_out, created_at, updated_at)
VALUES (1, '우삼겹 김치찜', 12000, '대표 메뉴입니다.', false, NOW(), NOW());
