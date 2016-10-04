---
title: "Prácticas - Recuperación de Información"
subtitle: "Práctica 1 - Codificacin de Caracteres"
author:
  - Daniel Lopez Garcia

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

Búsqueda a realizar: Codificacion de caracteres y herramientas Linux.

#Codificacion de caracteres.

La codificación de caracteres es el método que permite convertir un carácter
de un lenguaje natural en un símbolo de otro sistema de representación
aplicando normas o reglas de codificación.

#Unicode

Unicode es un set de caracteres universal, es decir, un estándar en el que se definen todos los caracteres necesarios
para la escritura de la mayoría de los idiomas hablados en la actualidad que se usan en la computadora.
Su objetivo es ser, y, en gran medida, ya lo ha logrado, un superconjunto de todos los sets de caracteres que se hayan codificado.

Generalmente resulta imposible combinar distintas codificaciones en la misma página web o en una base de datos,
por lo que siempre es muy difícil soportar páginas plurilingües si se aplican enfoques "antiguos" cuando se trata de tareas de codificación.
El Consorcio Unicode proporciona un único y extenso set de caracteres que pretende incluir todos los caracteres necesarios para cualquier sistema de escritura del mundo,
incluyendo sistemas ancestrales.

La codificación de caracteres refleja la manera en la que el set de caracteres codificados se convierte a bytes para su procesamiento en la computadora.
Los formatos de codificación que se pueden usar con Unicode se denominan UTF-8, UTF-16 y UTF-32.

Se considera que las primeras 65.536 ubicaciones de puntos de código en el set de caracteres Unicode constituyen el Plano plurilingüe básico (Basic Multilingual Plane, BMP). El BMP incluye la mayoría de los caracteres utilizados más frecuentemente.

UTF-8 utiliza 1 byte para representar caracteres en el set ASCII, dos bytes para caracteres en otros bloques alfabéticos y tres bytes para el resto del BMP. Para los caracteres complementarios se utilizan 4 bytes.

UTF-16 utiliza 2 bytes para cualquier carácter en el BMP y 4 bytes para los caracteres complementarios.

UTF-32 emplea 4 bytes para todos los caracteres.

#Herramientas

Los editores de texto crean y modifican ficheros de texto plano.Modo texto o modo gráfico. La mayoría de editores usan el formato ASCII o UTF-8.
Los procesadores de texto crean ficheros de texto con formato e imágenes.
