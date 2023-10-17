package br.com.lucaplaster.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{

    UserModel findByUsername(String username);      // Cria o método automatico passando o atributo
}
