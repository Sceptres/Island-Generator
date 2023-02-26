# Assignment A2: Mesh Generator

  - Omar Shehada [shehadao@mcmaster.ca]
  - Abdallah Alqashqish [alqashqa@mcmaster.ca]
  - Beshoy Hezky [hezkyb@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

Generator options include:
1. -c,--color <vertex coloring> <segment coloring> <polygon coloring>   Sets the color generation for all the elements of the mesh.
2. -d,--dimension <widthxheight>                                        Sets the dimensions of the mesh.
3. -h,--help                                                            Displays program usage.
4. -m,--mesh <mesh type>                                                The type of mesh to generate. Either `grid` or `irregular`.
5. -np,--numPolygons <number of polygons>                               Sets the number of polygons to generate in the irregular mesh. Will be ignored if the mesh is a grid.
6. -out,--output <output file>>                                         The file to output to.
7. -rl,--relaxationLevel <relaxation level>                             Sets the relaxation level of an irregular mesh. Will be ignored by grid mesh
8. -ss,--squareSize <square size>                                       Sets the size of the squares in the grid mesh. Ignored by irregular mesh.
9. -t,--thickness <vertex thickness> <segment thickness>                Sets the thickness for the vertices and segments.

```
mosser@azrael A2 % cd generator 
mosser@azrael generator % java -jar generator.jar -out sample.mesh -m grid (Generates a grid mesh)
mosser@azrael generator % java -jar generator.jar -out sample.mesh -m irregular (Generates an irregular mesh)
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator % 
```

### Visualizer

To visualize an existing mesh, go to the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

Visualizer options include:
1. -h,--help                      Displays program usage.
2. -in,--input <input file>>      Takes in the mesh file to read from.
3. -out,--output <output file>>   The file to output to.
4. -X,--debug                     Enable debug mode when rendering the mesh.

```
mosser@azrael A2 % cd visualizer 
mosser@azrael visualizer % java -jar visualizer.jar -in ../generator/sample.mesh -out sample.svg
mosser@azrael visualizer % java -jar visualizer.jar -in ../generator/sample.mesh -out sample.svg -X (For debugging mode)

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```
To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

1. Works as expected per feature outline and requirements
2. No bugs and unexpected behaviour
3. Smooth program execution


### Product Backlog

- | Id | Feature title                        | Who?          | Start     |     End   |   Status  |
- | 01 |Creating a mesh ADT (fix generator)   |Abdallah,Beshoy| 8-2-2023  | 16-2-2023 |  Complete |
- | 02 |Producing full meshes                 |Omar           | 8-2-2023  | 16-2-2023 |  Complete |
- | 03 |Playing with rendering                |Beshoy         | 16-2-2023 | 16-2-2023 |  Complete |
- | 04 |Visualization mode                    |Omar           | 16-2-2023 | 17-2-2023 |  Complete |
- | 05 |Mesh configuration                    |Abdallah,Beshoy| 19-2-2023 | 21-2-2023 |  Complete |
- | 04 |Irregular mesh generation             |Omar & Beshoy  | 22-2-2023 | 25-2-2023 |  Complete |


