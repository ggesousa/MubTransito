package com.example.mubtransito.roomDatabase.DAOs;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mubtransito.roomDatabase.entities.Usuario;

import java.util.List;

@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM usuario WHERE id=:id")
    Usuario getUserById(Long id);

    @Query("SELECT * FROM usuario")
    List<Usuario> getAllUsuarios();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insert(Usuario... usuarios);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Usuario usuario);

    @Update
    void update(Usuario... usuarios);

    @Delete
    void delete(Usuario... usuarios);
}
