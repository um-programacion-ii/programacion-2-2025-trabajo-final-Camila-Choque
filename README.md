[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IEOUmR9z)

# Trabajo Final – Programación II

## 👩‍🎓 Datos del la Estudiante

- **Nombre y apellido:** Mariela Camila Zoe Choque  
- **Legajo:** 62069 
- **Email institucional:** m.choque@alumno.um.edu.ar
- **Docente:** Daniel Quinteros y Fernando Villarreal
  
## 📌 Información General

- **Materia:** Programación II  
- **Carrera:** Ingeniería en Informática  
- **Universidad:** Universidad de Mendoza  
- **Año:** 2025  


## 🎯 Objetivo del Proyecto

El objetivo del proyecto es desarrollar un sistema distribuido que permita la gestión de eventos y ventas asociadas a usuarios autenticados, incorporando comunicación entre servicios, persistencia de datos, mensajería asincrónica mediante Kafka y el uso de Redis para optimizar el manejo de información temporal, simulando un entorno real de sistemas escalables y desacoplados.

## 🧩 Componentes del Sistema

**Back:**  
Gestiona la lógica de negocio del sistema, administra usuarios, eventos y ventas, ademas se comunica con el servicio de la cátedra y con el proxy para el intercambio de información.

**Proxy:**  
Actúa como intermediario, siendo el único componente con acceso a Kafka y Redis.

**Cliente móvil:**  
Aplicación desarrollada en Kotlin Multiplatform (KMP) que permite a los usuarios autenticarse, visualizar eventos y realizar compras.
## 🔧 Kafka y Redis

| Tecnología | ¿Para qué se usa? |
|-----------|------------------|
| **Kafka** | Mensajería asincrónica entre componentes del sistema mediante eventos, permitiendo una comunicación desacoplada y escalable. |
| **Redis** | Almacenamiento en memoria de datos temporales (por ejemplo, estado y bloqueo de asientos), optimizando el rendimiento del sistema. |

## ⚙️ Ejecucion
### ▶️ Clonar el repositorio:
    git clone git@github.com:um-programacion-ii/programacion-2-2025-trabajo-final-Camila-Choque.git
### ▶️ Navegar al directorio Backend
    cd Backend/
### ▶️ Controlamos con el siguiente comando que kafka y zookeeper esten funcionando
    sudo docker ps -a
### ▶️ En caso de que no esten funcionando ejecutar el siguiente comando
    sudo docker start kafka
    sudo docker start zookeeper
### ▶️ Ejecutamos el backend
      ./mvnw
### ▶️ En otra terminal dentro del proyecto navegamos al directorio Proxy
     cd Proxy/
### ▶️ Ejecutamos el proxy
      mvn spring-boot:run
### ▶️ Abrimos la carpeta frontend/ en Android Studio y ejecutamos el emulador




