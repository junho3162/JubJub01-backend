-- 카테고리
INSERT INTO categories (name, icon_url, display_order, is_active, created_at, updated_at) VALUES ('한식', 'url', 1, true, NOW(), NOW());

-- 1번 매장 & 메뉴 (준호네 김치찜)
INSERT INTO stores (category_id, name, address, phone, status, base_cooking_time_min, created_at, updated_at) VALUES (1, '준호네 김치찜', '서울시', '02-123-4567', 'OPEN', 20, NOW(), NOW());
INSERT INTO menus (store_id, name, price, description, is_sold_out, created_at, updated_at) VALUES (1, '우삼겹 김치찜', 12000, '대표 메뉴', false, NOW(), NOW());

-- 2번 매장 & 메뉴 (맘스터치)
INSERT INTO stores (category_id, name, address, phone, status, base_cooking_time_min, created_at, updated_at) VALUES (1, '맘스터치', '서산시', '041-000-0000', 'OPEN', 15, NOW(), NOW());
INSERT INTO menus (store_id, name, price, description, is_sold_out, created_at, updated_at) VALUES (2, '싸이버거', 5400, '국룰 메뉴', false, NOW(), NOW());