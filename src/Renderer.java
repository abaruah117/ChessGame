import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * @author Amitav & Kevin 
 * Period 3
 */
/*
 * A class to Render images for user viewing
 */
public class Renderer extends Canvas {

	private static boolean DO_WIRE_MESH = false;
	private Canvas texture;
	private Vector color;

	private ArrayList<Light> lights;

	float[][] depthBuffer;

	private boolean isMain = true;
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
	 * Adds a Light to the ArrayList
	 * @param light the Light to be added
	 */
	public void addLight(Light light) {
		lights.add(light);
	}
	
	/**
	 * Returns the smaller float out of two floats
	 * @param value the first value
	 * @param max the second value
	 * @return the smaller value 
	 */
	public int clamp(float value, float max) {
		return (int) (value > max ? max : value);
	}
	
	/**
	 * Sets the depth buffer to the lowest possible values
	 */
	public void clearDepthBuffer() {
		for (int y = 0; y < depthBuffer.length; y++) {
			for (int x = 0; x < depthBuffer[0].length; x++) {
				depthBuffer[y][x] = -Float.MAX_VALUE;
			}
		}

	}
	/**
	 * Draws the pieces of a Chess Game using swing
	 * @param b the Board containing the pieces to be drawn
	 * @param c the Camera to be used 
	 * @param boardTrans 
	 * @param selected the selected Coords 
	 * @param rotationAngles the Vector at which the user has rotated the rendered image
	 */
	public void drawBoardPieces(Board b, Camera c, Vector boardTrans,
			ArrayList<Coord> selected, Vector rotationAngles) {

		Vector align = new Vector(60, 0, 70);

		Matrix xRot = new Matrix().rotationXMatrix(rotationAngles.getX());
		Matrix yRot = new Matrix().rotationYMatrix(rotationAngles.getY());
		Matrix zRot = new Matrix().rotationZMatrix(rotationAngles.getZ()); //TODO get matrices from engine, not needs but helps fps
		
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

	}
	/**
	 * Draws the tiles of the Board
	 * @param b the Chess Game's Board
	 * @param boardMatrix the Matrix for drawing Clickable tiles
	 * @param c the Camera 
	 * @param selected the user-selected Coords
	 */
	public void drawBoardTiles(Board b, Matrix boardMatrix, Camera c,
			ArrayList<Coord> selected) {
		Tile[][] tiles = b.getBoardColor();
		drawClickableTiles(tiles, boardMatrix, c, selected);
	}
	
	/**
	 * Draws 
	 * @param tiles the Tile 2d Array
	 * @param transform the Matrix to be used to format rendered images correctly
	 * @param camera the Camera
	 * @param selected the user-selected Coords
	 */
	public void drawClickableTiles(Tile[][] tiles, Matrix transform,
			Camera camera, ArrayList<Coord> selected) {
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
	 * Draws lines on the Board from Vertex 1 to Vertex2
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
		for (int y = (int) Math.ceil(e.getMinY()); y < 9999; y++) {
			drawPixel((int) Math.ceil(e.getCurrentX()), y, Color.RED);
			e.step();
		}
	}
	/*
	 * Draws lines based on 3 Vertices, 2 Edges, 3 int values and a Camera
	 * @param bot the bottom Vertex
	 * @param mid the middle Vertex
	 * @param top the top Vertex
	 * @param left the left Edge
	 * @param right the right Edge
	 * @param minX the minimum x value
	 * @param maxX the maximum x value
	 * @param y the y value
	 * @param camera
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
	 * Draws a given mesh for a given camera
	 * @param m the Mesh
	 * @param camera the Camera
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
	 * Draws a 3D triangle 
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

		@SuppressWarnings("unused")
		boolean isLeftTriangle = mid.getPos().getX() < e3.getXfromEq(mid
				.getPos().getY());
		// System.out.println("finished precalc");
		// System.out.println(left);

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
	 * Checks if this Renderer is the main one
	 * @return isMain
	 */
	public boolean isMain() {
		return isMain;
	}
	//TODO idk why 
	/**
	 * Multiplies the third float by the midpoint of the first two floats 
	 * @param min the minimum float
	 * @param max the maximum float
	 * @param t the float to be multiplied by
	 * @return t * (2*min - max)
	 */
	public float lerp(float min, float max, float t) {
		return t * (max - min) + min;
	}
	
	/**
	 * Sets the depth buffer to a new 2D float array
	 * @param depthBuffer the new 2D float array
	 */
	public void setDepthBuffer(float[][] depthBuffer) {
		this.depthBuffer = depthBuffer;
	}
	
	/**
	 * Sets isMain to a new boolean
	 * @param isMain the new boolean
	 */
	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	/**
	 * Sets texture to a new Canvas 
	 * @param texture the new Canvas
	 */
	public void setTexture(Canvas texture) {
		this.texture = texture;
	}

}
