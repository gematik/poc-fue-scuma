/*
 * Copyright 2022-2024, gematik GmbH
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by the
 * European Commission – subsequent versions of the EUPL (the "Licence").
 * You may not use this work except in compliance with the Licence.
 *
 * You find a copy of the Licence in the "Licence" file or at
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
 * In case of changes by gematik find details in the "Readme" file.
 *
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

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
