import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/**
 * The renderer class does all the math using matrices to divide models into triangles and draw them in 3d
 */
public class Renderer extends Canvas {

	private static boolean DO_WIRE_MESH = false;
	private Canvas texture;
	private Vector color;

	private ArrayList<Light> lights;

	float[][] depthBuffer;


	/**
	 * Initializes the width and height, the lighting, and the depth buffer float 2d array, and also clears the depth buffer. 
	 * @param width the Canvas width
	 * @param height the Canvas height
	 */
	public Renderer(int width, int height) {
		super(width, height);
		lights = new ArrayList<Light>();
		depthBuffer = new float[height][width];
		clearDepthBuffer();
	}
	
	/**
	 * Adds a Light to the scene
	 * @param light the Light to be added
	 */
	public void addLight(Light light) {
		lights.add(light);
	}
	

	
	/**
	 * Clears the depth buffer by setting to closest distance to the furthest possible value
	 */
	public void clearDepthBuffer() {
		for (int y = 0; y < depthBuffer.length; y++) {
			for (int x = 0; x < depthBuffer[0].length; x++) {
				depthBuffer[y][x] = -Float.MAX_VALUE;
			}
		}

	}
	/**
	 * Draws the pieces from the chess board
	 * @param b the Board containing the pieces to be drawn
	 * @param c the Camera to be used 
	 * @param boardTrans The translation matrix for the board, no rotation is allowed, to use rotation pass in the angles in the rotationAngles vector
	 * @param rotationAngles the Vector at which the user has rotated the board
	 */
	public void drawBoardPieces(Board b, Camera c, Vector boardTrans,
			 Vector rotationAngles) {

		Vector align = new Vector(60, 0, 70);

		Matrix xRot = new Matrix().rotationXMatrix(rotationAngles.getX());
		Matrix yRot = new Matrix().rotationYMatrix(rotationAngles.getY());
		Matrix zRot = new Matrix().rotationZMatrix(rotationAngles.getZ()); //TODO get matrices from engine, not needs but helps fps
		try {
		for (Piece p : b.getWhitePieces()) {
			Coord pos = p.getCoord();
			Vector posV = new Vector(
					-pos.getX()
							* Board.getTileSize(), 0, -pos.getY()
							* Board.getTileSize()).add(align);
			//Vector titledPos = new Vector((float) (posV.getX() * Math.cos(angleRadY)) , (float)(posV.getZ() * Math.sin(-angleRadX)), (float)(posV.getZ() * Math.cos(angleRadX) + (posV.getX() * Math.sin(-angleRadY))));
			Vector tiltedPos = posV.muliply(Matrix.multiply(yRot, zRot, xRot));
			Vector totalTrans = boardTrans.add(tiltedPos);
			Matrix positon = new Matrix().translationMatrix(totalTrans.getX(),
					totalTrans.getY(), totalTrans.getZ());

			Mesh m = ModelLoader.getModel(p.getClass().getName()).getMesh(c,
					Matrix.multiply(positon, yRot, zRot, xRot));
			color = new Vector(210,180,140);

			drawMesh(m, c);

		}

		for (Piece p : b.getBlackPieces()) {

			Coord pos = p.getCoord();
			Vector posV = new Vector(
					-pos.getX()
							* Board.getTileSize(), 0, -pos.getY()
							* Board.getTileSize()).add(align);
			//Vector titledPos = new Vector((float) (posV.getX() * Math.cos(angleRadY)) , (float)(posV.getZ() * Math.sin(-angleRadX)), (float)(posV.getZ() * Math.cos(angleRadX) + (posV.getX() * Math.sin(-angleRadY))));
			Vector tiltedPos = posV.muliply(Matrix.multiply(yRot, zRot, xRot));
			Vector totalTrans = boardTrans.add(tiltedPos);
			Matrix positon = new Matrix().translationMatrix(totalTrans.getX(),
					totalTrans.getY(), totalTrans.getZ());

			Mesh m = ModelLoader.getModel(p.getClass().getName()).getMesh(c,
					Matrix.multiply(positon, yRot, zRot, xRot));
			color = new Vector(51, 25, 0);

			drawMesh(m, c);

		}
		} catch (ConcurrentModificationException e) {
			drawBoardPieces(b, c, boardTrans, rotationAngles);
		}

	}

	
	/**
	 * Draws the tiles from the chess board
	 * @param tiles the 2d Array of Tiles to be drawn
	 * @param transform the transform matrix for the board, rotation is allowed
	 * @param camera the Camera to draw the scene from	
	 * @param selected the user-selected Coords
	 */
	public void drawBoardTiles(Board b, Matrix transform,
			Camera camera, ArrayList<Coord> selected) {
		Tile[][] tiles = b.getBoardColor();
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {

				Mesh m = tiles[y][x]
						.getMesh(camera, Matrix.multiply(transform,
								new Matrix().translationMatrix(
										(x - tiles.length / 2f)
												* Board.getTileSize(),
										0,
										(y - tiles.length / 2f)
												* Board.getTileSize())));
				boolean usedNewTexture = false;
				if (tiles[y][x].isWhite()) {
					for (Coord c : selected) {
						if (c.toVector().equals(tiles[y][x].getPos())) {
							setTexture(ModelLoader
									.getTexture("whiteSquareSelected"));
							// System.out.println("found a white square that has been selected");
							usedNewTexture = true;
						}
					}
				}
				if (!tiles[y][x].isWhite()) {
					for (Coord c : selected) {

						if (c.toVector().equals(tiles[y][x].getPos())) {
							// System.out.println("found a black square that has been selected");
							setTexture(ModelLoader
									.getTexture("blackSquareSelected"));
							usedNewTexture = true;
						}
					}
				}

				if (!usedNewTexture) {
					// System.out.println("Using norm tile");
					setTexture(m.getTexture());
				}

				OBJModel model = m.getModel();

				Vertex v1 = model.getVertex(0).multiply(m.getTransform())
						.perspectiveDevide();
				Vertex v2 = model.getVertex(1).multiply(m.getTransform())
						.perspectiveDevide();
				Vertex v3 = model.getVertex(2).multiply(m.getTransform())
						.perspectiveDevide();
				Vertex v4 = model.getVertex(3).multiply(m.getTransform())
						.perspectiveDevide();

				Clickable clickable = new Clickable(v3.getPos(), v4.getPos(),
						v1.getPos(), v2.getPos());
				tiles[y][x].setCollider(clickable);
				// v1
				// v2
				// v3
				// v4



				drawTriangle(v1, v2, v3, camera);
				drawTriangle(v2, v3, v4, camera);

			}
		}
	}
	/**
	 * Draws a 2D line from v1 to v2, used for debugging
	 * @param v1 the first Vertex
	 * @param v2 the second Vertex
	 */
	public void drawLine(Vertex v1, Vertex v2) {
		Vertex bot = v1;
		Vertex top = v2;
		if (bot.getPos().getY() > top.getPos().getY()) {
			bot = v2;
			top = v1;
		}
		Edge e = new Edge(bot, top);
		for (int y = (int) Math.ceil(e.getMinY()); y < e.getMaxY(); y++) {
			drawPixel((int) Math.ceil(e.getCurrentX()), y, Color.RED);
			e.step();
		}
	}
	
	/**
	 * Draws a 3 dimensional line, with interpolated textures, normals, z-buffering, lighting, and clipping [Most complicated line ever] <b>
	 * Interpolation is done using the barycentric method
	 * @param bot The bottom vertex of the triangle this line is being drawn from, used for interpolation
	 * @param mid The middle vertex of the triangle this line is being drawn from, handyness does not matter,  used for interpolation
	 * @param top The top vertex of the triangle this line is being drawn from, used for interpolation
	 * @param left The left edge of the triangle, used for line stepping. The method will not step across the lines
	 * @param right The right edge of the triangle, used for line stepping.  The method will not step across the line
	 * @param minX The minimum X value, may be different from the edge left value due to filling conventions
	 * @param maxX The minimum Y value, may be different from the edge left value due to filling conventions
	 * @param y The current Y value being drawn
	 * @param camera The camera to draw this line from
	 */
	public void drawLine(Vertex bot, Vertex mid, Vertex top, Edge left,
			Edge right, int minX, int maxX, int y, Camera camera) {

		if (maxX - minX > getWidth()) {
			// System.out.println("SkippingX");
			return;
		}
		for (int x = (int) Math.ceil(minX); x < Math.ceil(maxX); x++) {

			if (x < 0 || x >= getWidth()) {
				continue;
			}

			float z = lerp(left.getCurrentZ(), right.getCurrentZ(), (x - minX)
					/ (maxX - minX));

			Vector position = new Vector(x, y, z);

			float distance = camera.getPos().subtract(position).length();

			if (distance < depthBuffer[y][x]
					|| distance < camera.getNearCliping()
					|| z < camera.getPos().getZ()) {
				continue;
			}

			Vector f1 = bot.getPos().subtract(position);
			Vector f2 = mid.getPos().subtract(position);
			Vector f3 = top.getPos().subtract(position);
			Vector surfaceNormal = bot.getPos().subtract(mid.getPos())
					.cross(top.getPos().subtract(mid.getPos()));
			float area = surfaceNormal.length();
			float area1 = f2.cross(f3).length() / area;
			float area2 = f3.cross(f1).length() / area;
			float area3 = f1.cross(f2).length() / area;

			Vector objectColor;

			if (bot.getTextureCord() != null && mid.getTextureCord() != null
					&& top.getTextureCord() != null && texture != null) {
				Vector textureCord = bot
						.getTextureCord()
						.multiply(area1)
						.add(mid.getTextureCord().multiply(area2)
								.add(top.getTextureCord().multiply(area3)));
				Color c = texture.getPixelAt(textureCord);
				objectColor = new Vector(c.getRed(), c.getGreen(), c.getBlue());
			} else if (color != null) {
				objectColor = color;
			} else {
				objectColor = new Vector(100, 200, 100);
			}

			if ((x == (int) (Math.ceil(minX)) || y == left.getMinY())
					&& DO_WIRE_MESH) {
				objectColor = new Vector(0, 0, 0);
			}

			Vector interpolatedNormal = bot
					.getNormal()
					.multiply(area1)
					.add(mid.getNormal().multiply(area2)
							.add(top.getNormal().multiply(area3)));
			Iterator<Light> lightIter = lights.iterator();
			
			while(lightIter.hasNext()) {
				Light light = lightIter.next();
				Vector ambient = light.getAmbientColor().multiply(
						light.getAmbientBrightness());
				// Vector lightDir = light.getPosition().subtract(position)
				// .normalize();
				Vector lightDir = position.subtract(light.getPosition());

				float diffuseStrength = Math.max(interpolatedNormal.normalize()
						.dot(lightDir.normalize()), 0);
				// System.out.println(normal.dot(lightDir));
				Vector diffuse = light.getDiffuseColor().normalize()
						.multiply(light.getDiffuseBrightness())
						.devide(lightDir.length()).multiply(diffuseStrength);
				objectColor = (ambient.add(diffuse)).multiply(objectColor);

			}

			this.drawPixel(x, y, objectColor);
			depthBuffer[y][x] = distance;

		}
	}
	
	/**
	 * Splits a mesh into its many triangles, then draws the triangles
	 * @param m The mesh to draw
	 * @param camera The camera to draw the mesh from
	 */
	public void drawMesh(Mesh m, Camera camera) {
		setTexture(m.getTexture());
		OBJModel model = m.getModel();
		for (int i = 0; i < model.VertexCount(); i += 3) {

			Vertex v1 = model.getVertex(i + 0).multiply(m.getTransform())
					.perspectiveDevide();
			Vertex v2 = model.getVertex(i + 1).multiply(m.getTransform())
					.perspectiveDevide();
			Vertex v3 = model.getVertex(i + 2).multiply(m.getTransform())
					.perspectiveDevide();

			drawTriangle(v1, v2, v3, camera);

		}

	}
	/**
	 * Splits an arbitrary 3D triangle into 2 triangles, then steps across the lines and draws them <b> Vertex order does not matter
	 * @param v1 the first Vertex 
	 * @param v2 the second Vertex
	 * @param v3 the third Vertex 
	 * @param camera the Camera
	 */
	public void drawTriangle(Vertex v1, Vertex v2, Vertex v3, Camera camera) {
		Vertex top = v1;
		Vertex mid = v2;
		Vertex bot = v3;
		if (bot.getPos().getY() > mid.getPos().getY()) {
			Vertex temp = bot;
			bot = mid;
			mid = temp;
		}
		if (mid.getPos().getY() > top.getPos().getY()) {
			Vertex temp = mid;
			mid = top;
			top = temp;
		}
		if (bot.getPos().getY() > mid.getPos().getY()) {
			Vertex temp = bot;
			bot = mid;
			mid = temp;
		}

		Edge e1 = new Edge(bot, mid);
		Edge e2 = new Edge(mid, top);
		Edge e3 = new Edge(bot, top);



		for (int y = (int) Math.ceil(e1.getMinY()); y < Math.ceil(e1.getMaxY()); y++) {

			if (y < 0 || y >= getHeight()) {
				continue;
			}

			int min;
			int max;
			Edge left;
			Edge right;
			if (e1.getCurrentX() < e3.getCurrentX()) {
				min = (int) Math.ceil(e1.getCurrentX());
				max = (int) Math.ceil(e3.getCurrentX());
				left = e1;
				right = e3;
			} else {
				min = (int) Math.ceil(e3.getCurrentX());
				max = (int) Math.ceil(e1.getCurrentX());
				left = e3;
				right = e1;
			}
			drawLine(bot, mid, top, left, right, min, max, y, camera);

			e1.step();
			e3.step();
		}
		// System.out.println("finished triangle p1");

		for (int y = (int) Math.ceil(e2.getMinY()); y < Math.ceil(e2.getMaxY()); y++) {

			if (y < 0 || y >= getHeight()) {
				continue;
			}
			int min;
			int max;
			Edge left;
			Edge right;
			if (e2.getCurrentX() < e3.getCurrentX()) {
				min = (int) Math.ceil(e2.getCurrentX());
				max = (int) Math.ceil(e3.getCurrentX());
				left = e2;
				right = e3;
			} else {
				min = (int) Math.ceil(e3.getCurrentX());
				max = (int) Math.ceil(e2.getCurrentX());
				left = e3;
				right = e2;
			}
			drawLine(bot, mid, top, left, right, min, max, y, camera);

			e2.step();
			e3.step();
		}
		// System.out.println("Finished triangle p2");

	}
	
	/**
	 * Gets the depth buffer
	 * @return the 2d float array depth buffer
	 */
	public float[][] getDepthBuffer() {
		return depthBuffer;
	}
	/**
	 * Gets the texture 
	 * @return the texture Canvas
	 */
	public Canvas getTexture() {
		return texture;
	}

	/**
	 * Does a 2D linear interpolation a value across the min and the max
	 * @param min The min value
	 * @param max The max value
	 * @param t a number between 0 and 1, 0 being the min and 1 being the max, a number in between is interpolated
	 * @return
	 */
	public float lerp(float min, float max, float t) {
		return t * (max - min) + min;
	}
	
	
	/**
	 * Sets texture to a new Canvas 
	 * @param texture the new Canvas
	 */
	public void setTexture(Canvas texture) {
		this.texture = texture;
	}

}
