# algo3-tp-2022c2

Integrante: Santiago Antón

El objetivo del TP es crear un juego basado en Breakout, desarrollado por Atari y lanzado en 1976. 

Gameplay (arcade): https://www.youtube.com/watch?v=UhAEjDKEHgk

Gameplay (Atari 2600): https://www.youtube.com/watch?v=Cr6z3AyhRr8

El juego consiste en lo siguiente:

- El objetivo es destruir filas de bloques con una bola que es golpeada por una paleta que puede
moverse de izquierda a derecha, y la bola acelera cada vez que es golpeada.

- Destruir los bloques suman puntos, y suman mayor cantidad los bloques de las filas superiores.

- El jugador pierde una vida cada vez que la bola toca la pared inferior, y al perderlas todas
el juego se reinicia. La cantidad de vidas pueden ser 3 o 5.

- La bola no puede pasar por los costados o el techo porque hay paredes que la hacen rebotar.

- La paleta reduce su tamaño al tocar la pared superior.

- En el juego original la bola acelera después de 4 golpes, después de 12, y al golpear bloques
de las cuatro filas superiores; aunque esto puede modificarse para que aumente ligeramente cada
vez que un bloque es golpeado.

- Además, en el juego original solo pueden eliminarse dos pantallas de bloques. Después de esto
no se generan más bloques y el jugador debe reiniciar el juego para seguir jugando. Esto también
es modificable, para admitir mayor cantidad de pantallas.

El patrón de diseño a utilizar es Factory Method: este patrón permite crear diferentes tipos de
objetos Bloque utilizando una clase FabricaDeBloques que se encarga de crearlos tras determinar
su comportamiento por medio de la lectura de un archivo .CSV, como cantidad de golpes antes de
ser destruido, el diseño del bloque y su ubicación.

Instrucciones para configurar el entorno:

El TP utiliza tanto JUnit como JavaFX. El archivo pom.xml contiene las dependencias necesarias
para ejecutar el programa. Para Visual Studio Code las extensiones necesarias son:

- Extension Pack for Java
- JavaFX Support

Instrucciones para ejecutar las pruebas:

Las pruebas son personalizables y no necesitan edición para ser ejecutadas. Se ejecutan utilizando JUnit; para el ejemplo de Visual Studio Code se debe ingresar a la pestaña "pruebas" en el costado izquierdo y posteriormente seleccionar "ejecutar prueba" al elegir una prueba en particular o todas.

Instrucciones para ejecutar el juego:

La ubicación src/main contiene el archivo que debe compilarse y correrse para jugar, que es el archivo Main.java en java/algo3. Para Visual Studio Code la extensión utilizada para compilar es Debugger for Java, que está incluido en el Extension Pack. La carpeta resources tiene los recursos utilizados en la aplicación.

Los controles por defecto son:

Flecha izquierda y flecha derecha - movimiento
R - reinicio
Barra espaciadora - iniciar juego

Sobre los niveles:

Para que el juego detecte los nuevos niveles es necesario modificar la constante global CANT_NIVELES en Breakout.java, acorde a la cantidad de archivos .csv que hay en el directorio. Los niveles están ordenados numéricamente de menor a mayor, por lo que para insertar un nuevo nivel hay que seguir este patrón. También se puede modificar el nivel en el cual se inicia el juego con la variable NIVEL_INI. Los niveles se crean escribiendo un archivo .csv en la carpeta niveles de la siguiente forma:
Posición en X - posición en Y - vida - puntuación - color (RGB) - visibilidad.