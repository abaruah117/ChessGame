

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Kevin 
 *This static class should be used to load models and indivdiual textures from file
 */
public class ModelLoader {

	private static Map<String, Model> models;
	private static Map<String, Canvas> textures;
	private static String directoryPath;


	/**
	 * Initializes the model loader to search in a directory. All files to be loaded must be in this folder
	 * @param dirString
	 */
	public static void init(String dirString) {

		models = new HashMap<String, Model>();
		textures = new HashMap<String, Canvas>();
		directoryPath = dirString;
	}
	
	/**
	 * Loads a model from file. See other constructor for naming rules
	 * @param name The name of the model to be loaded
	 * @param transform The transform of this 
	 */
	public static void loadModel(String name, Matrix transform) {
		loadModel(name, transform, true);
	}
	
	/**
	 * Loads a model from file
	 * The model must be of .obj format located in the directory specifies in the init method <b>
	 * The file must be named "name.obj", and a texture if needed as "name.png" <b>
	 * To get the model later, use the same name used here
	 * @param name The name of the file, following the above rules
	 * @param modelMatrix The model matrix of this model
	 * @param smoothNormals Whether or not to smooth the normals, which averages surrounding normals for a better look, but extra start up time
	 */
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
		models.put(name, m);
		
	}
	
	/**
	 * Loads a texture from file <b>
	 * The texture must be of .png format located in the directory specifies in the init method <b>
	 * The texture must be in a file "name.png" <b>
	 * The texture should not be associated with a model, if so it should be loaded along with the model using loadModel() <b>
	 * To get the texture, use the same name given here
	 * @param name The name of the texture, following the above rules
	 */
	public static void loadTexture(String name) {
		Canvas s = new Canvas(directoryPath + "/" + name + ".png");
		textures.put(name, s);
	}
	
	/**
	 * Gets a a texture from memory<b>
	 * Must of been loaded beforehand <b>
	 * Use the same name used to load the texture
	 * @param name The name of the texture
	 * @return The texture
	 */
	public static Canvas getTexture(String name) {
		Canvas c =  textures.get(name);
		if(c == null) {
			System.err.println("The model for " + name + " does not exists");
			System.exit(1);
		}
		return c;
	}
	
	/**
	 * Gets a a model from memory<b>
	 * Must of been loaded beforehand <b>
	 * Use the same name used to load the model
	 * @param name The name of the model
	 * @return The model
	 */
	public static Model getModel(String name) {
		Model m =  models.get(name);
		if(m == null) {
			System.err.println("The model for " + name + " does not exists");
			System.exit(1);
		}
		return m;
	}
	
}
