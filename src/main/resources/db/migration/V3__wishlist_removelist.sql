alter table wishlist_item
    modify wishlist_id int null;

update wishlist_item
set wishlist_id = null
where deleted = 1;