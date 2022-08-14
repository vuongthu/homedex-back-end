alter table user_household_mappings
    drop constraint user_household_mappings_fk_households_id_fkey,
    add constraint user_household_mappings_fk_households_id_fkey
        foreign key (fk_households_id) references households (id) on delete cascade;

alter table user_household_mappings
    drop constraint user_household_mappings_fk_users_id_fkey,
    add constraint user_household_mappings_fk_users_id_fkey
        foreign key (fk_users_id) references users (id) on delete cascade;

alter table categories
    drop constraint categories_fk_households_id_fkey,
    add constraint categories_fk_households_id_fkey
        foreign key (fk_households_id) references households (id) on delete cascade;

alter table items
    drop constraint items_fk_categories_id_fkey,
    add constraint items_fk_categories_id_fkey
        foreign key (fk_categories_id) references categories (id) on delete cascade;