package Game;

public class GameLights extends GameObjectPrefab {
    int GameObjectModel;

    public int getModel(){
        return GameObjectModel;
    }

    /**
     * @param id     ID of an object (non zero)
     * @param parent parent id (0 if no parent)
     * @param GameObjectModel object 3D model
     */
    public GameLights(int id, int parent, int GameObjectModel) throws Exception {
        super(id, parent);
        this.GameObjectModel = GameObjectModel;
    }
}
