CREATE EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TABLE media (
                       id UUID PRIMARY KEY UNIQUE NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       category VARCHAR(100),
                       media_url VARCHAR(255),
                       creation_date TIMESTAMP,
                       type VARCHAR(20) NOT NULL,
                       user_id UUID NOT NULL
);

CREATE TABLE question (
                          id UUID PRIMARY KEY UNIQUE NOT NULL,
                          media_id UUID NOT NULL,
                          description TEXT NOT NULL,
                          value DOUBLE PRECISION NOT NULL,
                          CONSTRAINT fk_media FOREIGN KEY(media_id) REFERENCES media(id) ON DELETE CASCADE
);

CREATE TABLE alternative (
                             id UUID PRIMARY KEY UNIQUE NOT NULL,
                             question_id UUID NOT NULL,
                             text VARCHAR(255) NOT NULL,
                             is_correct BOOLEAN NOT NULL,
                             CONSTRAINT fk_question FOREIGN KEY(question_id) REFERENCES question(id) ON DELETE CASCADE
);

CREATE TABLE users (
                       id UUID PRIMARY KEY UNIQUE NOT NULL,
                       login TEXT NOT NULL UNIQUE,
                       password TEXT NOT NULL,
                       role TEXT NOT NULL
);