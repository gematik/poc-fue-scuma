package de.gematik.scuma.apis;

import de.gematik.kether.eth.types.Data20;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by rk on 14.11.2022.
 * gematik.de
 */
public class ControlApiJavaTests {
    @Test
    public void registerProvider() {
        ControlApiJava controlApiJava = new ControlApiJava(ConfigKt.getContractId(), ConfigKt.getOwnerId(), ConfigKt.getRpc());
        CompletableFuture<Boolean> future = controlApiJava.registerProviderAsync(ConfigKt.getUserId());
        try {
            assert (future.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert (false);
        }
    }

    @Test
    public void getProviders() {
        ControlApiJava controlApiJava = new ControlApiJava(ConfigKt.getContractId(), ConfigKt.getOwnerId(), ConfigKt.getRpc());
        List<Data20> providers = controlApiJava.getProviders();
        System.out.println(providers);
    }

    @Test
    public void unRegisterProvider() {
        ControlApiJava controlApiJava = new ControlApiJava(ConfigKt.getContractId(), ConfigKt.getOwnerId(), ConfigKt.getRpc());
        CompletableFuture<Boolean> future = controlApiJava.unregisterProviderAsync(ConfigKt.getUserId());
        try {
            assert (future.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert (false);
        }
    }

}
