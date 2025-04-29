# 📚 Bank Application - Microservice Architecture

## 🛠 Tecnologías usadas

- **Backend:** Spring Boot 3, Java 17, PostgreSQL
- **Frontend:** Angular 17 (Standalone Components)
- **Docker:** Backend dockerizado con Dockerfile
- **Testing:** Pruebas unitarias en backend (pendiente frontend)
- **Base de datos:** PostgreSQL (localhost:5432)
- **Estilos:** CSS personalizado (no se utilizó framework de UI)

---

## 🚀 Descripción del Proyecto

Aplicación bancaria de arquitectura de microservicios que permite la gestión de clientes, cuentas y transacciones. Además, ofrece generación de reportes en formato JSON y PDF (base64).

Incluye:

- Gestión de Customers, Accounts y Transactions.
- Validaciones de saldo disponible en transacciones.
- Generación de reportes de movimientos por cliente y rango de fechas.
- Dockerización del backend para facilitar el despliegue.

---

## ⚙️ Instalación y Ejecución

### 1. Backend + Base de Datos con Docker

- Asegúrate de tener **Docker** instalado.
- Ejecuta desde la raíz del proyecto:

```bash
docker-compose up --build
```

- El backend estará disponible en:  
  👉 `http://localhost:8080`

- La base de datos PostgreSQL estará en:  
  👉 `localhost:5432` (base de datos: `bankdb`, usuario: `postgres`, password: `postgres`)

### 2. Frontend (Angular)

- Desde la carpeta del proyecto frontend:

```bash
npm install
ng serve --open
```

- El frontend estará disponible en:  
  👉 `http://localhost:4200`

---

## 📄 Endpoints RESTful disponibles

| Entidad     | Endpoint Base       |
| ----------- | ------------------- |
| Customer    | `/api/customers`    |
| Account     | `/api/accounts`     |
| Transaction | `/api/transactions` |
| Reporte     | `/api/reports`      |

---

## 📋 Funcionalidades principales

### Backend

- **CRUD completo** de Customers, Accounts y Transactions.
- **Movimientos** positivos (DEPÓSITO) y negativos (RETIRO).
- **Validación automática** de saldo antes de aceptar retiros.
- **Excepciones controladas** y mensajes de error claros.
- **Exportación de reportes** en formato JSON y PDF.

### Frontend

- **Gestión visual** de Customers, Accounts y Transactions.
- **Módulo de reportes** con descarga de PDF.
- **Validaciones** en formularios de registro y edición.
- **Alertas** para manejo de errores.
- **Consumo de servicios** mediante `customer.service.ts`, `account.service.ts`, `transaction.service.ts`, `report.service.ts`.

---

## 🐳 Dockerización

- **Dockerfile** con etapa de construcción y ejecución automática del backend.
- **docker-compose** lanza la base de datos PostgreSQL y el backend en un solo paso.
- Ejecuta todo con:

```bash
docker-compose up --build
```

---

## 📂 Estructura del Proyecto

```
backend/
├── src/main/java/com/yourcompany/bankapp
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── BankappApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── static/
└── Dockerfile

docker-compose.yml

frontend/
├── src/app/
│   ├── features/
│   │   ├── customers/
│   │   ├── accounts/
│   │   ├── transactions/
│   │   └── reports/
│   ├── services/
│   └── app.component.ts
└── angular.json
```

---

## 📈 Próximos pasos sugeridos

- Implementar **pruebas unitarias** en el frontend.
- Mejorar seguridad (JWT para autenticación).
- Implementar manejo de roles (admin, user).
- Deploy en servicios cloud (AWS, Azure, etc).

---

## 📜 Licencia

Este proyecto es de uso educativo para demostraciones técnicas.\
Siéntete libre de utilizarlo y expandirlo.

