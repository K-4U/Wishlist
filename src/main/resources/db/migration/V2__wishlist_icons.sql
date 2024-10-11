USE wishlist;
alter table wishlist
    modify icon varchar(255) null;
UPDATE wishlist
SET icon = 'feather'
WHERE icon = 'FEATHER';
UPDATE wishlist
SET icon = 'pine-tree'
WHERE icon = 'TREE';
UPDATE wishlist
SET icon = 'broom'
WHERE icon = 'QUIDDITCH';
UPDATE wishlist
SET icon = 'gift'
WHERE icon = 'GIFT';