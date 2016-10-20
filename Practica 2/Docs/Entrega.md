---
title: "Prácticas - Recuperación de Información"
subtitle: "Práctica 2 - Indexación de Textos"
author:
  - Iván Calle Gil
  - Daniel López García
  - Lothar Soto Palma
  - José Carlos Entrena Jiménez

toc: true
toc-depth: 2
numbersections: true

mainfont: Droid Serif
monofont: Source Code Pro

geometry: "a4paper, top=2.5cm, bottom=2.5cm, left=3cm, right=3cm"

header-includes:
  - \usepackage{graphicx}
  - \usepackage{float}
  - \usepackage[spanish]{babel}
---
\pagebreak

# Introducción


# Implementación

## Lectura de datos

Al leer los ficheros de texto que vamos a tratar, y previo a la aplicación del _stemmer_, hemos de eliminar los signos de puntuación del fichero. Para esto, hemos utilizado una función llamada _removePunctuation_, que cambia las mayúsculas por minúsculas y elimina todos los caracteres no alfanuméricos, mediante el uso de una expresión regular. Así, nos aseguramos de que tratamos únicamente con palabras en minúscula sin ningún caracter especial.

Para el fichero con las palabras vacías no necesitamos ningún tipo de tratamiento, así que únicamente lo leemos. Como este fichero será el mismo independientemente del número de documentos que vayamos a indexar, lo leeremos una única vez y lo almacenaremos como variable de clase.

A la hora de leer los ficheros, el programa recibirá un _path_ que, si es un directorio, recorreremos buscando todos los ficheros de texto que contenga, incluyendo aquellos que estén en subcarpetas, y almacenamos todo el texto en un _string_. De proporcionar el _path_ a un fichero, simplemente indexaremos este.

## Indexación

Una vez hemos leído los datos, utilizamos _StringTokenizer_ para obtener los tokens sobre los que ir aplicando el _stemmer_ uno a uno. Antes de llamar al método _stem_, hemos de comprobar que la palabra no esté en el conjunto de palabras vacías, que hemos leído previamente y tenemos almacenado en una tabla hash. Si es así, podemos continuar y obtener la raíz del token, la cual almacenaremos en una tabla hash en caso de ser nueva, o aumentaremos en uno el entero asociado a dicha raíz en la tabla hash.

# Ley de Zipf

Podemos ver dos gráficos en los que se muestran las frecuencias de todas las palabras del texto (figura 1), y solo las 100 primeras (figura 2).

\begin{figure}[htb]
\includegraphics[width=0.9\textwidth,keepaspectratio]{img/grafico.png}
\caption{Ley de Zipf}
\end{figure}


\begin{figure}[htb]
\includegraphics[width=0.9\textwidth,keepaspectratio]{img/grafico100.png}
\caption{Ley de Zipf}
\end{figure}

# Trabajo en grupo

En esta práctica hemos intentado que el trabajo de cada uno de los componentes fuera similar en cuanto a carga. El número de tareas a realizar no era numeroso, por lo que el reparto no ha sido demasiado grande, y consideramos que cada uno de los componente ha realizado un trabajo equiparable al de los demás. A continuación detallamos con más profundidad la aportación de cada miembro:

* Lothar Soto: Ha sido el encargado de la creación de los métodos de lectura de archivos y de la mayor parte del tratamiento de datos, como la eliminación de los signos de puntuación.

* Iván Calle: Se ha encargado de los métodos de lectura recursiva de un directorio, además de la implementación del proceso de lexificación.

* Daniel García: Ha llevado a cabo la generación de los gráficos para la Ley de Zipf, además de recopilar los datos obtenidos en el programa creando el código correspondiente, y volcarlos a los ficheros de datos.  

* José Carlos Entrena: Encargado de la redacción del documento de entrega, además de revisar el código y hacer unas implementaciones mínimas.

Cabe destacar que la planificación del desarrollo de la práctica, junto a la discusión del mejor método para la implementación, fue realizada por todos los miembros del grupo en común, y solo después pasamos a llevar a cabo lo previamente discutido.
