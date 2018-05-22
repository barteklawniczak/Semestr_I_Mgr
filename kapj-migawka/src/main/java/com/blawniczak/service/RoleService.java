package com.blawniczak.service;

import com.blawniczak.domain.Role;

public interface RoleService {

    Role findByName(String name);
}
