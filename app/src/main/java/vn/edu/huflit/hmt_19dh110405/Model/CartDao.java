package vn.edu.huflit.hmt_19dh110405.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM Cart")
    List<Cart> getAll();

    @Insert
    void insertAll(Cart... carts);

    @Insert
    void insertCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);

    @Update
    void updateCart(Cart cart);

    @Delete
    void deleteMultiCart(Cart... cart);

    @Query("DELETE FROM Cart")
    void delete();

}
