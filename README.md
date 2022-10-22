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

Los patrones de diseño a utilizar son:

- State: este patrón resuelve el problema de la modificación de los bloques. Al ser destruido,
el estado de un bloque pasa de "activo" a "destruido", por lo que su comportamiento es modificado,
ya que debe desaparecer de la pantalla y evitar la colisión con la bola.

- Singleton: el patrón se aplica para la paleta y la bola, ya que solo necesitamos una instancia
de ellas. Cada vez que la bola toca la pared inferior habría que restar una vida y relocalizarla
a su posición de lanzamiento, pero no destruirla y volverla a crear.

- Observer: este patrón sirve para indicarle a todos los objetos Bloque que deben modificar su
estado a "activo" cuando el jugador se queda sin vidas.
