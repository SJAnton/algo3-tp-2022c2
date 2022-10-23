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
