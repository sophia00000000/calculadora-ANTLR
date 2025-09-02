# calculadora-ANTLR

# Building a Calculator Using a Visitor

Implementación de una calculadora básica que peude soportar las siguiente operaciones:

a. Calcular: ` sin(x)`, `cos(x)`, `tan(x)`

b. Raíz cuadrada usando `sqrt(x)`

c. Logaritmo natura `ln(x)` y Logaritmo en base 10 `log(x)`

d. Uso de grados y de radianes `mode radianes` o  `mode grados`

e. Calcule el factorial de un numero usando `!` ejemplo `5!=120`


- Las funciones trigonométricas est+an inicialmente en grados.
- Soporta paréntesis anidados y precedencia de operadores.
  
---

### Compilar y ejecutar
Generar los archivos de ANTLR4


        antlr4 -no-listener -visitor LabeledExpr.g4 # -visitor is required!!!


        
Compilar los archivos Java:
  

        javac Calc.java LabeledExpr*.java
  

Ejecutar el programa y recibir entradas por consola:
  

        java Calc
  
Para salir, presiona Ctrl + D o Ctrl + C.

Ejecutar el programa con un archivo de entrada


        java Calc t.expr

---


## Casos de prueba

<img width="1049" height="704" alt="image" src="https://github.com/user-attachments/assets/017abba5-9d17-43bf-9031-872d808b49ef" />
