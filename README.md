# Island Generator

  - Omar Shehada [shehadao@mcmaster.ca]
  - Abdallah Alqashqish [alqashqa@mcmaster.ca]
  - Beshoy Hezky [hezkyb@mcmaster.ca]

## How to run the program
### Installation instructions
This product is handled by Maven, as a multi-module project.
To install the different tooling on your computer, simply run:
```
island@generator % mvn install
```
After installation, you'll find an application named `generator.jar` in the `generator` directory, a file named
`visualizer.jar` in the `visualizer` directory, and a file names `island.jar` in the island directory. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. 
The product takes many arguments that configure the mesh generation process.

Generator options include:
1. -c,--color <vertex coloring> <segment coloring> <polygon coloring>   Sets the color generation for all the elements of the mesh.
2. -d,--dimension <widthxheight>                                        Sets the dimensions of the mesh.
3. -h,--help                                                            Displays program usage.
4. -m,--mesh <mesh type>                                                The type of mesh to generate. Either `grid` or `irregular`.
5. -np,--numPolygons <number of polygons>                               Sets the number of polygons to generate in the irregular mesh. Will be ignored if the mesh is a grid.
6. -out,--output <output file>                                          The file to output to.
7. -rl,--relaxationLevel <relaxation level>                             Sets the relaxation level of an irregular mesh. Will be ignored by grid mesh
8. -ss,--squareSize <square size>                                       Sets the size of the squares in the grid mesh. Ignored by irregular mesh.
9. -t,--thickness <vertex thickness> <segment thickness>                Sets the thickness for the vertices and segments.

```
island@generator % cd generator 
island@generator generator % java -jar generator.jar -out sample.mesh -m grid (Generates a grid mesh)
island@generator generator % java -jar generator.jar -out sample.mesh -m irregular (Generates an irregular mesh)
island@generator generator % ls -lh sample.mesh
-rw-r--r--  1 user  group    29K 29 Jan 10:52 sample.mesh
island@generator generator % 
```

### Island Generator

To run the island generator, go to the `island` directory, and use `java -jar` to run the product.

Island Generator options include:
1. -i,--input <input file>                            Sets the file to read the mesh from.
2. -o,--output <output file>                          Sets the file to output the new island mesh to.
3. -h,--help                                          Displays program usage.
4. -m,--mode <mode>                                   The island generation mode. At the moment only `lagoon` and `random`.
5. -a,--altitude <altimetric profile>                 The island altimetric profile generation mode. At the moment only `lagoon`, `volcano`, or `hills`.
6. -l,--lakes <# of lakes>                            The number of lakes to place on the island. Note that lakes can merge.
7. -s,--shape <shape>                                 The shape to set the island to. Available shapes are `circle`, `oval`, and `star`.
8. -aq,--aquifers <# of aquifers>                     The number of random aquifers to add to the island.
9. -r, --rivers <# of rivers>                         The number of rivers to add to the island.
10. -s, --soil <absorption>                           The soil absorption profile to set for this island. Only `wet` and `dry`. Wet is the default.
11. -sed, --seed <seed>                               The seed of the island to generate. Generator will generate a random one if none are given.
12. -b, --biomes <biomes>                             The biomes of the island to generate. Options are `tropical` and `temperate`. Generator will generate a `tropical` island if none is given.
13. -H, --hook  <hook>                                Sets the hook to run after the generation of the island. Current options are `moisture` and `elevation`.
```
island@generator % cd island 
island@generator island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh -m lagoon
island@generator island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh -m random --shape circle --altitude volcano --biomes tropical # Generates a volcanic tropical island in the shape of a circle
island@generator island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh -m random --hook moisture # Moisture heatmap
island@generator island % java -jar island.jar -i ../generator/sample.mesh -o island.mesh -m random --hook elevation # Elevation heatmap
island@generator island % ls -lh island.mesh
-rw-r--r--  1 user  group    29K 29 Jan 10:52 sample.mesh
island@generator island % 
```

### Visualizer

To visualize an existing mesh, go to the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

Visualizer options include:
1. -h,--help                      Displays program usage.
2. -in,--input <input file>       Takes in the mesh file to read from.
3. -out,--output <output file>    The file to output to.
4. -X,--debug                     Enable debug mode when rendering the mesh.

```
island@generator % cd visualizer 
island@generator visualizer % java -jar visualizer.jar -in ../generator/sample.mesh -out sample.svg
island@generator visualizer % java -jar visualizer.jar -in ../generator/sample.mesh -out sample.svg -X (For debugging mode)
island@generator visualizer % ls -lh sample.svg
-rw-r--r--  1 user  group    56K 29 Jan 10:53 sample.svg
island@generator visualizer %
```
To viualize the SVG file:
  - Open it with a web browser
  - Convert it into something else with tools like `rsvg-convert`