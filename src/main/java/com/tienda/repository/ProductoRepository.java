package com.tienda.repository;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    // Consulta derivada que recupera los productos de un rango de precio y los ordena por precio ascendentemente
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);

    // Consulta JPQL que recupera los productos de un rango de precio y los ordena por precio ascendentemente
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(@Param("precioInf") double precioInf,
                                       @Param("precioSup") double precioSup);

    // Consulta SQL que recupera los productos de un rango de precio y los ordena por precio ascendentemente
    @Query(nativeQuery = true,
           value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(double precioInf, double precioSup);

    // Consulta ampliada: productos con existencias menores o iguales a la cantidad indicada
    public List<Producto> findByExistenciasLessThanEqualOrderByExistenciasAsc(Integer existencias);

}