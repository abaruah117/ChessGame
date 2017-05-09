

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ModelLoader {

	private static Map<String, Model> models;
	private static String directoryPath;
	
	public static Model getModel(String name) {
		Model m =  models.get(name);
		if(m == null) {
			System.err.println("The model for " + name + " does not exists");
			System.exit(1);
		}
		return m;
	}
	
	@SuppressWarnings("serial")
	public static void init(String dirString) {
		models = new HashMap<String, Model>(){};
		directoryPath = dirString;
	}
	
	public static void loadModel(String name, Matrix transform) {
		loadModel(name, transform, true);
	}
	
	public static void loadModel(String name, Matrix modelMatrix, boolean smoothNormals) {
		System.out.println("Loading " + name);
		File modelFile = new File(directoryPath + "/" + name + ".obj");
		if(!modelFile.exists()) {
			System.err.println("The file at " + modelFile.getAbsolutePath() + " does not exist");
			System.exit(1);
		}
		File textureFile = new File(directoryPath + "/" + name + ".png");
		Canvas texture;
		if(!textureFile.exists()) {
			texture = null;
		} else {
			texture = new Canvas(textureFile.getAbsolutePath());
		}
		Model m = new Model(new OBJModel(modelFile.getAbsolutePath(), smoothNormals), modelMatrix, texture, name);
//		System.out.println("After model has been created: \n");
//		for(Vertex v:m.getObjModel().getVerticies()) {
//			System.out.println(v.getOriginalNormal());
//		}
		models.put(name, m);
		
	}
	
}
