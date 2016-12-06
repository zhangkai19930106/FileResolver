import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

/**
 * Created by ZK on 2016/12/6.
 */
public class Starter implements EmbeddedServletContainerCustomizer {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Starter.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8088);
    }
}
