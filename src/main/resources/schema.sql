DROP TABLE IF EXISTS users, items, bookings, comments;

CREATE TABLE users (
                         id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         name varchar(100) DEFAULT 'noname',
                         email varchar(100) NOT NULL,
                         CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE items (
                         id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         name varchar(100) NOT NULL,
                         description text,
                         ownerId BIGINT,
                         available boolean,
                         CONSTRAINT item_user_fk FOREIGN KEY (ownerId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE bookings (                       id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       itemId BIGINT,
                       bookerId BIGINT,
                       startTime timestamp,
                       endTime timestamp,
                       status INTEGER,
                       CONSTRAINT bookings_items_fk FOREIGN KEY (itemId) REFERENCES items(id) ON DELETE CASCADE,
                       CONSTRAINT bookings_users_fk FOREIGN KEY (bookerId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        text varchar(100),
                        authorName varchar(100),
                        created timestamp,
                        itemId BIGINT,
                        CONSTRAINT comments_items_fk FOREIGN KEY (itemId) REFERENCES items(id) ON DELETE CASCADE
);
