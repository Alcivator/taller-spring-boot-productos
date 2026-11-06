package com.example.tienda.service;

import com.example.tienda.exception.ProductoInvalidoException;
import com.example.tienda.exception.ProductoNoEncontradoException;
import com.example.tienda.model.Producto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final Map<Long, Producto> productos = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public ProductoService() {
        // Datos iniciales para poder probar las consultas
        crear(new Producto(null, "Laptop", 850.00, 10));
        crear(new Producto(null, "Mouse inalámbrico", 15.50, 50));
        crear(new Producto(null, "Teclado mecánico", 35.00, 25));
    }

    public List<Producto> listar() {
        return productos.values().stream()
                .sorted(Comparator.comparing(Producto::getId))
                .toList();
    }

    public Producto obtenerPorId(Long id) {
        Producto producto = productos.get(id);
        if (producto == null) {
            throw new ProductoNoEncontradoException(id);
        }
        return producto;
    }

    public Producto crear(Producto producto) {
        if (producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio debe ser mayor a cero");
        }
        if (producto.getStock() < 0) {
            throw new ProductoInvalidoException("El stock no puede ser negativo");
        }
        producto.setId(secuencia.incrementAndGet());
        productos.put(producto.getId(), producto);
        return producto;
    }

    public void eliminar(Long id) {
        if (productos.remove(id) == null) {
            throw new ProductoNoEncontradoException(id);
        }
    }
}
