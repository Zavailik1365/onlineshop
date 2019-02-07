insert into usr (id, username, password, active, fullname, email)
values (1, 'Administrator', '$2a$08$J7eu9fiT0sv3aQFYJOsLHOA7bzNEm8KbgKOujI0SAD31138bptrne',
true, 'Administrator', 'administrator@onlineshop.com');

insert into user_role (user_id, roles)
values (1, 'ROLE_USER'), (1, 'ROLE_ADMIN');