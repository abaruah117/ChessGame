package com.kevin.graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJModel {

	private ArrayList<Vector> positions;
	private ArrayList<Vector> textureCords;
	private ArrayList<Vector> normals;
	private ArrayList<Vertex> verticies;

	public OBJModel(String path) {

		positions = new ArrayList<Vector>();
		textureCords = new ArrayList<Vector>();
		normals = new ArrayList<Vector>();
		verticies = new ArrayList<Vertex>();

		File f = new File(path);
		Scanner scanner = null;
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file at " + f.getAbsolutePath());
			System.exit(1);
		}
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			// System.out.println(line.indexOf("v "));
			if (line.length() == 0 || line.charAt(0) == '#'
					|| line.indexOf("mtllib ") > -1) {
				continue;
			} else if (line.indexOf("vt ") > -1) {
				String[] split = line.split(" ");
				textureCords.add(new Vector(Float.valueOf(split[1]), Float
						.valueOf(split[2])));
			} else if (line.indexOf("vn ") > -1) {
				String[] split = line.split(" ");
				normals.add(new Vector(Float.valueOf(split[1]), Float
						.valueOf(split[2]), Float.valueOf(split[3])));
			} else if (line.indexOf("v ") > -1) {
				String[] split = line.split(" ");
				System.out.println(line);
				positions.add(new Vector(Float.valueOf(split[1]), Float
						.valueOf(split[2]), Float.valueOf(split[3])));
				System.out.println("Found position vector at "
						+ positions.get(positions.size() - 1));
			} else if (line.indexOf("f ") > -1) {
				String[] split = line.split(" ");
				// Vertex v1 = new
				// Vertex(positions.get(Float.valueOf(split[1].split("/")[0])));
				Vertex v1, v2, v3;
				{
					String[] split1 = split[1].split("/");
					Integer vertIndex = Integer.valueOf(split1[0]);
					Integer textIndex;
					if (split1.length == 1 || split1[1] == "") {
						textIndex = -1;
					} else {
						textIndex = Integer.valueOf(split1[1]);
					}
					v1 = new Vertex(positions.get(vertIndex - 1),
							textIndex == -1 ? null : textureCords
									.get(textIndex - 1),
							new Vector(0, 0));
				}
				{
					String[] split1 = split[2].split("/");
					Integer vertIndex = Integer.valueOf(split1[0]);
					Integer textIndex;
					if (split1.length == 1 || split1[1] == "") {
						textIndex = -1;
					} else {
						textIndex = Integer.valueOf(split1[1]);
					}
					v2 = new Vertex(positions.get(vertIndex - 1),
							textIndex == -1 ? null : textureCords
									.get(textIndex - 1),
							new Vector(0, 0));
				}
				{
					String[] split1 = split[3].split("/");
					Integer vertIndex = Integer.valueOf(split1[0]);
					Integer textIndex;
					if (split1.length == 1 || split1[1] == "") {
						textIndex = -1;
					} else {
						textIndex = Integer.valueOf(split1[1]);
					}
					v3 = new Vertex(positions.get(vertIndex - 1),
							textIndex == -1 ? null : textureCords
									.get(textIndex - 1),
							new Vector(0, 0));
				}

				Vector normal = v1.getPos().subtract(v2.getPos())
						.cross(v1.getPos().subtract(v3.getPos()));
				v1.setNormal(normal);
				v2.setNormal(normal);
				v3.setNormal(normal);
				verticies.add(v1);
				verticies.add(v2);
				verticies.add(v3);

			}
		}

		for (int i = 0; i < verticies.size(); i++) {
			if (i % 100 == 0) {
				System.out.println("Calculating normals: " + i + "/"
						+ verticies.size());
			}
			for (int j = 0; j < verticies.size(); j++) {
				if (i == j) {
					continue;
				} else {
					if (verticies.get(i).getPos()
							.equals(verticies.get(j).getPos())) {
						verticies.get(i).addToNormal(
								verticies.get(j).getNormal());
					}
				}
			}
			verticies.get(i).normalizeNormal();
		}

		scanner.close();

	}

	public ArrayList<Vertex> getVerticies() {
		return verticies;
	}

	public int VertexCount() {
		return verticies.size();
	}

	public Vertex getVertex(int index) {
		return verticies.get(index);
	}

}
