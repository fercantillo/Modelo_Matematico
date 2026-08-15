# Modelo Matemático — Colonia de Atta cephalotes

Simulación discreta del proceso de recolección de material vegetal de una colonia
de hormigas cortadoras (*Atta cephalotes*), modelada como un grafo no dirigido y
ponderado.

Proyecto del curso **Matemática Discreta 2 (FUN-17)** — Universidad CENFOTEC.

## El modelo

El terreno se representa con un grafo de 12 nodos y 15 aristas, donde los pesos
son distancias en metros:

- **H** — hormiguero (nodo raíz)
- **I1, I2, I3** — intersecciones
- **T1, T2, T3** — puntos de tránsito
- **F1 … F5** — posibles fuentes de material vegetal

La simulación avanza en 15 pasos discretos de tiempo. Cada tres pasos aparece una
fuente de alimento en un nodo F aleatorio (`t mod 3 = 0`), y cada cuatro pasos se
evalúa la aparición de un obstáculo en la conexión H–I2 (`t mod 4 = 0`). La
hormiga H1 inicia en H con 100 unidades de energía, se desplaza un nodo por paso
usando BFS para encontrar ruta, consume 10 unidades por movimiento y debe
regresar al hormiguero antes de aceptar una nueva tarea.

## Estructura

    src/com/cenfotec/hormigas/
    ├── grafo/
    │   ├── Nodo.java              vértice del grafo
    │   ├── Arista.java            conexión con peso en metros
    │   └── Grafo.java             lista de adyacencia + construirGrafoColonia()
    ├── Hormiga.java               posición, energía, estado, fuente asignada
    ├── FuenteAlimento.java        nodo destino y estado de recolección
    ├── Simulacion.java            motor de pasos discretos
    └── Main.java                  construye el grafo y lanza la simulación

## Cómo compilar y ejecutar

El proyecto usa únicamente la biblioteca estándar de Java. No requiere Maven,
Gradle ni dependencias externas.

**Windows (PowerShell)**

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src | % { $_.FullName })
java -cp out com.cenfotec.hormigas.Main
```

**Mac / Linux (bash)**

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.cenfotec.hormigas.Main
```

Desde IntelliJ IDEA o Eclipse basta con marcar `src` como *source root* y
ejecutar `Main`.

> Si los acentos se ven mal en la consola de Windows, ejecutá `chcp 65001` antes
> de `java`, o agregá el parámetro `-Dfile.encoding=UTF-8`.

## Salida

El programa imprime primero una verificación del grafo (cantidad de nodos,
vecinos con sus pesos, grado de algunos nodos y si el grafo es conexo) y luego
el detalle paso a paso de la simulación, cerrando con un resumen de fuentes
generadas, recolectadas y pendientes.

Como la ubicación de las fuentes y la aparición del obstáculo son aleatorias,
cada ejecución produce recorridos distintos.

## Autores

- Johanna Benítez Blandón
- María Fernanda Cantillo Flores
- José Fabio Barquero Gutiérrez
- Valeria Díaz Bastos
