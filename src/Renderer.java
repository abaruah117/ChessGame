import java.awt.Color;
import java.util.ArrayList;

public class Renderer extends Canvas {

	private static boolean DO_WIRE_MESH = false;
	private Canvas texture;
	private Vector color;

	private ArrayList<Light> lights;

	float[][] depthBuffer;

	private boolean isMain = true;

	public Renderer(int width, int height) {
		super(width, height);
		lights = new ArrayList<Light>();
		depthBuffer = new float[height][width];
		clearDepthBuffer();
	}

	public void addLight(Light light) {
		lights.add(light);
	}

	public int clamp(float value, float max) {
		return (int) (value > max ? max : value);
	}

	public void clearDepthBuffer() {
		for (int y = 0; y < depthBuffer.length; y++) {
			for (int x = 0; x < depthBuffer[0].length; x++) {
				depthBuffer[y][x] = -Float.MAX_VALUE;
			}
		}

	}

	public void drawBoard(Board b, Camera c, Matrix boardMatrix,
			Matrix peiceMatrix) {

		Tile[][] tiles = b.getBoardColor();
		drawClickableTiles(tiles, boardMatrix, c);
		for (Piece p : b.getWhitePieces()) {
			// System.out.println(p.getClass().getName());

			Coord pos = p.getCoord();
			Matrix position = new Matrix()
					.translationMatrix(-pos.getX() * Board.getTileSize(), 0,
							-pos.getY() * Board.getTileSize());
			Mesh m = ModelLoader.getModel(p.getClass().getName()).getMesh(c,
					Matrix.multiply(position, peiceMatrix));
			color = new Vector(255, 223, 173);
			drawMesh(m, c);

		}

		for (Piece p : b.getBlackPieces()) {
			// System.out.println(p.getClass().getName());

			Coord pos = p.getCoord();
			Matrix position = new Matrix()
					.translationMatrix(-pos.getX() * Board.getTileSize(), 0,
							-pos.getY() * Board.getTileSize());
			Mesh m = ModelLoader.getModel(p.getClass().getName()).getMesh(c,
					Matrix.multiply(position, peiceMatrix));
			color = new Vector(100, 100, 100);
			drawMesh(m, c);

		}

	}

	public void drawClickableTiles(Tile[][] tiles, Matrix transform,
			Camera camera) {
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
				setTexture(m.getTexture());
				OBJModel model = m.getModel();

				Vertex v1 = model.getVertex(0).multiply(m.getTransform())
						.persectiveDevide();
				Vertex v2 = model.getVertex(1).multiply(m.getTransform())
						.persectiveDevide();
				Vertex v3 = model.getVertex(2).multiply(m.getTransform())
						.persectiveDevide();
				Vertex v4 = model.getVertex(3).multiply(m.getTransform())
						.persectiveDevide();

				Clickable clickable = new Clickable(v3.getPos(), v4.getPos(),
						v1.getPos(), v2.getPos());
				tiles[y][x].setCollider(clickable);
				// v1
				// v2
				// v3
				// v4

				// System.out.println(v1); left top
				// System.out.println(v2); left bot
				// System.out.println(v3); right top
				// System.out.println(v4); right bot
				// System.exit(0);

				drawTriangle(v1, v2, v3, camera);
				drawTriangle(v2, v3, v4, camera);

			}
		}
	}

	public void drawLine(Vertex v1, Vertex v2) {
		Vertex bot = v1;
		Vertex top = v2;
		if (bot.getPos().getY() > top.getPos().getY()) {
			bot = v2;
			top = v1;
		}
		Edge e = new Edge(bot, top);

		// System.out.println(e.getMinY() + " " + e.getMaxX());
		for (int y = (int) Math.ceil(e.getMinY()); y < 9999; y++) {
			drawPixel((int) Math.ceil(e.getCurrentX()), y, Color.RED);
			e.step();
		}
	}

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
			for (Light light : lights) {

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

	public void drawMesh(Mesh m, Camera camera) {
		setTexture(m.getTexture());
		OBJModel model = m.getModel();
		for (int i = 0; i < model.VertexCount(); i += 3) {

			Vertex v1 = model.getVertex(i + 0).multiply(m.getTransform())
					.persectiveDevide();
			Vertex v2 = model.getVertex(i + 1).multiply(m.getTransform())
					.persectiveDevide();
			Vertex v3 = model.getVertex(i + 2).multiply(m.getTransform())
					.persectiveDevide();

			drawTriangle(v1, v2, v3, camera);

		}

	}

	public void drawTriangle(Vertex v1, Vertex v2, Vertex v3, Camera camera) {
		// System.out.println("In draw triangle");
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

	public float[][] getDepthBuffer() {
		return depthBuffer;
	}

	public Canvas getTexture() {
		return texture;
	}

	public boolean isMain() {
		return isMain;
	}

	public float lerp(float min, float max, float t) {
		return t * (max - min) + min;
	}

	public void setDepthBuffer(float[][] depthBuffer) {
		this.depthBuffer = depthBuffer;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

	public void setTexture(Canvas texture) {
		this.texture = texture;
	}

}
