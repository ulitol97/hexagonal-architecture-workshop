# Workshop Arquitectura Hexagonal

Esta aplicación representa un sistema de inventariado de objetos.
El dominio se centra en mantener un catálogo simple de items y cubrir su ciclo de vida básico:
alta (create), consulta (read), actualización (update) y baja (delete), aplicando reglas básicas de negocio sobre la
información inventariada.

Los puntos de interacción con el exterior, fuera de nuestro dominio y negocio son:
- API REST: controlador recibe peticiones sobre los ítems.
- BD: base de datos en memoria almacena el estado de los ítems.

## Ramas del taller
El material del taller está organizado por ramas:

- `01-item-base`: proyecto inicial con arquitectura por capas básica.
- `02-item-3layers`: evolución a arquitectura de 3 capas.
- `03-item-hexagonal`: primer acercamiento a arquitectura hexagonal.

## Uso recomendado

```bash
git checkout 01-item-base
```
