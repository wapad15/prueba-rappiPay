# Mobile Automation Framework (Appium + Java)

Este proyecto define un framework de automatización mobile orientado a **soporte multiplataforma (Android/iOS)**, **reutilización**, **escalabilidad** y **mantenibilidad**.  
La arquitectura se basa en el patrón **Screen Object** para encapsular la interacción con cada pantalla y mantener los tests limpios y fáciles de evolucionar.

---

## Stack Tecnológico (decisión del proyecto)

- **Java**: lenguaje principal por estabilidad y compatibilidad con el ecosistema de automatización.
- **Appium**: herramienta de automatización mobile que permite ejecutar pruebas en Android e iOS con una arquitectura común.
- **TestNG**: runner de pruebas para manejo de suites (smoke/regression), grupos y control de ejecución (incluyendo paralelismo si el proyecto lo requiere).
- **Maven (`pom.xml`)**: gestión de dependencias y ejecución estandarizada en entornos CI/CD.
- **Logging: SLF4J + Logback**: SLF4J como fachada y Logback como implementación por facilidad de configuración y uso común en Java (sin acoplar el código a una sola librería).

---

## Estructura del proyecto (arquetipo) esquema en formato "ASCII"

mobile-automation-framework/
├─ README.md
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ framework/
│  │  │     ├─ config/
│  │  │     │  ├─ ConfigManager.java
│  │  │     │  ├─ Platform.java
│  │  │     │  └─ environments/
│  │  │     │     ├─ android.properties
│  │  │     │     └─ ios.properties
│  │  │     ├─ driver/
│  │  │     │  ├─ DriverFactory.java
│  │  │     │  ├─ DriverManager.java
│  │  │     │  └─ capabilities/
│  │  │     │     ├─ AndroidCapsBuilder.java
│  │  │     │     └─ IOSCapsBuilder.java
│  │  │     ├─ screens/
│  │  │     ├─ flows/
│  │  │     ├─ utils/
│  │  │     ├─ reporting/
│  │  │     └─ logging/
│  │  └─ resources/
│  │     └─ logback.xml
│  └─ test/
│     └─ java/
│        ├─ tests/
│        └─ suites/
└─ logs/

---

## Explicación de carpetas y archivos clave

### `pom.xml`
Define las dependencias del framework (Appium, TestNG, logging, etc.), la versión de Java (11+) y la forma estándar de ejecutar las pruebas.  
Es un archivo crítico porque garantiza reproducibilidad local y en CI/CD.

---

## `src/main/java/framework/config/`

### `ConfigManager.java`
Centraliza la lectura de configuración (platform, deviceName, app, serverUrl, etc.).  
Evita código quemado y permite cambiar de Android a iOS solo modificando parámetros o properties.

### `Platform.java`
Enum para representar plataformas soportadas: `ANDROID` e `IOS`.  
Esto mantiene las decisiones de plataforma tipadas y controladas, evitando strings sueltos en el código.

### `environments/android.properties` y `environments/ios.properties`
Archivos de propiedades por plataforma. Aquí se definen valores como:
- `platformName`
- `automationName` (UiAutomator2 / XCUITest)
- `deviceName`, `udid`
- `app` (apk/ipa)
- `appiumServerUrl`

La idea es que el cambio de plataforma sea un cambio de configuración, no de código.

---

## `src/main/java/framework/driver/`

### `DriverFactory.java`
Implementa la decisión de creación del driver según plataforma (AndroidDriver / IOSDriver).  
Esta capa permite mantener los tests independientes del detalle técnico de cómo se inicializa el driver.

### `DriverManager.java`
Responsable de almacenar y exponer el driver al resto del framework.  
Permite centralizar el ciclo de vida del driver, y si el proyecto escala a paralelismo, se puede manejar con `ThreadLocal` sin tocar la lógica de tests.

### `capabilities/AndroidCapsBuilder.java` y `capabilities/IOSCapsBuilder.java`
Responsables de construir capabilities específicas por plataforma.  
Separar capabilities en builders evita duplicación y permite cambios por plataforma sin romper el resto del framework.

---

## `src/main/java/framework/screens/`
Capa donde se implementa el patrón **Screen Object**.  
Aquí se crean clases por pantalla para encapsular:
- locators
- acciones (tap, type, scroll)
- validaciones básicas (isDisplayed)

Ejemplos de clases que normalmente vivirían aquí (dependen del contexto real del producto):
- `HomeAppScreen.java`
- `LoginScreen.java`
- `ProductHomeScreen.java`

> Estas clases son propias del producto y pueden cambiar por proyecto, pero la carpeta y el patrón se mantienen.

---

## `src/main/java/framework/flows/`
Capa opcional orientada a lógica de negocio reusable.  
Permite agrupar secuencias de acciones que combinan múltiples pantallas para evitar duplicación en tests.

