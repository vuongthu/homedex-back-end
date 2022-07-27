create table users (
    id uuid not null default gen_random_uuid() PRIMARY KEY,
    username text not null,
    email text not null,
    constraint username_email_unique UNIQUE (username, email)
);

create table households (
    id uuid not null default gen_random_uuid() PRIMARY KEY,
    name text not null
);

create table user_household_mappings (
    fk_users_id uuid not null,
    fk_households_id uuid not null,
    FOREIGN KEY (fk_users_id) REFERENCES users(id),
    FOREIGN KEY (fk_households_id) REFERENCES households(id),
    constraint user_household_unique UNIQUE (fk_users_id, fk_households_id)
);

create table categories (
    id uuid not null default gen_random_uuid() PRIMARY KEY,
    name text not null,
    fk_households_id uuid not null,
    FOREIGN KEY (fk_households_id) REFERENCES households(id)
);

create type measurement_type as enum ('quantity', 'scale');

create table items (
    id uuid not null default gen_random_uuid() PRIMARY KEY,
    name text not null,
    measurement measurement_type not null,
    brand text not null,
    add_info text not null,
    expiration timestamp,
    fk_categories_id uuid not null,
    FOREIGN KEY (fk_categories_id) REFERENCES categories(id)
);