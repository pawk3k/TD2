
package Model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.openvr.Texture;
import org.newdawn.slick.opengl.PNGDecoder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11C.GL_NEAREST;
import static org.newdawn.slick.opengl.PNGDecoder.RGBA;

public class TextureClass {
    private String path;

    private float width, height;
    private int textureID;
    private Texture texture;

        public TextureClass(String path) {
            this.path = path;
        }

        public void create() {

            try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(path))
            ) {

                PNGDecoder decoder = new PNGDecoder(is);
                ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
                decoder.decode(buf, decoder.getWidth()*4, RGBA);
                buf.flip();
                textureID = GL11.glGenTextures();
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

            }
            catch (IOException e) {
                System.err.println("Can't find texture at " + path);
            }
        }

        public void destroy() {
            GL13.glDeleteTextures(textureID);
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public int getTextureID() {
            return textureID;
        }
}