Ejemplos típicos (según el producto):
- `LoginFlow.java`
- `CheckoutFlow.java`
- `ProductFlow.java`

---

## `src/main/java/framework/utils/`
Utilidades técnicas reutilizables:
- esperas explícitas centralizadas
- helpers de interacción comunes
- captura de evidencias

Ejemplos típicos:
- `WaitUtils.java`
- `ScreenshotUtils.java`

---

## `src/main/java/framework/reporting/`
Capa para integrar listeners y evidencias de ejecución.  
Aquí suele centralizarse la captura de screenshots en fallos y generación de reportes si se requiere (Allure/Extent).

---

## `src/main/java/framework/logging/`
Capa para estandarizar logging.  
Se usa **SLF4J** en el código y la configuración se controla vía `logback.xml`, lo cual permite trazabilidad por consola/archivo sin tocar clases de tests.

---

## `src/main/resources/logback.xml`
Configuración del logging:
- formato de logs
- nivel (INFO/DEBUG)
- salida a archivo en `logs/`
Esto asegura trazabilidad tanto local como en CI.

---

## `src/test/java/tests/`
Capa de pruebas. Aquí se implementan los casos, manteniendo la lógica técnica fuera del test.  
Normalmente existe un `BaseTest` que hace setup/teardown del driver y carga configuración.

Ejemplos típicos:
- `BaseTest.java`
- `LoginSmokeTest.java`
- `ProductRegressionTest.java`

---

## `src/test/java/suites/`
Suites TestNG (XML) para controlar ejecución por tipo:
- `testng-smoke.xml`
- `testng-regression.xml`

Esto permite ejecutar subsets de pruebas y evitar correr todo siempre.

---

## `/logs/`
Salida estándar de ejecución:
- logs
- evidencias si se agregan (screenshots)
- trazabilidad para análisis de fallos

---

## Cómo esta arquitectura cumple los requisitos

- **Soporte multiplataforma (Android/iOS):** `Platform + ConfigManager + DriverFactory + CapsBuilders + properties por plataforma`.
- **Reutilización:** `screens` encapsula UI, `utils/flows` reducen duplicación en tests.
- **Escalabilidad:** se agregan nuevas pantallas y tests sin impactar el core (config/driver).
- **Java 11+:** definido como estándar del proyecto y configurado en `pom.xml`.
- **TestNG:** suites, grupos y control de ejecución (incluyendo paralelismo si se requiere).
- **Appium:** driver centralizado y capabilities por plataforma.
- **Maven:** dependencias y ejecución reproducible en cualquier entorno.
- **Logging (SLF4J + Logback):** trazabilidad controlada por configuración, sin acoplar el código.

---
## Patrones aplicados
- Screen Object: encapsula locators y acciones por pantalla.
- Factory: creación de driver por plataforma (Android/iOS).
- Builder: construcción de capabilities por plataforma.
- Separation of concerns: config/driver/screens/tests separados.

---
## Selección de plataforma
La plataforma se define por parámetro de ejecución:
- -Dplatform=android
- -Dplatform=ios

---
## Logs y evidencias
- Logging con SLF4J + Logback (consola + archivo en /logs).
- En caso de fallo, se captura screenshot automáticamente y se guarda en /logs (o /reports).
- El listener/hook del runner registra el error con el nombre del test y la pantalla objetivo.

---

## Ejecución de pruebas Con ejemplo de acuerdo a las estrategias de plataformas escalabilidad y mantenibilidad
Smoke:
mvn test -DsuiteXmlFile=src/test/java/suites/testng-smoke.xml -Dplatform=android

Regression:
mvn test -DsuiteXmlFile=src/test/java/suites/testng-regression.xml -Dplatform=ios

---
## Escalabilidad y Mantenibilidad

La arquitectura del framework está diseñada para permitir el crecimiento del proyecto sin afectar la estabilidad de las pruebas existentes.

- **Separación de responsabilidades:** La división en capas (config, driver, screens, flows, utils, tests) evita que cambios en una parte del sistema impacten el resto.
- **Screen Object Pattern:** Los cambios en la interfaz de usuario se manejan dentro de la clase de la pantalla correspondiente, sin modificar los tests.
- **Driver desacoplado:** La creación del driver está centralizada en `DriverFactory` y `DriverManager`, lo que permite soportar nuevas plataformas o configuraciones sin alterar los casos de prueba.
- **Configuración externa:** El uso de archivos `.properties` por plataforma permite cambiar dispositivos, apps o servidores sin modificar código.
- **Reutilización de componentes:** Utilidades, flujos y helpers reducen duplicación de código cuando el número de pruebas crece.
- **Suites de ejecución:** La organización por suites (smoke, regression, etc.) permite ampliar el set de pruebas sin afectar ejecuciones críticas.
- **Logging y evidencias centralizadas:** Facilitan el mantenimiento y el análisis de fallos cuando el proyecto escala.

---
