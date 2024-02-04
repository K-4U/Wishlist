CREATE TABLE IF NOT EXISTS wishlist.beckers_user (
    id            INT AUTO_INCREMENT
        PRIMARY KEY,
    email         VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    pass_hash     VARCHAR(255) NULL,
    date_of_birth DATETIME     NULL,
    avatar_name   VARCHAR(255) NULL
)
    ENGINE = MyISAM;

CREATE TABLE IF NOT EXISTS wishlist.password_token (
    id      INT AUTO_INCREMENT
        PRIMARY KEY,
    token   VARCHAR(255) NULL,
    user_id INT          NOT NULL
)
    ENGINE = MyISAM;

CREATE INDEX FKeypkdtctvb1p7c0y0usaior76
    ON wishlist.password_token(user_id);

CREATE TABLE IF NOT EXISTS wishlist.wishlist (
    id        INT AUTO_INCREMENT
        PRIMARY KEY,
    icon      VARCHAR(255) NULL,
    list_name VARCHAR(255) NULL,
    owner_id  INT          NOT NULL
)
    ENGINE = MyISAM;

CREATE INDEX FK50elg10fjjjljs63vsqnbo5dq
    ON wishlist.wishlist(owner_id);

CREATE TABLE IF NOT EXISTS wishlist.wishlist_item (
    id              INT AUTO_INCREMENT
        PRIMARY KEY,
    added_on        DATETIME     NOT NULL,
    description     VARCHAR(255) NOT NULL,
    price           DOUBLE       NOT NULL,
    purchase_event  INT          NULL,
    purchased_on    DATETIME     NULL,
    url             VARCHAR(255) NOT NULL,
    owner_id        INT          NOT NULL,
    purchased_by_id INT          NULL,
    wishlist_id     INT          NOT NULL,
    deleted         BIT          NOT NULL,
    remarks         TEXT         NULL
)
    ENGINE = MyISAM;

CREATE INDEX FK3o7nq3tsvge6sse87fsnviops
    ON wishlist.wishlist_item(purchased_by_id);

CREATE INDEX FK5iw5sajivrxnt4qjxqlgo8yb1
    ON wishlist.wishlist_item(wishlist_id);

CREATE INDEX FKph12aab60ly59m3me1627hrw8
    ON wishlist.wishlist_item(owner_id);

