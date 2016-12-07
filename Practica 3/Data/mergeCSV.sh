#!/bin/bash
# Modified from http://stackoverflow.com/questions/24641948/merging-csv-files-appending-instead-of-merging/24643455

OutFileName="${!#}"                       # Output name is last argument
for ((i=1; i<"$#"; i++)); do
   if [[ "$i" -eq 1 ]] ; then
      head -1  ${!i} >   $OutFileName       # Copy header if it is the first file
   fi
   tail -n +2  ${!i} >>  $OutFileName      # From the 2nd line in other files
done
