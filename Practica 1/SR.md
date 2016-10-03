---
title: "Prácticas - Recuperación de Información"
subtitle: "Práctica 1 - Sistemas de Recomendación"
author:
  - Lothar Soto Palma

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


# Práctica 1: Búsqueda.

Búsqueda a realizar: Sistemas de Recomendación.

## Datos para el Timeline

* **Motor:** _Google_
    * **Búsqueda:** Sistemas de Recomendación
        * Web: https://es.wikipedia.org/wiki/Sistema_de_recomendación Tiempo: X minutos Conocimiento: Definición del concepto.
        * Web: https://es.wikipedia.org/wiki/Sistema_de_filtrado_de_informaci%C3%B3n Tiempo: X minutos Conocimiento: Definición del concepto.
        * Web: https://www.upf.edu/hipertextnet/numero-2/recomendacion.html Tiempo: X minutos Conocimiento: Definición del concepto, Estructura de los SR, Ejemplos de SR.
        * Web: http://jarroba.com/que-son-los-sistemas-de-recomendacion/ Tiempo: X minutos Conocimiento: Definición del concepto, Tipos de SR.
    * **Búsqueda:** Estructura sistemas de recomendación
        * Web: http://decsai.ugr.es/~lci/proceedings-pdf/compumat07.pdf. Tiempo: X minutos Conocimiento: Modelado de un SR
* **Motor:** _Bing_
    *  **Búsqueda:** Estructura sistema recomendación
        * Web: https://es.wikipedia.org/wiki/Sistema_de_recomendación Tiempo: X minutos Conocimiento: Concepto.
        * Web: https://www.upf.edu/hipertextnet/numero-6/recomendacion.html Tiempo: X minutos Conocimiento: Concepto.
        * Web: https://www.researchgate.net/publication/237680987_USO_DE_CONOCIMIENTO_ESTRUCTURADO_EN_UN_SISTEMA_DE_RECOMENDACION_BASADO_EN_CONTENIDO1 Tiempo: X minutos Conocimiento: Concepto.
* **Motor:** _Google_
    * **Búsqueda:** Recommender system
        * Web: https://en.wikipedia.org/wiki/Recommender_system Tiempo: X minutos Conocimiento: Modelado de un SR.
        * Web: https://es.coursera.org/learn/recommender-systems Tiempo: 0 minutos Conocimiento: Error 404.
        * Web: https://www.quora.com/Which-algorithms-are-used-in-recommender-systems Tiempo: X minutos Conocimiento: Algoritmos.
        * Web: http://infolab.stanford.edu/~ullman/mmds/ch9.pdf Tiempo: X minutos Conocimiento: Algoritmos.
* **Motor:** _Bing_
    * **Búsqueda:** Types of recommender system
        * Web: https://bluepiit.com/blog/classifying-recommender-systems/  Tiempo: X minutos Conocimiento: Concepto.
    * **Búsqueda:** examples recommender system
        * Web: http://infolab.stanford.edu/~ullman/mmds/ch9.pdf Tiempo: X minutos Conocimiento: Concepto.
* **Motor:** _Yahoo_
    *  **Búsqueda:** Ejemplos sistema de recomendación
        * Web: https://es.noticias.yahoo.com/netflix-importancia-sistemas-recomendaci%C3%B3n-063504652.html Tiempo: X minutos Conocimiento: Concepto.  

# Contenido Presentación
## Definición del concepto
Un sistema de recomendación es un sistema inteligente que proporciona a los usuarios una serie de sugerencias personalizadas (recomendaciones) sobre un determinado tipo de elementos (items). Los sistemas de recomendación estudian las características de cada usuario y mediante un procesamiento de los datos, encuentra un subconjunto de items que pueden resultar de interés para el usuario.

Los SR se han ido consolidando como potentes herramientas para ayudar a reducir la sobrecarga de información a la que nos enfrentamos en los procesos de búsqueda de información.  

Ayudan a filtrar los ítems de información recuperados, usando distintas técnicas para identificar aquellos ítems que mejor satisfacen las preferencias o necesidades de los usuarios.

## Estructura de los SR
Los elementos fundamentales que intervienen en el esquema de funcionamiento de un SR. Dichos elementos los podemos usar como criterios de clasificación y son los siguientes:  

* **Entradas / salidas del proceso de generación de la recomendación:** Es la información que necesitamos para realizar las recomendaciones constituye la entrada o entradas del sistema. Usan las entradas del usuario activo, pero también información sobre los ítems o información del resto de usuarios del sistema, que actúan como colaboradores. La realimentación por parte de los usuarios es muy importante de cara a albergar una información más completa.

* **Método usado para generar las recomendaciones:** Normalmente estos métodos no son exclusivos entre si, de hecho pueden ser complementarios, en un mismo SR pueden implementarse varios métodos de recomendación. Hay diversos tipos de generación de recomendaciones y suelen ser dos tipos los sistemas basados en la colaboración y los que no.

* **Grado de personalización:** Agrega una capa de profundidad al SR haciendo que la recomendación esté adaptada para cada usuario, de modo que son sistemas que tienen en cuenta la información personal y normalmente hacen uso de técnicas basadas en contenidos o colaboraciones.

## Tipos de SR
* **SR con Filtrado basado en Contenido:** (ejm: YouTube, Netflix) Se basa principalmente en que el sistema crea sugerencias o recomendaciones  a partir del aprendizaje de un perfil de intereses del usuario con sus preferencias actuales, basandose en la valoración que este da a los items o elementos.  

* **SR con Filtrado Demográfico:** Estas recomendaciones se realizan en función de las características de los usuarios (edad, sexo, situación geográfica, profesión, etc).  

* **SR basado en Filtrado Colaborativo:** (ejm: Filmaffinity) El ﬁltrado colaborativo consiste en ver que usuarios son similares al usuario activo (o usuario al que hay que realizarle las recomendaciones) y a continuación,recomendar aquellos items que no han sido votados por el usuario activo y que han resultado bien valorados por los usuarios similares.  

* **SR con métodos de Filtrado Híbrido:** (ejm: Amazon) Mezclan alguno de los tres filtrados mencionados anteriormente para realizar recomendaciones e incluso lo combinan con alguna otra técnica de inteligencia artificial como pueda ser la lógica borrosa o la computación evolutiva.   

* **SR basados en utilidad:**  Estos sistemas realizan recomendaciones basadas en una valoración de la utilidad de cada item para el usuario. El principal problema para este tipo es como crear esa valoración para cada uno de los usuarios individualmente, las técnicas son muy variadas en la industria.

* **SR basados en conocimiento:** Este sistema intenta realizar sugerencias de items basandose en inferencias realizadas sobre las necesidades de los ususarios y sus preferencias.
## Algoritmos usados en Sistemas de Recomendación
Los de las transparencias.

# Referencias
* https://es.wikipedia.org/wiki/Sistema_de_recomendación
* Enrique Herrera-Viedma & Carlos Porcel & Lorenzo Hidalgo. Sistemas de recomendaciones: herramientas para el filtrado de información en Internet [en linea]. "Hipertext.net", núm. 2, 2004. <http://www.hipertext.net>
* http://decsai.ugr.es/~lci/proceedings-pdf/compumat07.pdf
* https://en.wikipedia.org/wiki/Recommender_system
